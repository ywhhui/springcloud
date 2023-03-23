package com.szcgc.finance.repository;

import com.szcgc.finance.model.CreditRating;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CreditRatingRepository extends PagingAndSortingRepository<CreditRating, Integer> {

    CreditRating findByProjectIdAndCustomerIdAndDate(Integer projectId, Integer custId, String date);

    void deleteByProjectIdAndCustomerId(Integer projectId, Integer customerId);
}
