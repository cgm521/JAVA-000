# 3、(必做)改造自定义RPC的程序，提交到github:
- 2)尝试将客户端动态代理改成AOP，添加异常处理
  - 字节码增强 [ByteBuddyRpcfx](rpc01/rpcfx-core/src/main/java/io/kimmking/rpcfx/proxy/ByteBuddyRpcfx.java)
  - 异常处理 [RpcfxException](rpc01/rpcfx-core/src/main/java/io/kimmking/rpcfx/api/RpcfxException.java)

- 3)尝试使用Netty+HTTP作为client端传输方式

    - netty客户端 [NettyClient](rpc01/rpcfx-core/src/main/java/io/kimmking/rpcfx/io/client/NettyClient.java)

    - netty服务端 [NettyServerClient](rpc01/rpcfx-core/src/main/java/io/kimmking/rpcfx/io/server/NettyServerClient.java)

客户端根据注入的client不同，走Http请求或者netty请求
```java
 @GetMapping("/findId")
    public User findId() throws InstantiationException, IllegalAccessException {
        log.info("findId");
        UserService userService = byteBuddyRpcfx.create(UserService.class, "http://localhost:8081/");
        return userService.findById(1);
    }

    @Bean
    public AbsClient creditClient() {
        // return new HttpClient();
        return new NettyClient();
    }

    @Bean
    public ByteBuddyRpcfx byteBuddyRpcfx() {
        return new ByteBuddyRpcfx(client);
    }
```