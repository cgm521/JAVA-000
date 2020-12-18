package io.kimmking.rpcfx.io.client;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.enums.BodyTypeEnum;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/18 下午11:06
 */

public abstract class AbsClient {

    public abstract RpcfxResponse send(RpcfxRequest request, String url, BodyTypeEnum bodyType);

    protected void close() {

    }
}
