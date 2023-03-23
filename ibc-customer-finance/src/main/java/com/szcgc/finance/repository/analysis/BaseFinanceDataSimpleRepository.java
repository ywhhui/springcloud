package com.szcgc.finance.repository.analysis;

import com.szcgc.finance.model.analysis.BaseFinanceDataSimple;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BaseFinanceDataSimpleRepository extends PagingAndSortingRepository<BaseFinanceDataSimple, Integer> {

    List<BaseFinanceDataSimple> findByMainId(Integer mainId);

}
