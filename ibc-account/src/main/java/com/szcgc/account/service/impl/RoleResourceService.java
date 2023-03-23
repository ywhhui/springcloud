package com.szcgc.account.service.impl;

import com.szcgc.account.model.permission.RoleResource;
import com.szcgc.account.repository.RoleResourceRepository;
import com.szcgc.account.service.IRoleResourceService;
import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.SundryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author liaohong
 * @create 2020/8/26 15:42
 */
@Service
public class RoleResourceService extends BaseService<RoleResourceRepository, RoleResource, Integer> implements IRoleResourceService {

    @Override
    public List<RoleResource> findBySysRoleId(int sysRoleId) {
        return repository.findBySysRoleId(SundryUtils.requireId(sysRoleId));
    }

    @Override
    public List<Integer> findSysResourceIds(Integer[] sysRoleIds) {
        return repository.findSysResourceIds(Objects.requireNonNull(sysRoleIds));
    }

    @Override
    public int deleteBySysRoleId(int sysRoleId) {
        return repository.deleteBySysRoleId(SundryUtils.requireId(sysRoleId));
    }

}
