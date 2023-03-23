package com.szcgc.flowable.feign;

import com.szcgc.comm.constant.AppConstant;
import com.szcgc.flowable.model.FlowTaskInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/9/26 16:00
 */
@FeignClient(
        value = AppConstant.APPLICATION_FLOW_NAME,
        fallback = IFlowTaskClientFallback.class,
        contextId = "flow-task-client"
)
public interface IFlowTaskClient {

    String API_PREFIX = "/flowable/task";
    String PROJECT = API_PREFIX + "/project";
    String ACCOUNT = API_PREFIX + "/account";
    String NAME = API_PREFIX + "/name";
    String PROCESS = API_PREFIX + "/process";
    String TASK = API_PREFIX + "/task";
    String ASSIGN = API_PREFIX + "/assign";
    String COMPLETE = API_PREFIX + "/complete";

    @GetMapping(PROJECT)
    List<FlowTaskInfo> queryByProjectId(@RequestParam("projectId") int projectId) ;

    @GetMapping(ACCOUNT)
    List<FlowTaskInfo> queryByAccountId(@RequestParam("accountId") int accountId) ;

    @GetMapping(NAME)
    List<FlowTaskInfo> queryByAccountIdTask(@RequestParam("accountId") int accountId, @RequestParam("taskName") String taskName) ;

    @GetMapping(PROCESS)
    FlowTaskInfo query(@RequestParam("projectId") int projectId, @RequestParam("accountId") int accountId, @RequestParam("taskName") String taskName, @RequestParam("processName") String processName) ;

    @GetMapping(TASK)
    FlowTaskInfo query(@RequestParam("taskId") String taskId) ;

    @GetMapping(ASSIGN)
    boolean assign(@RequestParam("taskId") String taskId, @RequestParam("accountId") int accountId) ;

    @GetMapping(COMPLETE)
    boolean complete(@RequestParam("taskId") String taskId, @RequestParam(value = "variables" ,required = false) String variables) ;
}
