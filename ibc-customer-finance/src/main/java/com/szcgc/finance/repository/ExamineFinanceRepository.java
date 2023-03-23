package com.szcgc.finance.repository;

import com.szcgc.finance.model.ExamineFinanceInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author liaohong
 * @create 2020/9/1 17:10
 */
public interface ExamineFinanceRepository extends PagingAndSortingRepository<ExamineFinanceInfo, Integer> {

    ExamineFinanceInfo save(ExamineFinanceInfo info);

}
