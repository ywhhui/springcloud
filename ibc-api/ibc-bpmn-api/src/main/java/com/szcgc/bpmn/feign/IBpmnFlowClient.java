package com.szcgc.bpmn.feign;

import com.szcgc.comm.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author liaohong
 * @create 2022/9/28 10:13
 */
@FeignClient(
        value = AppConstant.APPLICATION_BPMN_NAME,
        fallback = IBpmnFlowClientFallback.class,
        contextId = "bpmn-flow-client"
)
public interface IBpmnFlowClient {

    String API_PREFIX = "/flow";
    String STARTED = API_PREFIX + "/started";
    String ENDING = API_PREFIX + "/ending";
    String TASK = API_PREFIX + "/task";

    @GetMapping(STARTED)
    void processStarted(@RequestParam("projectId") int projectId,
                        @RequestParam("processName") String processName,
                        @RequestParam("processId") String processId,
                        @RequestParam("param") String param);

    @GetMapping(ENDING)
    void processEnding(@RequestParam("projectId") int projectId,
                       @RequestParam("processName") String processName,
                       @RequestParam("processId") String processId,
                       @RequestParam("param") String param);

    //方法参数过长，非最佳范式
    @GetMapping(TASK)
    void taskCreated(@RequestParam("accountId") int accountId,
                     @RequestParam("projectId") int projectId,
                     @RequestParam("processName") String processName,
                     @RequestParam("processId") String processId,
                     @RequestParam("taskName") String taskName,
                     @RequestParam("taskId") String taskId);
}
