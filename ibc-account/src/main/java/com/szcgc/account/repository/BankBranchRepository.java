package com.szcgc.account.repository;

import com.szcgc.account.model.system.BankBranchInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/23 15:32
 */
public interface BankBranchRepository extends PagingAndSortingRepository<BankBranchInfo, Integer> {

    List<BankBranchInfo> findByBankId(int bankId);

    BankBranchInfo findFirstByBankIdOrderByIdDesc(int bankId);
}
