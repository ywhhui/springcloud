package com.szcgc.bpmn.feign;

import com.szcgc.comm.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author liaohong
 * @create 2022/9/28 10:25
 */
@FeignClient(
        value = AppConstant.APPLICATION_BPMN_NAME,
        fallback = IBpmnProjectClientFallback.class,
        contextId = "bpmn-project-client"
)
public interface IBpmnProjectClient {

    String API_PREFIX = "/project";
    String ACT = API_PREFIX + "/act";
    String BEH = API_PREFIX + "/beh";

    @GetMapping(ACT)
    void projectAction(@RequestParam("accountId") int accountId,
                       @RequestParam("projectId") int projectId,
                       @RequestParam("caseName") String caseName,
                       @RequestParam("opinion") String opinion,
                       @RequestParam("remarks") String remarks,
                       @RequestParam("taskId") String taskId,
                       @RequestParam("extra") String extra);

    @GetMapping(BEH)
    void projectBehaviour(@RequestParam("accountId") int accountId, @RequestParam("projectId") int projectId, @RequestParam("param") String param);

}
