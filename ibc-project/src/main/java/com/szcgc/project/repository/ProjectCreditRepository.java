package com.szcgc.project.repository;

import com.szcgc.project.model.ProjectCreditInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2021/1/14 15:22
 */
public interface ProjectCreditRepository extends PagingAndSortingRepository<ProjectCreditInfo, Integer> {

    ProjectCreditInfo findFirstByProjectIdOrderByIdDesc(int projectId);

    List<ProjectCreditInfo> findByCreditIdAndPhaseValue(int creditId, int phaseValue);

    Page<ProjectCreditInfo> findByCreditIdAndPhaseValue(int creditId, int phaseValue, Pageable page);
}
