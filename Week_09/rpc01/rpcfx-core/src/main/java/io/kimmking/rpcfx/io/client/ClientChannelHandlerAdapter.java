package io.kimmking.rpcfx.io.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author:wb-cgm503374
 * @Description 客户端handler
 * @Date:Created in 2020/12/18 下午9:51
 */

public class ClientChannelHandlerAdapter extends SimpleChannelInboundHandler<ByteBuf> {
    private String param;
    private CustomerChannelInitializer customerChannelInitializer;

    public ClientChannelHandlerAdapter(String param, CustomerChannelInitializer customerChannelInitializer) {
        this.param = param;
        this.customerChannelInitializer = customerChannelInitializer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String res = msg.toString(CharsetUtil.UTF_8);
        System.out.println("客户端收到返回数据：" + res);
        customerChannelInitializer.setResponse(res);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端准备发送数据");
        ctx.writeAndFlush(Unpooled.copiedBuffer(param +"\n\r", CharsetUtil.UTF_8));
        System.out.println("客户端发送数据完成");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常");
        cause.printStackTrace();
        ctx.close();
    }
}
