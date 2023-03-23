package com.szcgc.project.feign;

import com.szcgc.comm.constant.AppConstant;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.project.constant.ProjectStatusEnum;
import com.szcgc.project.model.AttendInfo;
import com.szcgc.project.model.CommAuditInfo;
import com.szcgc.project.model.ProjectInfo;
import com.szcgc.project.model.ProjectSupervisorInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author liaohong
 * @create 2022/9/26 17:27
 */

@FeignClient(
        value = AppConstant.APPLICATION_PROJIECT_NAME,
        fallback = IProjectClientFallback.class
)
public interface IProjectClient {

    String API_PREFIX = "/project/";
    //String PROJECT = API_PREFIX + "/project";
    String STATUS = API_PREFIX + "/status";
    String SUPERVISOR = API_PREFIX + "/supervisor";
    String ATTEND = API_PREFIX + "/attend";
    String AUDIT = API_PREFIX + "/audit";
    String RELEASE_TODO = API_PREFIX + "/release/todo";
    String RELEASE_GUARANTEE = API_PREFIX + "/release/guarantee";

    @GetMapping(API_PREFIX + WebConstants.DETAIL)
    ProjectInfo findById(@RequestParam("projectId") int projectId);

    @GetMapping(STATUS)
    int tryUpdateStatus(@RequestParam("projectId") int projectId, @RequestParam("status") ProjectStatusEnum status);

    @GetMapping(SUPERVISOR)
    ProjectSupervisorInfo findSupervisorById(@RequestParam("projectId") int projectId);

    @GetMapping(ATTEND)
    AttendInfo findAttendById(@RequestParam("projectId") int projectId);

    @GetMapping(AUDIT)
    CommAuditInfo findAuditById(@RequestParam("projectId") int projectId);

    /**
     * 还款登记后 判断是否启动解保待办的条件
     * @param projectId
     * @return  true 表示需要启动解保待办 false 不满足启动解保待办条件
     */
    @GetMapping(RELEASE_TODO)
    boolean releaseTodo(@RequestParam("projectId") int projectId);

    /**
     * 审核还款证明书后 判断是否要生成 解除反担保物待办 或者 退还保证金待办
     * @param projectId   releaseType 1：反担保措施 2:保证金：
     * @return  true 表示要 false 不
     */
    @GetMapping(RELEASE_GUARANTEE)
    boolean releaseGuarantee(@RequestParam("projectId") int projectId,@RequestParam("releaseType") String releaseType);

}
