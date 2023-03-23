package com.szcgc.account.repository;

import com.szcgc.account.model.system.AgreementTemplateInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author liaohong
 * @create 2020/8/17 18:17
 */
public interface AgreementTemplateRepository extends PagingAndSortingRepository<AgreementTemplateInfo, Integer> {
}
