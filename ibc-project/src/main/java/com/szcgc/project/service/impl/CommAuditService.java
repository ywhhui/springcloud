package com.szcgc.project.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.project.model.CommAuditInfo;
import com.szcgc.project.repository.CommAuditRepository;
import com.szcgc.project.service.ICommAuditService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/10/13 9:33
 */
@Service
public class CommAuditService extends BaseService<CommAuditRepository, CommAuditInfo, Integer> implements ICommAuditService {

    @Override
    public List<CommAuditInfo> findByProjectId(int projectId) {
        return repository.findByProjectId(projectId);
    }

    @Override
    public CommAuditInfo findLastByProjectId(int projectId) {
        return repository.findFirstByProjectIdOrderByIdDesc(projectId);
    }
}
