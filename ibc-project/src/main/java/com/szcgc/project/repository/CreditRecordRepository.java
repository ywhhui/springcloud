package com.szcgc.project.repository;

import com.szcgc.project.model.CreditRecordInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author: liaohong
 * @Date: 2020/8/7 17:20
 * @Description:
 */
public interface CreditRecordRepository extends PagingAndSortingRepository<CreditRecordInfo, Integer> {

    List<CreditRecordInfo> findByCreditId(int creditId);

    Page<CreditRecordInfo> findByCreditId(int creditId, Pageable page);
}
