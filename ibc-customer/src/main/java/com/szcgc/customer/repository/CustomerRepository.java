package com.szcgc.customer.repository;

import com.szcgc.customer.model.CustomerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<CustomerInfo, Integer> {

    Page<CustomerInfo> findByNameLike(String name, Pageable page);

    Page<CustomerInfo> findByNameLikeAndCate(String name, int cate, Pageable page);

    List<CustomerInfo> findByName(String name);

    List<CustomerInfo> findByIdInAndNameContaining(List<Integer> ids, String name);

    Page<CustomerInfo> findByNameContainingOrderByUpdateAtDesc(String custName, Pageable dftPage);

    List<CustomerInfo> findByIdNoIn(List<String> ids);

    Page<CustomerInfo> findByNameContainingAndIdInOrderByUpdateAtDesc(String custName, List<Integer> custIds, Pageable dftPage);

    CustomerInfo findByIdNo(String idNo);
}
