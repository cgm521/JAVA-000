# 3、(必做)改造自定义RPC的程序，提交到github:
- 2)尝试将客户端动态代理改成AOP，添加异常处理
  - 字节码增强 [ByteBuddyRpcfx](rpc01/rpcfx-core/src/main/java/io/kimmking/rpcfx/proxy/ByteBuddyRpcfx.java)
  - 异常处理 [RpcfxException](rpc01/rpcfx-core/src/main/java/io/kimmking/rpcfx/api/RpcfxException.java)

  服务端处理异常
```java
  public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();
        try {
            // 作业1：改成泛型和反射
            Object service = resolver.resolve(serviceClass);
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams()); 
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
        } catch (ClassNotFoundException e) {
            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setException(RpcfxException.creatFailException(RpcfxException.ErrorCode.CLASS_NOT_FIND, e));
            response.setStatus(false);
            return response;
        } catch (Exception e) {
            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setException(RpcfxException.creatFailException(RpcfxException.ErrorCode.SYS_ERROR, e));
            response.setStatus(false);
            return response;
        }
    }
```

客户端处理异常

```java
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
        public Object intercept(@Origin String method, @AllArguments Object[] params) throws RpcfxException {
            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass);
            request.setMethod(method.substring(method.lastIndexOf(".") + 1, method.indexOf("(")));
            request.setParams(params);
            RpcfxResponse response = HttpClientUtil.post(request, url);
            // 判断请求是否成功
            if (response.isStatus()) {
                return JSON.parse(response.getResult().toString());
            } else {
                response.getException().printStackTrace();
                throw response.getException();
            }
        }
    }
}
```
- 3)尝试使用Netty+HTTP作为client端传输方式

 [NettyClient](rpc01/rpcfx-core/src/main/java/io/kimmking/rpcfx/io/client/NettyClient.java)

 [NettyServerClient](rpc01/rpcfx-core/src/main/java/io/kimmking/rpcfx/io/server/NettyServerClient.java)
