package io.kimmking.rpcfx.io.client;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxException;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.enums.BodyTypeEnum;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/13 6:38 下午
 */

public class NettyClient extends AbsClient{
    private static Bootstrap b;
    private static EventLoopGroup workerGroup;
    private static Channel clientChannel;

    static {
        workerGroup = new NioEventLoopGroup(2);
        b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, false);
    }

    @Override
    public RpcfxResponse send(RpcfxRequest request, String url, BodyTypeEnum bodyType) {
        return handle(request, url);
    }

    private RpcfxResponse handle(RpcfxRequest param, String url) {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            int port = uri.getPort();

            CustomerChannelInitializer customerChannelInitializer = new CustomerChannelInitializer(JSON.toJSONString(param));

            ChannelFuture f;
            b.handler(customerChannelInitializer);
            // start the client.
            f = b.connect(host, port).sync();
            clientChannel = f.channel();
            //接收服务端返回的数据
            String response = customerChannelInitializer.getResponse();
            // 客户端关闭
//            close();
            System.out.println("response>>" + response);
            return JSON.parseObject(response, RpcfxResponse.class);
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            return RpcfxResponse.createFail(RpcfxException.creatFailException(RpcfxException.ErrorCode.REQUEST_EXCEPTION, e));
        }
    }

    /**
     * 客户端关闭
     */
    @Override
    public void close() {
        //关闭客户端套接字
        if (clientChannel != null) {
            clientChannel.close();
        }
        //关闭客户端线程组
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
