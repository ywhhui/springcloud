package com.szcgc.account.service.impl;

import com.szcgc.account.constant.AccountRoleEnum;
import com.szcgc.account.model.OrganizationInfo;
import com.szcgc.account.repository.OrganizationRepository;
import com.szcgc.account.service.IOrganizationService;
import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.SundryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2020/8/17 19:36
 */
@Service
public class OrganizationService  extends BaseService<OrganizationRepository, OrganizationInfo, Integer> implements IOrganizationService {

    List<OrganizationInfo> infos;
    Map<AccountRoleEnum, List<OrganizationInfo>> oranizations;

    @Override
    public void init() {
        infos = (List<OrganizationInfo>) repository.findAll();
        oranizations = infos.stream().filter(item -> item.getAccountId() > 0).collect(Collectors.groupingBy(OrganizationInfo::getRoleId));
    }

    @Override
    public List<OrganizationInfo> findAll() {
        if (infos == null) {
            init();
        }
        return infos;
    }

    @Override
    public List<OrganizationInfo> findByAccountId(int accountId) {
        if (infos == null) {
            init();
        }
        if (infos != null) {
            return infos.stream().filter(item -> item.getAccountId() == accountId).collect(Collectors.toList());
        }
        return repository.findByAccountId(SundryUtils.requireId(accountId));
    }

    @Override
    public List<OrganizationInfo> findByRoleId(AccountRoleEnum roleId) {
        if (oranizations == null) {
            init();
        }
        if (oranizations != null) {
            return oranizations.get(roleId);
        }
        return repository.findByRoleId(roleId);
    }

    @Override
    public List<OrganizationInfo> findByBusinessType(String businessType) {
        if (infos == null) {
            init();
        }
        if (infos != null) {
            return infos.stream().filter(item -> businessType.equals(item.getBusinessType())).collect(Collectors.toList());
        }
        return repository.findByBusinessType(Objects.requireNonNull(businessType));
    }
}
