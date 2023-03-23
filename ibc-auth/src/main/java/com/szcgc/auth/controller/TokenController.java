package com.szcgc.auth.controller;

import com.szcgc.account.feign.IAccountClient;
import com.szcgc.account.model.AccountInfo;
import com.szcgc.auth.util.TokenUtil;
import com.szcgc.auth.vo.LoginVo;
import com.szcgc.comm.IbcResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liaohong
 * @create 2022/9/20 14:14
 */
@RestController
public class TokenController {

    @Autowired
    IAccountClient accountClient;

    @PostMapping("login")
    public IbcResponse<LoginVo> login(@RequestParam("userName") String name, @RequestParam("password") String password) {
        if (name == null || password == null) {
            return IbcResponse.error400("请求参数错误");
        }
        AccountInfo account = accountClient.login(name, password);
        if (account == null) {
            return IbcResponse.error400("用户名或密码错误");
        }
        if (!account.isEnable()) {
            return IbcResponse.error400("该账号无法使用,有疑问请联系管理员");
        }
        String token = TokenUtil.makeToken(String.valueOf(account.getId()));
        LoginVo vo = new LoginVo();
        vo.accessToken = token;
        return IbcResponse.ok(vo);
    }


}
