package com.szcgc.finance.repository.base;

import com.szcgc.finance.model.base.CashFlowStatement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CashFlowStatementRepository extends PagingAndSortingRepository<CashFlowStatement, Integer> {

    List<CashFlowStatement> findByCustomerId(Integer custId);

    CashFlowStatement findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(Integer projectId, Integer custId, String date);
}
