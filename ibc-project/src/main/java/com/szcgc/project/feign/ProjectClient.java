package com.szcgc.project.feign;

import com.szcgc.comm.web.WebConstants;
import com.szcgc.project.constant.ProjectStatusEnum;
import com.szcgc.project.model.AttendInfo;
import com.szcgc.project.model.CommAuditInfo;
import com.szcgc.project.model.ProjectInfo;
import com.szcgc.project.model.ProjectSupervisorInfo;
import com.szcgc.project.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Author liaohong
 * @create 2022/9/28 15:20
 */
@RestController
public class ProjectClient implements IProjectClient {

    private static Logger logger = LoggerFactory.getLogger(ProjectClient.class);

    @Autowired
    IProjectService projectService;

    @Autowired
    IProjectSupervisorService supervisorService;

    @Autowired
    IAttendService attendService;

    @Autowired
    ICommAuditService auditService;

    @Autowired
    IReleaseService releaseService;

    @Autowired
    IReleaseDetailService releaseDetailService;

    @Override
    @GetMapping(API_PREFIX + WebConstants.DETAIL)
    public ProjectInfo findById(@RequestParam("projectId") int projectId) {
        Optional<ProjectInfo> project = projectService.find(projectId);
        if (!project.isPresent())
            return null;
        return project.get();
    }

    @Override
    @GetMapping(STATUS)
    public int tryUpdateStatus(@RequestParam("projectId") int projectId, @RequestParam("status") ProjectStatusEnum status) {
        ProjectStatusEnum currStatus = projectService.findStatus(projectId);
        if (status.ordinal() <= currStatus.ordinal())
            return 0;
        int rst = projectService.updateStatus(projectId, status);
        logger.info("tryUpdateStatus {}->{}:{}", currStatus, status, rst);
        return rst;
    }

    @Override
    @GetMapping(SUPERVISOR)
    public ProjectSupervisorInfo findSupervisorById(@RequestParam("projectId") int projectId) {
        return supervisorService.findByProjectId(projectId);
    }

    @Override
    @GetMapping(ATTEND)
    public AttendInfo findAttendById(@RequestParam("projectId") int projectId) {
        return attendService.findLastByProjectId(projectId);
    }

    @Override
    @GetMapping(AUDIT)
    public CommAuditInfo findAuditById(@RequestParam("projectId") int projectId) {
        return auditService.findLastByProjectId(projectId);
    }

    @Override
    public boolean releaseTodo(int projectId) {
        releaseService.releaseTodo(projectId);
        return false;
    }

    @Override
    public boolean releaseGuarantee(int projectId, String releaseType) {
        releaseDetailService.releaseGuarantee(projectId,releaseType);
        return false;
    }


}
