package io.kimmking.rpcfx.io.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.CountDownLatch;

/**
 * @Author:wb-cgm503374
 * @Description 客户端
 * @Date:Created in 2020/12/18 下午9:50
 */

public class CustomerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private String response;
    private CountDownLatch countDownLatch;
    private String param;

    private ClientChannelHandlerAdapter clientChannelHandlerAdapter;


    public CustomerChannelInitializer(String param) {
        this.param = param;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        countDownLatch = new CountDownLatch(1);
        clientChannelHandlerAdapter = new ClientChannelHandlerAdapter(param, this);
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(clientChannelHandlerAdapter);
    }

    public String getResponse() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
        countDownLatch.countDown();
    }
}
