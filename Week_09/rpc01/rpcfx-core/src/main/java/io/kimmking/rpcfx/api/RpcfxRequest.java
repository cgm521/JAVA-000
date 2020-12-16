package io.kimmking.rpcfx.api;

import io.kimmking.rpcfx.enums.BodyTypeEnum;

public class RpcfxRequest {

    private String serviceClass;

    private String method;

    private Object[] params;

    private BodyTypeEnum bodyType;

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public BodyTypeEnum getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyTypeEnum bodyType) {
        this.bodyType = bodyType;
    }
}
