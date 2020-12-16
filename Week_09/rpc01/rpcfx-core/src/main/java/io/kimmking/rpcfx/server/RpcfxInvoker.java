package io.kimmking.rpcfx.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import io.kimmking.rpcfx.api.RpcfxException;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RpcfxInvoker {

    private RpcfxResolver resolver;

    private XStream xstream = new XStream(new DomDriver());

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();


        try {
            // 作业1：改成泛型和反射
            Object service = resolver.resolve(serviceClass);//this.applicationContext.getBean(serviceClass);
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
            // 两次json序列化能否合并成一个
            response.setResult(result);
            response.setClassName(result.getClass().getName());
            response.setStatus(true);
            return response;
        } catch (ClassNotFoundException e) {

            // 3.Xstream

            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setException(RpcfxException.creatFailException(RpcfxException.ErrorCode.CLASS_NOT_FIND, e));
            response.setStatus(false);
            return response;
        } catch (Exception e) {
            // 3.Xstream

            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setException(RpcfxException.creatFailException(RpcfxException.ErrorCode.SYS_ERROR, e));
            response.setStatus(false);
            return response;
        }
    }


    public String invokeXml(String request) {
        RpcfxRequest rpcfxRequest = (RpcfxRequest) xstream.fromXML(request);
        RpcfxResponse response = invoke(rpcfxRequest);
        return xstream.toXML(response);

    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }

}
