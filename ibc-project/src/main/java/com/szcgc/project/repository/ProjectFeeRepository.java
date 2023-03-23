package com.szcgc.project.repository;

import com.szcgc.project.constant.FeeTypeEnum;
import com.szcgc.project.model.ProjectFee;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author: chenxinli
 * @Date: 2020/8/24 20:41
 * @Description:
 */
public interface ProjectFeeRepository extends PagingAndSortingRepository<ProjectFee, Integer> {

    List<ProjectFee> findByProjectId(int projectId);

    List<ProjectFee> findByProjectIdAndFeeTypeEnum(int projectId, FeeTypeEnum type);

    List<ProjectFee> findByProjectIdAndProjectEvaluateInfoId(int projectId, int projectEvaluateInfoId);

    ProjectFee findFirstByProjectIdAndFeeTypeEnumOrderByIdDesc(int projectId, FeeTypeEnum type);

    ProjectFee findBySoucreProjectId(int soucreProjectId);

}
