package io.kimmking.rpcfx.io.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/12/15 10:39
 */

public class ClientInboundHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ClientInboundHandler.class);

    private String requestUri;
    private byte[] param;

    public ClientInboundHandler(String requestUri, byte[] param) {
        this.requestUri = requestUri;
        this.param = param;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg>>>>>>" + msg);
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            for (Map.Entry<String, String> entry : response.headers().entries()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            String resp = buf.toString(CharsetUtil.UTF_8);
            System.out.println("client--->"+resp);
//            handleResponse(resp);
            buf.release();
        }
    }

    private void handleResponse(String resp) {
        byte[] body = resp.getBytes();
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, Unpooled.wrappedBuffer(body));
        response.headers().set("Content-Type", "text/plain;charset=UTF-8");
        response.headers().setInt("Content-Length", body.length);
//        this.ctx.writeAndFlush(response);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        URI uri = new URI(requestUri);
        //配置HttpRequest的请求数据和一些配置信息
        FullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_0, HttpMethod.POST, uri.toASCIIString(),Unpooled.wrappedBuffer(this.param));
        request.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8")
                // 开启长连接
                // .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
                // 设置传递请求内容的长度
                .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

        //发送数据
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}