package com.szcgc.customer.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.customer.model.Company;
import com.szcgc.customer.repository.CompanyRepository;
import com.szcgc.customer.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class CompanyServiceImpl extends BaseService<CompanyRepository, Company, Integer> implements CompanyService {

    @Override
    public Company findByCustId(Integer custId) {
        return repository.findByCustomerId(custId);
    }

    @Override
    public List<Company> findByTidIn(List<Long> tids) {
        return repository.findByTidIn(tids);
    }
}
