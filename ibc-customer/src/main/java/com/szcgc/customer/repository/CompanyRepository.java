package com.szcgc.customer.repository;

import com.szcgc.customer.model.Company;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Integer> {
    Company findByCustomerId(Integer custId);

    List<Company> findByTidIn(List<Long> tids);
}
