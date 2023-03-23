package com.szcgc.project.repository;

import com.szcgc.project.model.ReviewInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/10/14 16:03
 */
public interface ReviewRepository extends PagingAndSortingRepository<ReviewInfo, Integer> {

    List<ReviewInfo> findByProjectId(int projectId);

    ReviewInfo findFirstByProjectIdAndStopReasonIsNullOrderByIdDesc(int projectId);

    ReviewInfo findFirstByProjectIdAndStopReasonIsNotNullOrderByIdDesc(int projectId);
}
