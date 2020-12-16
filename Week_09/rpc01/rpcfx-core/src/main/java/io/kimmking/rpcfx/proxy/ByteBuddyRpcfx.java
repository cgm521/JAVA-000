package io.kimmking.rpcfx.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.RpcfxException;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.client.HttpClientUtil;
import io.kimmking.rpcfx.client.NettyClient;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @Author:wb-cgm503374
 * @Description ByteBuddy 字节码增强，把请求代理到拦截方法中，进行远程
 * @Date:Created in 2020/12/15 下午10:32
 */
@Slf4j
public class ByteBuddyRpcfx {
    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    public static <T> T create(final Class<T> serverClass, final String url) throws IllegalAccessException, InstantiationException {
        return new ByteBuddy()
                .subclass(serverClass)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(new Intercept(url, serverClass.getName())))
                .make().load(serverClass.getClassLoader()).getLoaded().newInstance();
    }

    public static class Intercept {
        private String url;
        private String serviceClass;

        public Intercept(String url, String serviceClass) {
            this.url = url;
            this.serviceClass = serviceClass;
        }

        @RuntimeType
        public Object intercept(@Origin String method, @AllArguments Object[] params) throws Exception {
            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass);
            request.setMethod(method.substring(method.lastIndexOf(".") + 1, method.indexOf("(")));
            request.setParams(params);
//            Object handle = NettyClient.handle(request, url);
//            System.out.println(handle);
//            System.out.println("---");
//            return handle;
            // xml
            RpcfxResponse responseXml = HttpClientUtil.postXml(request, url);

            // 判断请求是否成功
            if (responseXml.isStatus()) {
                return responseXml.getResult();
            } else {
                log.error("方法{}请求失败，exception:{}", method, responseXml.getException());
                throw responseXml.getException();
            }

            // json
//            RpcfxResponse response = HttpClientUtil.postJson(request, url);
//            if (response.isStatus()) {
//                return JSON.parseObject(JSON.toJSONString(responseXml.getResult()), Class.forName(response.getClassName()));
//            } else {
//                log.error("方法{}请求失败，exception:{}", method, response.getException());
//                throw response.getException();
//            }
        }
    }
}
