package io.kimmking.rpcfx.api;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/13 5:18 下午
 */

public class RpcfxException extends Exception {

    private ErrorCode errorCode;

    public RpcfxException() {
    }

    public RpcfxException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public static RpcfxException creatFailException(ErrorCode errorCode, Exception e) {
        return new RpcfxException(e, errorCode);
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public enum ErrorCode {
        SYS_ERROR("SYS_ERROR", "系统错误"),
        CLASS_NOT_FIND("CLASS_NOT_FIND", "classNotFind"),
        REQUEST_EXCEPTION("REQUEST_EXCEPTION", "请求异常"),

        ;
        private String code;
        private String message;

        ErrorCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
