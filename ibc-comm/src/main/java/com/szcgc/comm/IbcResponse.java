package com.szcgc.comm;

/**
 * 通用response返回类
 * 这个和IbcPager其实更应该放在web层
 *
 * @param <T>
 */
public class IbcResponse<T> {

    private static final int OK200 = 200;
    private static final int ERROR400 = 400;    //请求错误
    private static final int ERROR401 = 401;    //未登录
    private static final int ERROR403 = 403;    //未授权
    private static final int ERROR500 = 500;    //内部错误
    private static final String OK_MSG = "ok";
    private static final IbcResponse<?> OK = IbcResponse.ok(null);
    public static final String ERROR_NO_AUTHORIZATION = "{\"code\":403,\"msg\":\"没有该接口访问权限\"}";
    public static final String ERROR_NO_AUTHENTICATION = "{\"code\":401,\"msg\":\"没有登录信息\"}";
    public static final String ERROR_NO_SERVER = "{\"code\":500,\"msg\":\"服务器内部异常\"}";

    public int code;
    public String msg;
    public T data;

    private IbcResponse() {
    }

    public static <T> IbcResponse<T> ok(T t) {
        IbcResponse<T> rst = new IbcResponse<T>();
        rst.code = OK200;
        rst.msg = OK_MSG;
        rst.data = t;
        return rst;
    }

    public static <T> IbcResponse<T> ok() {
        return (IbcResponse<T>) OK;
    }

    public static <T> IbcResponse<T> error400(String msg) {
        return error(ERROR400, msg);
    }

    public static <T> IbcResponse<T> error401(String msg) {
        return error(ERROR401, msg);
    }

    public static <T> IbcResponse<T> error403(String msg) {
        return error(ERROR403, msg);
    }

    public static <T> IbcResponse<T> error500(String msg) {
        return error(ERROR500, msg);
    }

    private static <T> IbcResponse<T> error(int code, String msg) {
        IbcResponse<T> rst = new IbcResponse<T>();
        rst.code = code;
        rst.msg = msg;
        return rst;
    }
}
