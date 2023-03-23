package com.szcgc.project.repository;

import com.szcgc.project.constant.BusinessTypeCateEnum;
import com.szcgc.project.model.CreditInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/25 17:35
 */
public interface CreditRepository extends PagingAndSortingRepository<CreditInfo, Integer> {

    List<CreditInfo> findByCustomerId(int customerId);

    List<CreditInfo> findByCustomerIdAndCate(int customerId, BusinessTypeCateEnum cate);

}
