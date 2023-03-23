package com.szcgc.customer.repository;

import com.szcgc.customer.model.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Integer> {
    Person findByCustomerId(Integer custId);
}
