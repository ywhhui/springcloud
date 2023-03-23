package com.szcgc.account.service.impl;

import com.szcgc.account.model.permission.SysRole;
import com.szcgc.account.repository.SysRoleRepository;
import com.szcgc.account.service.ISysRoleService;
import com.szcgc.comm.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/26 15:43
 */
@Service
public class SysRoleService extends BaseService<SysRoleRepository, SysRole, Integer> implements ISysRoleService {

    @Override
    public List<SysRole> findAll() {
        return (List<SysRole>) repository.findAll();
    }

}
