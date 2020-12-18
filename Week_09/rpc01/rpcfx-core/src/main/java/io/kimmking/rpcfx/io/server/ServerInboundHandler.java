package io.kimmking.rpcfx.io.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/11/1 17:30
 */
@Slf4j
public class ServerInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg>>>" + msg);
        FullHttpRequest fullRequest = (FullHttpRequest)msg;

        if (msg instanceof FullHttpRequest) {
            String param = ((FullHttpRequest) msg).content().toString(CharsetUtil.UTF_8);
            RpcfxRequest request = JSON.parseObject(param, RpcfxRequest.class);
        }
//        String param = parse(fullRequest);
//        System.out.println("server param:" + param);

        ctx.write("response").addListener(ChannelFutureListener.CLOSE);

        fullRequest.release();
        ctx.flush();
    }

    /**
     * 解析参数
     *
     * @param fullRequest
     * @return
     * @throws IOException
     */
    protected String parse(final FullHttpRequest fullRequest) {
        JSONObject jsonObject = new JSONObject();
        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(fullRequest);
        decoder.offer(fullRequest);

        List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
        try {
            for (InterfaceHttpData param : parmList) {
                Attribute data = (Attribute)param;
                jsonObject.put(data.getName(), data.getValue());
            }
        } catch (IOException e) {
            log.error("request param parse error", e);
            return null;
        }
        return jsonObject.toJSONString();
    }
}
