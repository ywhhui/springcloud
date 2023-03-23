package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.model.ProjectCreditInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/17 17:03
 */
public interface IProjectCreditService extends IbcService<ProjectCreditInfo, Integer> {

    ProjectCreditInfo findLastByProjectId(int projectId);

    boolean credit(int projectId, int creditId, int phase);

    List<ProjectCreditInfo> findByCreditIdAndPhaseValue(int creditId, int phaseValue);

    Page<ProjectCreditInfo> findByCreditIdAndPhaseValue(int creditId, int phaseValue, int pageNo, int pageSize);

}
