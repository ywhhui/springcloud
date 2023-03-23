package com.szcgc.finance.repository.analysis;

import com.szcgc.finance.model.analysis.FinanceAnalysis;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FinanceAnalysisRepository extends PagingAndSortingRepository<FinanceAnalysis, Integer> {

    FinanceAnalysis findTop1ByProjectIdAndCustomerIdOrderByCreateAtDesc(Integer projectId, Integer custId);

}
