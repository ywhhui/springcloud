package com.szcgc.account.service.impl;

import com.szcgc.account.model.system.BankBranchInfo;
import com.szcgc.account.model.system.BankInfo;
import com.szcgc.account.repository.BankBranchRepository;
import com.szcgc.account.repository.BankRepository;
import com.szcgc.account.service.IBankBranchService;
import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.SundryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author liaohong
 * @create 2020/9/23 15:38
 */
@Service
public class BankBranchService extends BaseService<BankBranchRepository, BankBranchInfo, Integer> implements IBankBranchService {

    @Autowired
    BankRepository bankRepository;

    @Override
    public List<BankBranchInfo> findAll() {
        return (List<BankBranchInfo>) repository.findAll();
    }

    @Override
    public List<BankBranchInfo> findByBankId(int bankId) {
        return repository.findByBankId(SundryUtils.requireId(bankId));
    }

    @Override
    public String findName(int id) {
        if (id <= 0)
            return null;
        Optional<BankBranchInfo> optional = find(id);
        if (optional.isPresent()) {
            Optional<BankInfo> optionalBankInfo = bankRepository.findById(optional.get().getBankId());
            if (optionalBankInfo.isPresent()) {
                return optionalBankInfo.get().getFullName() + "-" + optional.get().getName();
            } else {
                return optional.get().getName();
            }
        }
        return null;
    }

    @Override
    public BankBranchInfo insert(BankBranchInfo entity) {
        Objects.requireNonNull(entity);
        BankBranchInfo info = repository.findFirstByBankIdOrderByIdDesc(entity.getBankId());
        int seriesId = 1;
        if (info != null) {
            seriesId = info.getId() % 1000 + 1;
        }
        int id = entity.getBankId() * 1000 + seriesId;
        entity.setId(id);
        return super.insert(entity);
    }

    @Override
    public String getZiJinBankName(int bankBranchId) {
        BankBranchInfo bankBranchInfo = repository.findById(bankBranchId).get();
        int bankId = bankBranchInfo.getBankId();
        String bankName = "";
        if (bankId == 997) {
            bankName = bankBranchInfo.getName();
        } else {
            bankName = bankRepository.findById(bankId).get().getFullName();
        }
        return bankName;
    }
}
