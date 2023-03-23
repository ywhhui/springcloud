package com.szcgc.account.repository;

import com.szcgc.account.model.system.BusinessConfigInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author liaohong
 * @create 2020/9/4 16:08
 */
public interface BusinessCfgRepository extends PagingAndSortingRepository<BusinessConfigInfo, Integer> {

    BusinessConfigInfo findByBusinessType(String businessType);
}
