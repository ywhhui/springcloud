package com.szcgc.account.service.impl;

import com.szcgc.account.model.system.BusinessConfigInfo;
import com.szcgc.account.repository.BusinessCfgRepository;
import com.szcgc.account.service.IBusinessCfgService;
import com.szcgc.comm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author liaohong
 * @create 2020/9/4 16:08
 */
@Service
public class BusinessCfgService extends BaseService<BusinessCfgRepository, BusinessConfigInfo, Integer> implements IBusinessCfgService {

    @Autowired
    BusinessCfgRepository repository;

    @Override
    public BusinessConfigInfo findByBusinessType(String businessType) {
        return repository.findByBusinessType(Objects.requireNonNull(businessType));
    }
}
