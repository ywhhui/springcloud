package com.szcgc.cougua.repository;

import com.szcgc.cougua.model.IndividualInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/1 17:10
 */
public interface IndividualRepository extends PagingAndSortingRepository<IndividualInfo, Integer> {

    List<IndividualInfo> findByProjectId(int projectId);

}
