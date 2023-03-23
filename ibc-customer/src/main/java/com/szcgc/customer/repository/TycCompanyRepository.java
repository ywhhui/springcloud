package com.szcgc.customer.repository;

import com.szcgc.customer.model.TycCompany;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TycCompanyRepository extends PagingAndSortingRepository<TycCompany, Integer> {

    TycCompany findTop1ByIdNoOrderByCreateAtDesc(String idNo);

}
