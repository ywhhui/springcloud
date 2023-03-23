package com.szcgc.account.feign;

import com.szcgc.account.constant.AccountRoleEnum;
import com.szcgc.account.model.AccountInfo;
import com.szcgc.account.model.DepartmentInfo;
import com.szcgc.account.model.OrganizationInfo;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/9/26 17:44
 */
public class IAccountClientFallback implements IAccountClient {

    @Override
    public AccountInfo findAccount(int accountId) {
        return null;
    }

    @Override
    public DepartmentInfo findDepart(int departmentId) {
        return null;
    }

    @Override
    public AccountInfo login(String name, String password) {
        return null;
    }

    @Override
    public List<OrganizationInfo> findOrganize(AccountRoleEnum roleId) {
        return null;
    }
}
