package com.szcgc.finance.repository.analysis;

import com.szcgc.finance.model.analysis.FinanceAnalysisSimple;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FinanceAnalysisSimpleRepository extends PagingAndSortingRepository<FinanceAnalysisSimple, Integer> {

    List<FinanceAnalysisSimple> findByMainId(Integer mainId);

}
