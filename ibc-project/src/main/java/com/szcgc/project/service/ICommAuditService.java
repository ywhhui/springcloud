package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.model.AttendInfo;
import com.szcgc.project.model.CommAuditInfo;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/17 17:02
 */
public interface ICommAuditService extends IbcService<CommAuditInfo, Integer> {

    List<CommAuditInfo> findByProjectId(int projectId);

    CommAuditInfo findLastByProjectId(int projectId);

}
