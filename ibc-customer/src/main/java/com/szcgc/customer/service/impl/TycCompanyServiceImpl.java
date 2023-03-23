package com.szcgc.customer.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.customer.model.TycCompany;
import com.szcgc.customer.repository.TycCompanyRepository;
import com.szcgc.customer.service.TycCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TycCompanyServiceImpl extends BaseService<TycCompanyRepository, TycCompany, Integer> implements TycCompanyService {

    @Override
    public TycCompany findTop1ByIdNoOrderByCreateAtDesc(String idNo) {
        return repository.findTop1ByIdNoOrderByCreateAtDesc(idNo);
    }
}
