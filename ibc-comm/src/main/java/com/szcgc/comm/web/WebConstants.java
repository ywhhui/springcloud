package com.szcgc.comm.web;

/**
 * @Author liaohong
 * @create 2020/8/24 17:50
 */
public class WebConstants {

    /**
     * URL_API:api接口
     */
    public static final String URL_API = "/api/**";

    /**
     * URL_LOGIN:登录页面
     */
    public static final String URL_LOGIN = "/accounts/login";
    public static final String URL_LOGIN_CHECK = "/accounts/check";

    public static final String AUTH_KEY = "accessToken";
    public static final String HEADER_UID = "ibc_user_id";

    /**
     * 初始密码
     */
    public static final String PASSWORD_DFT = "123456";
    public static final String INDEX = "/list";  //列表页
    public static final String INSERT = "/insert";  //新增
    public static final String BATCH_INSERT = "/batch-insert";  //批量新增
    public static final String DETAIL = "/detail";  //详情
    public static final String UPDATE = "/update";  //编辑
    public static final String BATCH_UPDATE = "/batch-update";  //批量编辑
    public static final String DELETE = "/delete";   //删除
    public static final String PGNM = "pgnum";   //当前页数
    public static final String PGSZ = "pgsize";   //每页条数
    public static final String KEYS = "keys";   //搜索关键词

    //public static final int PGSZ_DFT = 10;   //每页条数
}
