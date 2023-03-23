package com.szcgc.gateway.config;

import com.szcgc.comm.constant.AppConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liaohong
 * @create 2022/9/29 20:33
 */
public class AuthConfig {

    public static final List<String> DEFAULT_SKIP_URL = new ArrayList<>();

    static {

        DEFAULT_SKIP_URL.add("/*/v2/api-docs/**");
        DEFAULT_SKIP_URL.add("/" + AppConstant.APPLICATION_AUTH_NAME + "/**");
        DEFAULT_SKIP_URL.add("/" + AppConstant.APPLICATION_FILE_NAME + "/**");

//        DEFAULT_SKIP_URL.add("/example");
//        DEFAULT_SKIP_URL.add("/token/**");
//        DEFAULT_SKIP_URL.add("/captcha/**");
//        DEFAULT_SKIP_URL.add("/actuator/health/**");
//        DEFAULT_SKIP_URL.add("/v2/api-docs/**");
//        DEFAULT_SKIP_URL.add("/auth/**");
//        DEFAULT_SKIP_URL.add("/oauth/**");
//        DEFAULT_SKIP_URL.add("/log/**");
//        DEFAULT_SKIP_URL.add("/menu/routes");
//        DEFAULT_SKIP_URL.add("/menu/auth-routes");
//        DEFAULT_SKIP_URL.add("/tenant/info");
//        DEFAULT_SKIP_URL.add("/order/create/**");
//        DEFAULT_SKIP_URL.add("/storage/deduct/**");
//        DEFAULT_SKIP_URL.add("/error/**");
//        DEFAULT_SKIP_URL.add("/assets/**");
    }

}
