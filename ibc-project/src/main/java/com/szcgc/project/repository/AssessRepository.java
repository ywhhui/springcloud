package com.szcgc.project.repository;

import com.szcgc.project.model.AssessInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2021/6/3 17:08
 */
public interface AssessRepository extends PagingAndSortingRepository<AssessInfo, Integer> {

    List<AssessInfo> findByProjectIdAndAccountId(int projectId, int accountId);

    Page<AssessInfo> findByAccountId(int accountId, Pageable page);

}
