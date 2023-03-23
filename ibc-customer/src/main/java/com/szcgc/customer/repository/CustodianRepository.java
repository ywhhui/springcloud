package com.szcgc.customer.repository;

import com.szcgc.customer.model.Custodian;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustodianRepository extends PagingAndSortingRepository<Custodian, Integer> {

    List<Custodian> findByCustomerIdIn(List<Integer> custIds);

    Custodian findTop1ByCustomerId(Integer custId);

    List<Custodian> findByCustodianNameContaining(String custodianName);
}
