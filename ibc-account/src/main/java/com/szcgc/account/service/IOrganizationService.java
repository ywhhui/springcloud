package com.szcgc.account.service;

import com.szcgc.account.constant.AccountRoleEnum;
import com.szcgc.account.model.OrganizationInfo;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/17 18:07
 */
public interface IOrganizationService extends IbcService<OrganizationInfo, Integer> {

    void init();

    List<OrganizationInfo> findAll();

    List<OrganizationInfo> findByAccountId(int accountId);

    List<OrganizationInfo> findByRoleId(AccountRoleEnum roleId);

    //List<OrganizationInfo> findByDepartmentId(int departmentId);

    List<OrganizationInfo> findByBusinessType(String businessType);
}
