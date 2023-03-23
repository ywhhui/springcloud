package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.model.AssessInfo;
import org.springframework.data.domain.Page;

/**
 * @Author liaohong
 * @create 2021/6/3 15:48
 */
public interface IAssessService extends IbcService<AssessInfo, Integer> {

    AssessInfo insertToAssign(int customerId, int projectId, int count);

    Page<Integer> findToAssign(int pageNo, int pageSize);

    Page<Integer> findAssigned(int accountId, int pageNo, int pageSize);

    int assign(int projectId, int accountId);

    int assess(int projectId);
}
