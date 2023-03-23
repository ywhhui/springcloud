package com.szcgc.account.service.impl;

import com.szcgc.account.model.permission.UserRole;
import com.szcgc.account.repository.UserRoleRepository;
import com.szcgc.account.service.IUserRoleService;
import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.SundryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/26 15:43
 */
@Service
public class UserRoleService extends BaseService<UserRoleRepository, UserRole, Integer> implements IUserRoleService {

    @Autowired
    UserRoleRepository repository;

    @Override
    public List<UserRole> findByAccountId(int accountId) {
        return repository.findByAccountId(SundryUtils.requireId(accountId));
    }

    @Override
    public List<UserRole> findBySysRoleId(int sysRoleId) {
        return repository.findBySysRoleId(SundryUtils.requireId(sysRoleId));
    }

    @Override
    public int deleteByAccountId(int accountId) {
        return repository.deleteByAccountId(SundryUtils.requireId(accountId));
    }

}
