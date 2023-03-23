package com.szcgc.finance.repository.base;

import com.szcgc.finance.model.base.IncomeStatement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IncomeStatementRepository extends PagingAndSortingRepository<IncomeStatement, Integer> {

    List<IncomeStatement> findByCustomerId(Integer custId);

    IncomeStatement findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(Integer projectId, Integer custId, String date);
}
