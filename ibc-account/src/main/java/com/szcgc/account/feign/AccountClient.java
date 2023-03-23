package com.szcgc.account.feign;

import com.szcgc.account.constant.AccountRoleEnum;
import com.szcgc.account.model.AccountInfo;
import com.szcgc.account.model.DepartmentInfo;
import com.szcgc.account.model.OrganizationInfo;
import com.szcgc.account.service.IAccountService;
import com.szcgc.account.service.IDepartmentService;
import com.szcgc.account.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @Author liaohong
 * @create 2022/9/26 17:46
 */
@RestController
public class AccountClient implements IAccountClient {

    @Autowired
    IAccountService accountService;

    @Autowired
    IDepartmentService departmentService;

    @Autowired
    IOrganizationService organizationService;

    @Override
    @GetMapping(ACCOUNT)
    public AccountInfo findAccount(@RequestParam("accountId") int accountId) {
        Optional<AccountInfo> account = accountService.find(accountId);
        if (!account.isPresent())
            return null;
        return account.get();
    }

    @Override
    @GetMapping(DEPART)
    public DepartmentInfo findDepart(@RequestParam("departmentId") int departmentId) {
        Optional<DepartmentInfo> depart = departmentService.find(departmentId);
        if (!depart.isPresent())
            return null;
        return depart.get();
    }

    @Override
    @PostMapping(LOGIN)
    public AccountInfo login(@RequestParam("userName") String name, @RequestParam("password") String password) {
        return accountService.login(name, password);
    }

    @Override
    @GetMapping(ORGANIZE)
    public List<OrganizationInfo> findOrganize(@RequestParam("roleId") AccountRoleEnum roleId) {
        return organizationService.findByRoleId(AccountRoleEnum.AssetAssessor);
    }

}
