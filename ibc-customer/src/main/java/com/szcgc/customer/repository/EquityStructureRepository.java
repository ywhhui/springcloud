package com.szcgc.customer.repository;

import com.szcgc.customer.model.EquityStructure;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EquityStructureRepository extends PagingAndSortingRepository<EquityStructure, Integer> {


    List<EquityStructure> getByProjectIdAndCustomerId(Integer projectId, Integer custId);
}
