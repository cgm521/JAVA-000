package io.kimmking.rpcfx.proxy;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/15 下午9:21
 */

public class CglibRpcfx {
    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }
    public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");
    public static <T> T create(final Class<T> serviceClass, final String url) {
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(serviceClass);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
                RpcfxRequest request = new RpcfxRequest();
                request.setServiceClass(serviceClass.getName());
                request.setMethod(method.getName());
                request.setParams(params);
                RpcfxResponse response = post(request, url);
                return JSON.parseObject(response.getResult().toString(), Class.forName(response.getClassName()));
            }
        });
        return (T) enhancer.create();
    }
    private static RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: "+reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, reqJson))
                .build();
        String respJson = client.newCall(request).execute().body().string();
        System.out.println("resp json: "+respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }
}
