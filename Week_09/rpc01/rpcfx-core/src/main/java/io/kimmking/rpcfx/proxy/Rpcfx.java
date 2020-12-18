package io.kimmking.rpcfx.proxy;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.io.client.HttpClientUtil;
import okhttp3.MediaType;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {

        // 0. 替换动态代理 -> AOP
        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));

    }

    public static class RpcfxInvocationHandler implements InvocationHandler {

        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;

        public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }

        // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
        // int byte char float double long bool
        // [], data class

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(params);

//            Object handle = NettyClient.handle(request, url);
            System.out.println("----------");
            RpcfxResponse response = HttpClientUtil.postJson(request, url);
            // 这里判断response.status，处理异常
            // 考虑封装一个全局的RpcfxException
            if (response.isStatus()) {
                return JSON.parseObject(response.getResult().toString(), Class.forName(response.getClassName()));
            } else {
                response.getException().printStackTrace();
                throw response.getException();
            }
        }
    }
}
