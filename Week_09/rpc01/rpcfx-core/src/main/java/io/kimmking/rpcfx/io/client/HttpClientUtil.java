package io.kimmking.rpcfx.io.client;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import io.kimmking.rpcfx.api.RpcfxException;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.enums.BodyTypeEnum;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/15 下午9:27
 */

public class HttpClientUtil {
    public static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");
    public static final MediaType XML_YPE = MediaType.get("application/xml");
    private static XStream xstream = new XStream(new DomDriver());

    public static RpcfxResponse postJson(RpcfxRequest req, String url) {
        req.setBodyType(BodyTypeEnum.JSON);
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: " + reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON_TYPE, reqJson))
                .build();
        try {
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: " + respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        } catch (IOException e) {
            return RpcfxResponse.createFail(RpcfxException.creatFailException(RpcfxException.ErrorCode.REQUEST_EXCEPTION, e));
        }
    }

    // xml 格式传输
    public static RpcfxResponse postXml(RpcfxRequest req, String url) {
        req.setBodyType(BodyTypeEnum.XML);
        String xml = xstream.toXML(req);
        System.out.println("req xml: " + xml);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url + "xml")
                .post(RequestBody.create(XML_YPE, xml))
                .build();
        try {
            String respXml = client.newCall(request).execute().body().string();
            System.out.println("resp xml: " + respXml);
            return (RpcfxResponse) xstream.fromXML(respXml);
        } catch (Exception e) {
            return RpcfxResponse.createFail(RpcfxException.creatFailException(RpcfxException.ErrorCode.REQUEST_EXCEPTION, e));
        }
    }
}
