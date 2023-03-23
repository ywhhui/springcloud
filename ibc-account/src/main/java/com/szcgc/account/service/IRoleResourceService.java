package com.szcgc.account.service;

import com.szcgc.account.model.permission.RoleResource;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/26 10:54
 */
public interface IRoleResourceService extends IbcService<RoleResource, Integer> {

    List<RoleResource> findBySysRoleId(int sysRoleId);

    List<Integer> findSysResourceIds(Integer[] sysRoleIds);

    int deleteBySysRoleId(int sysRoleId);
}
