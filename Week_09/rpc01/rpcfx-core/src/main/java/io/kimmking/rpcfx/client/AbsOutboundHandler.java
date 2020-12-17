package io.kimmking.rpcfx.client;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/11/1 17:42
 */
@Slf4j
public abstract class AbsOutboundHandler {

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final String routeUrl) {

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
