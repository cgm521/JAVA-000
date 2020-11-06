package com.example.nettygateway;

import com.example.nettygateway.inbound.HttpInboundHandler;
import com.example.nettygateway.utils.ApplicationContextProvider;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/11/1 17:13
 */
@Slf4j
@Component
public class NettyGatewayServer {
    @Value("${server.netty.port}")
    private String serverNettyPort;

    public void run() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(16);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                .option(EpollChannelOption.SO_REUSEPORT, true)
                .childOption(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                //.handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new HttpServerCodec());
                    p.addLast(new HttpObjectAggregator(1024 * 1024));
                    p.addLast(ApplicationContextProvider.getBean(HttpInboundHandler.class));
                }
            });
            int port = Integer.parseInt(serverNettyPort);
            Channel channel = bootstrap.bind(port).sync().channel();
            log.info("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("开启netty http服务器失败！！");
            e.printStackTrace();
        }

    }

}
