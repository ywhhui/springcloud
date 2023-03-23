package com.szcgc.project.repository;

import com.szcgc.project.model.AnnotateInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/11/12 16:38
 */
public interface AnnotateRepository extends PagingAndSortingRepository<AnnotateInfo, Integer> {

    List<AnnotateInfo> findByProjectId(int projectId);

    AnnotateInfo findFirstByProjectIdOrderByIdDesc(int projectId);

}
