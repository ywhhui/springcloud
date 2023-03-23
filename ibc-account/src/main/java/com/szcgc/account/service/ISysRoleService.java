package com.szcgc.account.service;

import com.szcgc.account.model.permission.SysRole;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/26 10:55
 */
public interface ISysRoleService extends IbcService<SysRole, Integer> {

    List<SysRole> findAll();

}
