package com.szcgc.finance.repository.analysis;

import com.szcgc.finance.model.analysis.CashFlowStatementSimple;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CashFlowStatementSimpleRepository extends PagingAndSortingRepository<CashFlowStatementSimple, Integer> {

    List<CashFlowStatementSimple> findByMainId(Integer mainId);
}
