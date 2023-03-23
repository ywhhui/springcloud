package com.szcgc.project.feign;

import com.szcgc.project.constant.ProjectStatusEnum;
import com.szcgc.project.model.AttendInfo;
import com.szcgc.project.model.CommAuditInfo;
import com.szcgc.project.model.ProjectInfo;
import com.szcgc.project.model.ProjectSupervisorInfo;

/**
 * @Author liaohong
 * @create 2022/9/28 9:11
 */
public class IProjectClientFallback implements IProjectClient {

    @Override
    public ProjectInfo findById(int projectId) {
        return null;
    }

    @Override
    public int tryUpdateStatus(int projectId, ProjectStatusEnum status) {
        return 0;
    }

    @Override
    public ProjectSupervisorInfo findSupervisorById(int projectId) {
        return null;
    }

    @Override
    public AttendInfo findAttendById(int projectId) {
        return null;
    }

    @Override
    public CommAuditInfo findAuditById(int projectId) {
        return null;
    }

    @Override
    public boolean releaseTodo(int projectId) {
        return false;
    }

    @Override
    public boolean releaseGuarantee(int projectId, String releaseType) {
        return false;
    }


}
