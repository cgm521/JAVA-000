package io.kimmking.rpcfx.api;

public class RpcfxResponse {

    private Object result;

    private boolean status;

    private RpcfxException exception;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public RpcfxException getException() {
        return exception;
    }

    public void setException(RpcfxException exception) {
        this.exception = exception;
    }

    public static RpcfxResponse createFail(RpcfxException exception) {
        RpcfxResponse response = new RpcfxResponse();
        response.setStatus(false);
        response.setException(exception);
        return response;
    }
}
