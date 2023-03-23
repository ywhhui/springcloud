package com.szcgc.finance.repository.analysis;

import com.szcgc.finance.model.analysis.IncomeStatementSimple;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IncomeStatementSimpleRepository extends PagingAndSortingRepository<IncomeStatementSimple, Integer> {

    List<IncomeStatementSimple> findByMainId(Integer mainId);
}
