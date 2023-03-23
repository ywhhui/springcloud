package com.szcgc.account.service.impl;

import com.szcgc.account.model.system.BankInfo;
import com.szcgc.account.repository.BankRepository;
import com.szcgc.account.service.IBankService;
import com.szcgc.comm.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author liaohong
 * @create 2020/9/23 15:38
 */
@Service
public class BankService extends BaseService<BankRepository, BankInfo, Integer> implements IBankService {

    @Override
    public List<BankInfo> findAll() {
        return (List<BankInfo>) repository.findAll();
    }

    @Override
    public String findName(int id) {
        if (id <= 0)
            return null;
        Optional<BankInfo> optional = find(id);
        if (optional.isPresent())
            return optional.get().getFullName();
        return null;
    }
}
