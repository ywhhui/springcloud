package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.constant.BusinessTypeCateTopEnum;
import com.szcgc.project.constant.BusinessTypeEnum;
import com.szcgc.project.constant.EnterprisesTypeEnum;
import com.szcgc.project.constant.ProjectStatusEnum;
import com.szcgc.project.model.ProjectInfo;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IProjectService extends IbcService<ProjectInfo, Integer> {

    Page<ProjectInfo> findCustomer(int customerId, int pageNo, int pageSize);

    Page<ProjectInfo> findCustomer(int customerId, BusinessTypeCateTopEnum top, int pageNo, int pageSize);

    Page<ProjectInfo> findByProjectCode(String projectCode, int pageNo, int pageSize);

    ProjectStatusEnum findStatus(int projectId);

    String findCode(int projectId);

    int findCustomerId(int projectId);

    long sumAmount(int customerId, ProjectStatusEnum status);

    int updateStatus(int projectId, ProjectStatusEnum status);

    int updateEnterprisesType(int projectId, EnterprisesTypeEnum enterprisesTypeEnum);

    int updateManual(int projectId, String currManual);

    int updateBasic(int projectId, long amount, int during, int duringUnit, BusinessTypeEnum businessType);

    int updateBasic(int projectId, LocalDate endDate);

    int updateContInfo(int projectId, int contYear, String contNo);

    List<ProjectInfo> findAll();

    Page<ProjectInfo> findByStatusIn(String code, List<ProjectStatusEnum> status, int pageNo,
                                     int pageSize);

    int findLastContNo(int contYear);

}
