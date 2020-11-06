package com.example.nettygateway.inbound;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.example.nettygateway.filter.HttpRequestFilter;
import com.example.nettygateway.outbound.AbsOutboundHandler;
import com.example.nettygateway.router.HttpEndpointRouter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/11/1 17:30
 */
@Slf4j
@Sharable
@Component
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    /**
     * 请求客户端端处理器
     */
    //@Resource(name = "nettyHttpClientOutboundHandler")
    @Resource(name = "httpClientOutboundHandle")
    private AbsOutboundHandler outboundHandler;
    /**
     * 过滤器
     */
    @Resource
    private HttpRequestFilter httpRequestFilter;

    /**
     * 路由器
     */
    @Resource
    private HttpEndpointRouter httpEndpointRouter;
    /**
     * 限流，同时可处理请求数
     */
    private Semaphore semaphore = new Semaphore(8);

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        semaphore.acquire();
        log.info("{} 获取到资源，剩余资源：{}", Thread.currentThread().getName(), semaphore.availablePermits());
        TimeUnit.SECONDS.sleep(2);
        FullHttpRequest fullRequest = (FullHttpRequest)msg;

        httpRequestFilter.filter(fullRequest, ctx);
        String route = httpEndpointRouter.route(fullRequest);
        outboundHandler.handle(fullRequest, ctx, route);
        semaphore.release();
        log.info("{} 释放资源，剩余资源：{}", Thread.currentThread().getName(), semaphore.availablePermits());
    }
}
