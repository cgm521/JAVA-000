package com.example.nettygateway.outbound.netty;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/11/2 00:24
 */

public class ResponseInboundHandler extends ChannelInboundHandlerAdapter {
    Map<String, String> head = new HashMap<String, String>();
    String respContent = "";
    Integer respLength = Integer.MAX_VALUE; // 响应报文长度

    //@Override
    //public void channelActive(ChannelHandlerContext ctx) throws Exception {
    //
    //    // 通断被激活时我们发送请求到指定路径
    //    URI uri = new URI("/user/get");
    //    FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uri.toASCIIString());
    //    // FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString()); // 有时候1.1发送的请求会报400错误
    //    // 添加请求头，保持长连接
    //    request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
    //    // 添加请求头，确定请求体的长度
    //    request.headers().add(HttpHeaderNames.CONTENT_LENGTH,request.content().readableBytes());
    //    // 将请求实体写入出站处理器
    //    ctx.writeAndFlush(request);
    //    super.channelActive(ctx);
    //}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpResponse fullHttpResponse = null;
        try {
            System.out.println("response handler");
            System.out.println(msg);

            if (msg instanceof HttpResponse) {
                HttpResponse response = (HttpResponse) msg;
                for (Map.Entry entry : response.headers().entries()) {
                    head.put((String) entry.getKey(), (String) entry.getValue());
                }
                if (response.headers().get("Content-Length") != null) {
                    System.out.println("response Length");
                    respLength = Integer.parseInt(response.headers().get("Content-Length"));
                }
            }

            if (msg instanceof HttpContent) {
                HttpContent content = (HttpContent) msg;
                ByteBuf buf = content.content();
                respContent += buf.toString(Charset.forName("UTF-8"));
                content.release();
                if (respContent.getBytes().length >= respLength || !buf.isReadable()) {
                    System.out.println("response isReadable");
                     fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, OK, Unpooled.wrappedBuffer(respContent.getBytes()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fullHttpResponse != null) {
                if (!HttpUtil.isKeepAlive(fullHttpResponse)) {
                    ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(fullHttpResponse);
                }
            }
            ctx.flush();
            ctx.channel().close();
            ctx.close();
        }



    }
}
