package com.szcgc.project.repository;

import com.szcgc.project.model.EvaluateInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/18 10:20
 */
public interface EvaluateRepository extends PagingAndSortingRepository<EvaluateInfo, Integer> {

    List<EvaluateInfo> findByProjectId(int projectId);

    EvaluateInfo findFirstByProjectIdOrderByIdDesc(int projectId);

    EvaluateInfo findByProjectIdAndTaskId(int projectId, String taskId);

    EvaluateInfo findByProjectIdAndTimes(int projectId, int times);

    int countByProjectId(int projectId);
}
