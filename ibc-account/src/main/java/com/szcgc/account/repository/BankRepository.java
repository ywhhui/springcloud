package com.szcgc.account.repository;

import com.szcgc.account.model.system.BankInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author liaohong
 * @create 2020/9/23 15:32
 */
public interface BankRepository extends PagingAndSortingRepository<BankInfo, Integer> {
}
