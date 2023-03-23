package com.szcgc.project.repository;

import com.szcgc.project.model.ConclusionInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/18 10:21
 */
public interface ConclusionRepository extends CrudRepository<ConclusionInfo, Integer> {

    List<ConclusionInfo> findByProjectId(int projectId);

    ConclusionInfo findFirstByProjectIdOrderByIdDesc(int projectId);
}
