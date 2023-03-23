package com.szcgc.customer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.szcgc.account.feign.IAccountClient;
import com.szcgc.account.model.AccountInfo;
import com.szcgc.comm.service.BaseService;
import com.szcgc.customer.model.Custodian;
import com.szcgc.customer.model.CustomerInfo;
import com.szcgc.customer.repository.CustodianRepository;
import com.szcgc.customer.repository.CustomerRepository;
import com.szcgc.customer.service.CustodianService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@Service
public class CustodianServiceImpl extends BaseService<CustodianRepository, Custodian, Integer> implements CustodianService {

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private IAccountClient accountClient;

    @Override
    public List<Custodian> findByCustomerIdIn(List<Integer> custIds) {
        return repository.findByCustomerIdIn(custIds);
    }

    @Override
    public void edit(int accountId, Integer custId, Integer custodianId) {
        // 获取客户信息
        CustomerInfo customerInfo = customerRepository.findById(custId).orElse(CustomerInfo.builder().build());
        // 获取管户信息
        Custodian custodian = repository.findTop1ByCustomerId(custId);
        // 获取用户信息
        AccountInfo accountInfo = accountClient.findAccount(custodianId);

        if (ObjectUtil.isNull(custodian)) {
            custodian = Custodian.builder()
                    .custodianId(custodianId)
                    .custodianName(accountInfo.getRealName())
                    .customerId(custId)
                    .customerName(customerInfo.getName())
                    .build();
            custodian.setCreateBy(accountId);
        } else {
            custodian.setCustodianId(custodianId);
            custodian.setCustodianName(accountInfo.getRealName());
        }
        custodian.setUpdateBy(accountId);
        repository.save(custodian);
    }

    @Override
    public List<Custodian> findByCustodianNameContaining(String custodianName) {
        return repository.findByCustodianNameContaining(custodianName);
    }

    @Override
    public Custodian findTop1ByCustomerId(Integer custId) {
        return repository.findTop1ByCustomerId(custId);
    }
}
