package com.szcgc.flowable.feign;

import com.szcgc.comm.constant.AppConstant;
import com.szcgc.flowable.model.FlowProcessInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author liaohong
 * @create 2020/8/7 14:22
 */
@FeignClient(
        value = AppConstant.APPLICATION_FLOW_NAME,
        fallback = IFlowProcessClientFallback.class,
        contextId = "flow-process-client"
)
public interface IFlowProcessClient {

    String API_PREFIX = "/flowable/process";
    String START = API_PREFIX + "/start";
    String JUMP = API_PREFIX + "/jump";
    String MOVE = API_PREFIX + "/move";
    String SUSPEND = API_PREFIX + "/suspend";
    String ACTIVATE = API_PREFIX + "/activate";
    String DELETE = API_PREFIX + "/delete";

    @GetMapping(START)
    FlowProcessInfo start(@RequestParam("processName") String processName, @RequestParam("projectId") int projectId, @RequestParam("accountId") int accountId) ;

    @GetMapping(JUMP)
    boolean jump(@RequestParam("processId") String processId, @RequestParam("activityId") String activityId) ;

    @GetMapping(MOVE)
    boolean move(@RequestParam("processId") String processId, @RequestParam("currentActivityId") String currentActivityId, @RequestParam("newActivityId") String newActivityId) ;

    @GetMapping(SUSPEND)
    boolean suspend(@RequestParam("processId") String processId) ;

    @GetMapping(ACTIVATE)
    boolean activate(@RequestParam("processId") String processId) ;

    @GetMapping(DELETE)
    boolean delete(@RequestParam("processId") String processId) ;

}
