package io.kimmking.rpcfx.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.enums.BodyTypeEnum;
import io.kimmking.rpcfx.io.client.AbsClient;
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

    private AbsClient client;

    public ByteBuddyRpcfx(AbsClient client) {
        this.client = client;
    }

    public <T> T create(final Class<T> serverClass, final String url) throws IllegalAccessException, InstantiationException {
        return new ByteBuddy()
                .subclass(serverClass)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(new Intercept(url, serverClass.getName(), client)))
                .make().load(serverClass.getClassLoader()).getLoaded().newInstance();
    }

    public static class Intercept {
        private String url;
        private String serviceClass;
        private AbsClient client;

        public Intercept(String url, String serviceClass,AbsClient client) {
            this.url = url;
            this.serviceClass = serviceClass;
            this.client = client;
        }

        @RuntimeType
        public Object intercept(@Origin String method, @AllArguments Object[] params) throws Exception {
            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass);
            request.setMethod(method.substring(method.lastIndexOf(".") + 1, method.indexOf("(")));
            request.setParams(params);
            RpcfxResponse res = client.send(request, url, BodyTypeEnum.JSON);
            System.out.println(res);
            System.out.println("---");
            if (res.isStatus()) {
                return JSON.parseObject(JSON.toJSONString(res.getResult()), Class.forName(res.getClassName()));
            } else {
                log.error("方法{}请求失败，exception:{}", method, res.getException());
                throw res.getException();
            }
            // xml
//            RpcfxResponse responseXml = HttpClientUtil.postXml(request, url);
//
//            // 判断请求是否成功
//            if (responseXml.isStatus()) {
//                return responseXml.getResult();
//            } else {
//                log.error("方法{}请求失败，exception:{}", method, responseXml.getException());
//                throw responseXml.getException();
//            }

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
