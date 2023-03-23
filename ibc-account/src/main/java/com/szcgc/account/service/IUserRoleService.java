package com.szcgc.account.service;

import com.szcgc.account.model.permission.UserRole;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/26 11:03
 */
public interface IUserRoleService extends IbcService<UserRole, Integer> {
    List<UserRole> findByAccountId(int accountId);

    List<UserRole> findBySysRoleId(int sysRoleId);

    int deleteByAccountId(int accountId);

}
