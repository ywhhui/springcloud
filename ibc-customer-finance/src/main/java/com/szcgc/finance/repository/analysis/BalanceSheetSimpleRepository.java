package com.szcgc.finance.repository.analysis;

import com.szcgc.finance.model.analysis.BalanceSheetSimple;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BalanceSheetSimpleRepository extends PagingAndSortingRepository<BalanceSheetSimple, Integer> {

    List<BalanceSheetSimple> findByMainId(Integer mainId);
}
