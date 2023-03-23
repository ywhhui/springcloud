package com.szcgc.account.controller;

import com.szcgc.account.model.AccountInfo;
import com.szcgc.account.service.IAccountService;
import com.szcgc.comm.IbcPager;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.IbcResult;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.szcgc.comm.util.SundryUtils.page;
import static com.szcgc.comm.util.SundryUtils.size;

/**
 * @Author liaohong
 * @create 2020/8/24 10:41
 */
@Api(tags = "账号管理")
@RestController
@RequestMapping("accounts")
public class AccountController {

    @Autowired
    IAccountService accountService;

    @Operation(summary = "退出")
    @PostMapping("logout")
    public IbcResponse<String> logout(HttpServletRequest request) {
        //accountProxy.logout(request);
        return IbcResponse.ok("ok");
    }

    @Operation(summary = "登录账号详情")
    @GetMapping("info")
    public IbcResponse<AccountInfo> info(@IbcId @Parameter(hidden = true) int accountId) {
        return detail(accountId);
    }

    @Operation(summary = "修改密码")
    @PutMapping("changepwd")
    public IbcResponse<String> changePassword(@IbcId @Parameter(hidden = true) int accountId,
                                              @RequestParam("oldpwd") String oldpwd,
                                              @RequestParam("newpwd") String password) {
        IbcResult<String> rst = accountService.modifyPwd(accountId, oldpwd, password);
        if (rst.isOk()) {
            return IbcResponse.ok();
        }
        return IbcResponse.error500(rst.getValue());
    }

    @Operation(summary = "修改头像")
    @PutMapping("changeavatar")
    public IbcResponse<String> changeAvatar(@IbcId @Parameter(hidden = true) int accountId,
                                            @RequestParam("newavatar") String avatar) {
        IbcResult<String> rst = accountService.modifyAvatar(accountId, avatar);
        if (rst.isOk()) {
            return IbcResponse.ok();
        }
        return IbcResponse.error500(rst.getValue());
    }

    @Operation(summary = "账号详情")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<AccountInfo> detail(@RequestParam("id") int id) {
        Optional<AccountInfo> account = accountService.find(id);
        if (account.isPresent()) {
            return IbcResponse.ok(account.get());
        }
        return IbcResponse.error400("未找到对应账号信息");
    }

    @Operation(summary = "锁定账号")
    @PutMapping("lock")
    public IbcResponse<String> lock(@RequestParam("accountId") int accountId) {
        IbcResult<String> rst = accountService.updateStatus(accountId, AccountInfo.STATUS_LOCK);
        if (rst.isOk()) {
            return IbcResponse.ok();
        }
        return IbcResponse.error500(rst.getValue());
    }

    @Operation(summary = "新增账号")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<String> add(@RequestParam("name") String name, @RequestParam("phone") String phone,
                                   @RequestParam("departmentId") int departmentId) {
        AccountInfo account = new AccountInfo();
        account.setName(name);
        account.setRealName(name);
        account.setPassword(phone);
        account.setDepartmentId(departmentId);
        account.setPassword(WebConstants.PASSWORD_DFT);
        accountService.insert(account);
        return IbcResponse.ok();
    }

    @Operation(summary = "重置密码")
    @PutMapping("resetpwd")
    public IbcResponse<String> resetPassword(@RequestParam("accountId") int accountId) {
        IbcResult<String> rst = accountService.resetPwd(accountId, WebConstants.PASSWORD_DFT);
        if (rst.isOk()) {
            return IbcResponse.ok();
        }
        return IbcResponse.error500(rst.getValue());
    }

    @Operation(summary = "账号列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<IbcPager<AccountInfo>> index(@RequestParam(name = WebConstants.PGNM, required = false) Integer page,
                                                    @RequestParam(name = WebConstants.PGSZ, required = false) Integer size) {
        Page<AccountInfo> accounts = accountService.findAll(page(page), size(size));
        return IbcResponse.ok(IbcPager.of(accounts));
    }

    //属于组织架构的范畴,挪到OrganizeController
    @Operation(summary = "某部门账号列表")
    @GetMapping("depart")
    public IbcResponse<List<AccountInfo>> depart(@RequestParam("id") int id) {
        List<AccountInfo> accounts = accountService.findByDepartmentId(id);
        return IbcResponse.ok(accounts);
    }

    @Operation(summary = "账号搜索")
    @RequestMapping(value = "search", method = {RequestMethod.POST, RequestMethod.GET})
    public IbcResponse<List<AccountInfo>> search(@RequestParam(name = "phone", required = false) String phone,
                                                 @RequestParam(name = "name", required = false) String name,
                                                 @RequestParam(name = "realname", required = false) String realname) {
        List<AccountInfo> accounts = new ArrayList<>();
        if (!StringUtils.isEmpty(phone)) {
            AccountInfo account = accountService.findByPhone(phone);
            if (account != null) {
                accounts.add(account);
            }
        } else if (!StringUtils.isEmpty(name)) {
            AccountInfo account = accountService.findByName(name);
            if (account != null) {
                accounts.add(account);
            }
        } else if (!StringUtils.isEmpty(realname)) {
            accounts = accountService.findByRealName(name);
        }
        return IbcResponse.ok(accounts);
    }

//    @Operation(summary = "按手机号查询")
//    @GetMapping("phone/{phone}")
//    public IbcResponse<AccountInfo> phone(@PathVariable String phone) {
//        AccountInfo account = accountService.findByPhone(phone);
//        if (account != null)
//            return IbcResponse.ok(account);
//        return IbcResponse.error400("未找到对应账号信息");
//    }
//
//    @Operation(summary = "按账号查询")
//    @GetMapping("name/{name}")
//    public IbcResponse<AccountInfo> name(@PathVariable String name) {
//        AccountInfo account = accountService.findByName(name);
//        if (account != null)
//            return IbcResponse.ok(account);
//        return IbcResponse.error400("未找到对应账号信息");
//    }
//
//    @Operation(summary = "按真实姓名查询")
//    @GetMapping("realname/{realname}")
//    public IbcResponse<List<AccountInfo>> realName(@PathVariable String realname) {
//        List<AccountInfo> accounts = accountService.findByRealName(realname);
//        return IbcResponse.ok(accounts);
//    }
}
