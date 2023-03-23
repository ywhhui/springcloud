package com.szcgc.account.service.impl;

import com.szcgc.account.model.permission.SysResource;
import com.szcgc.account.repository.SysResourceRepository;
import com.szcgc.account.service.ISysResourceService;
import com.szcgc.comm.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/26 15:43
 */
@Service
public class SysResourceService extends BaseService<SysResourceRepository, SysResource, Integer> implements ISysResourceService {

    @Override
    public List<SysResource> findAll() {
        return (List<SysResource>) repository.findAll();
    }
}
