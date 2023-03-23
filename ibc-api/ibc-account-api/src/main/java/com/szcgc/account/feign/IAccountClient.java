package com.szcgc.account.feign;

import com.szcgc.account.constant.AccountRoleEnum;
import com.szcgc.account.model.AccountInfo;
import com.szcgc.account.model.DepartmentInfo;
import com.szcgc.account.model.OrganizationInfo;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.constant.AppConstant;
import com.szcgc.comm.model.IbcTree;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2022/9/26 17:23
 */
@FeignClient(
        value = AppConstant.APPLICATION_ACCOUNT_NAME,
        fallback = IAccountClientFallback.class
)
public interface IAccountClient {

    String API_PREFIX = "/account";
    String ACCOUNT = API_PREFIX + "/account";
    String DEPART = API_PREFIX + "/depart";
    String LOGIN = API_PREFIX + "/login";
    String ORGANIZE = API_PREFIX + "/organize";

    @GetMapping(ACCOUNT)
    AccountInfo findAccount(@RequestParam("accountId") int accountId);

    @GetMapping(DEPART)
    DepartmentInfo findDepart(@RequestParam("departmentId") int departmentId);

    @PostMapping(LOGIN)
    AccountInfo login(@RequestParam("userName") String name, @RequestParam("password") String password);

    @GetMapping(ORGANIZE)
    List<OrganizationInfo> findOrganize(@RequestParam("roleId") AccountRoleEnum roleId);

}
