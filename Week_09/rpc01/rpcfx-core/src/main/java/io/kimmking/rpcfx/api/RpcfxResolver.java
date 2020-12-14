package io.kimmking.rpcfx.api;

public interface RpcfxResolver {

    // 服务端寻找实现类
    Object resolve(String serviceClass) throws ClassNotFoundException;

}
