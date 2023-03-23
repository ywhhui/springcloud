package com.szcgc.cougua.repository;

import com.szcgc.cougua.model.MaterialSubmitInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author liaohong
 * @create 2020/9/1 17:10
 */
public interface MaterialSubmitRepository extends PagingAndSortingRepository<MaterialSubmitInfo, Integer> {

    MaterialSubmitInfo findByProjectId(int projectId);

}
