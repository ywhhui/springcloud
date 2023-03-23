package com.szcgc.account.repository;

import com.szcgc.account.model.DepartmentInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author liaohong
 * @create 2020/8/17 18:14
 */
public interface DepartmentRepository extends PagingAndSortingRepository<DepartmentInfo, Integer> {
}
