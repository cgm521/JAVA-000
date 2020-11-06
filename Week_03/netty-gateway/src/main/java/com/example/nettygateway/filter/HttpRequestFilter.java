package com.example.nettygateway.filter;

import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Component;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/11/1 16:52
 */
@Component
public class HttpRequestFilter {
    
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx){
        fullRequest.headers().add("traceId", UUID.randomUUID().toString().replaceAll("-", ""));
    }
    
}
