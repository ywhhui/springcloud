package com.szcgc.project.service;

import com.szcgc.project.constant.FeeTypeEnum;
import com.szcgc.project.constant.ProjectStatusEnum;
import com.szcgc.project.model.ProjectFee;

import java.util.List;

/**
 * @Author: chenxinli
 * @Date: 2020/8/24 20:42
 * @Description:
 */
public interface IProjectFeeService extends IProjectSuperFind<ProjectFee, Integer> {

    //List<ProjectFee> findByProjectId(int projectId);
    boolean isCollected(List<ProjectFee> projectFees, int projectId);

    List<ProjectFee> findByStatus(int projectId, ProjectStatusEnum status);

    List<ProjectFee> findByType(int projectId, FeeTypeEnum type);

    ProjectFee findLastType(int projectId, FeeTypeEnum type);

    boolean hasDeposit(int projectId);

    List<ProjectFee> findByProjectIdAndProjectEvaluateInfoId(int projectId, int projectEvaluateInfoId);

    ProjectFee findBySoucreProjectId(int projectId);

}
