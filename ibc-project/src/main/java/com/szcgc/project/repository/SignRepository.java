package com.szcgc.project.repository;

import com.szcgc.project.model.SignInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/12/4 17:56
 */
public interface SignRepository extends PagingAndSortingRepository<SignInfo, Integer> {

    List<SignInfo> findByProjectId(int projectId);

    SignInfo findFirstByProjectIdOrderByIdDesc(int projectId);
}
