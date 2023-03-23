package com.szcgc.account.repository;

import com.szcgc.account.constant.AccountRoleEnum;
import com.szcgc.account.model.OrganizationInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/17 18:17
 */
public interface OrganizationRepository extends PagingAndSortingRepository<OrganizationInfo, Integer> {

    List<OrganizationInfo> findByAccountId(int accountId);

    List<OrganizationInfo> findByRoleId(AccountRoleEnum roleId);

    //List<OrganizationInfo> findByDepartmentId(int departmentId);

    List<OrganizationInfo> findByBusinessType(String businessType);
}
