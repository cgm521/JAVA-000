package io.kimmking.rpcfx.client;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/13 6:38 下午
 */
@Slf4j
public class NettyClient {
    private static Bootstrap b;

    static {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
    }

    public static Object handle(RpcfxRequest param, String url) {
        try {

            URI uri = new URI(url);
            String host = uri.getHost();
            int port = uri.getPort();
            byte[] bytes = JSON.toJSONBytes(param);

            ClientInbound clientInbound = new ClientInbound();
            b.handler(new LoggingHandler(LogLevel.INFO));
            b.handler(clientInbound);
            // 监听端口
            ChannelFuture f = b.connect(host, port);
            f.channel();
//            ChannelFuture sync = b.bind(0).sync();
//            sync.channel();

            NettyRequest nettyRequest = new NettyRequest();
            nettyRequest.setIp(host);
            nettyRequest.setPort(port);
            nettyRequest.setData(bytes);
            ChannelPromise promise = clientInbound.sendCommandPackage(nettyRequest);
            try {
                promise.await(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {

                e.printStackTrace();

            }
            NettyResponse result = clientInbound.getResponse();
            System.out.println("netty end!!!!");
            System.out.println(result.resultData);
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class ByteArrayToBinaryEncoder extends MessageToMessageEncoder<byte[]> {
        @Override
        protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
            out.add(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(msg)));
        }
    }
    @ChannelHandler.Sharable
    public static class ClientInbound extends SimpleChannelInboundHandler<DatagramPacket> {
        private ChannelHandlerContext ctx;

        private ChannelPromise promise;

        private NettyResponse response;

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
            ByteBuf content = datagramPacket.content();
            int dataLength = content.readableBytes();
            byte[] data = new byte[dataLength];
            content.readBytes(data);
            // 3.读取数据
            String reply = BytesToHexString(Arrays.copyOfRange(data, 0, dataLength), " ");
            response = NettyResponse.builder()
                    .resultData(reply)
                    .build();
            promise.setSuccess();
            log.info("RFID 读卡器返回数据:response={}", response);
        }

        public synchronized ChannelPromise sendCommandPackage(NettyRequest request) {
            while (ctx == null) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                    log.info("等待ChannelHandlerContext实例化");
                } catch (InterruptedException e) {
                    log.error("等待ChannelHandlerContext实例化过程中出错", e);
                }
            }
            promise = ctx.newPromise();
            try {
                ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(request.getData()),
                        new InetSocketAddress(request.getIp(),
                                request.getPort())))
                        .sync();
            } catch (InterruptedException e) {
                log.error("RFID读卡器重启失败，request={}", request, e);
                response = NettyResponse.builder().build();
                promise.setSuccess();
            }
            return promise;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            this.ctx = ctx;
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        /**
         * Gets called if an user event was triggered.
         */
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            System.out.println("d");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelInactive();
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelUnregistered();
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelRegistered();
        }

        public NettyResponse getResponse() {

            return response;

        }

        public String BytesToHexString(byte[] b, String Padding) {

            return BytesToHexString(b, Padding, 0, b.length);

        }

        public String BytesToHexString(byte[] b, String Padding, int startIndex, int length) {

            String RetStr = "";

            for (int i = 0; i < length; i++) {

                String TmpStr = Integer.toHexString(b[i + startIndex] & 0xFF);

                if (TmpStr.length() < 2) {

                    TmpStr = "0" + TmpStr;

                }

                RetStr += TmpStr + Padding;

            }

            if (RetStr.length() > 0) {

                RetStr = RetStr.substring(0, RetStr.length() - Padding.length()).toUpperCase();

            }

            return RetStr;

        }

    }

    @Builder
    @Data
    public static class NettyResponse {

        private Boolean success;

        private String resultData;

        public Boolean getSuccess() {
            return success;
        }
    }

    @Data
    public static class NettyRequest {

        private String ip;

        private Integer port;


        private byte[] data;


    }
}


