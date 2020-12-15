package io.kimmking.rpcfx.client;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/13 6:38 下午
 */

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
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST, uri.toASCIIString());
            //request.headers().add(fullRequest.headers());
            request.headers().remove("Connection");
            request.headers().add("Content-Length", bytes.length);
            request.headers().add("Host", host);
            request.headers().add("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:44.0) Gecko/20100101 Firefox/44.0");
            request.headers().add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            request.headers().add("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            //request.headers().add("Connection", "keep-alive");
            request.headers().add("Cache-Control", "max-age=0");
            ChannelInitializer<SocketChannel> channelInitializer = new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    //客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
//                    ch.pipeline().addLast(executor, new ResponseInboundHandler());
                }
            };
            ChannelFuture f;
            b.handler(channelInitializer);
            // start the client.
            f = b.connect(host, port).sync();
            f.channel().writeAndFlush(request);
            f.channel().closeFuture().sync();

            //接收服务端返回的数据
            AttributeKey<String> key = AttributeKey.valueOf("ServerData");
            Object result = f.channel().attr(key).get();
            System.out.println(result.toString());
            return result;
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
