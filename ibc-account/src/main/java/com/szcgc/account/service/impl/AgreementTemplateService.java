package com.szcgc.account.service.impl;

import com.szcgc.account.model.system.AgreementTemplateInfo;
import com.szcgc.account.repository.AgreementTemplateRepository;
import com.szcgc.account.service.IAgreementTemplateService;
import com.szcgc.comm.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/17 19:36
 */
@Service
public class AgreementTemplateService extends BaseService<AgreementTemplateRepository, AgreementTemplateInfo, Integer> implements IAgreementTemplateService {

    @Override
    public List<AgreementTemplateInfo> findAll() {
        return (List<AgreementTemplateInfo>) repository.findAll();
    }
}
