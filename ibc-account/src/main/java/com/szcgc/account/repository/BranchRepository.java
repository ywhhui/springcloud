package com.szcgc.account.repository;

import com.szcgc.account.model.BranchInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author liaohong
 * @create 2021/3/19 10:34
 */
public interface BranchRepository extends PagingAndSortingRepository<BranchInfo, Integer> {
}
