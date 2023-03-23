package com.szcgc.finance.repository.base;

import com.szcgc.finance.model.base.BalanceSheet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BalanceSheetRepository extends PagingAndSortingRepository<BalanceSheet, Integer> {

    List<BalanceSheet> findByCustomerId(Integer custId);

    BalanceSheet findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(Integer projectId, Integer custId, String date);
}
