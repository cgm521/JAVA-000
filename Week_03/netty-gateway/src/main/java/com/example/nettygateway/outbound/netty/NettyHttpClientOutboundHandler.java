package com.example.nettygateway.outbound.netty;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Resource;

import com.example.nettygateway.outbound.AbsOutboundHandler;
import com.example.nettygateway.router.HttpEndpointRouter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/11/1 23:59
 */
@Component
public class NettyHttpClientOutboundHandler extends AbsOutboundHandler {
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Bootstrap b = new Bootstrap();
    private static final EventExecutorGroup executor = new DefaultEventExecutorGroup(2);
    public static final Object lock = new Object();


    public NettyHttpClientOutboundHandler() {
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
    }

    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx, final String routeUrl) {
        try {
            URI uri = new URI(routeUrl + fullRequest.uri());
            String host = uri.getHost();
            int port = uri.getPort();
            byte[] bytes = parse(fullRequest).getBytes();
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uri.toASCIIString());
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
                    ch.pipeline().addLast(executor, new ResponseInboundHandler());
                }
            };
            ChannelFuture f;
            synchronized (lock) {
                b.handler(channelInitializer);
                // start the client.
                f = b.connect(host, port).sync();
            }
            f.channel().writeAndFlush(request);
            f.channel().closeFuture().sync();
            //b.handler(channelInitializer);
            //    // start the client.
            //ChannelFuture  f = b.connect(host, port).sync();
            //// 等待与远程http服务器断开连接
            //f.channel().closeFuture().sync();
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
