package com.szcgc.account.service.impl;

import com.szcgc.account.model.BranchInfo;
import com.szcgc.account.repository.BranchRepository;
import com.szcgc.account.service.IBranchService;
import com.szcgc.comm.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liaohong
 * @create 2021/3/19 10:38
 */
@Service
public class BranchService extends BaseService<BranchRepository, BranchInfo, Integer> implements IBranchService {

    @Override
    public List<BranchInfo> findAll() {
        return (List<BranchInfo>) repository.findAll();
    }

}
