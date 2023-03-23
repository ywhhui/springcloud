package com.szcgc.bpmn.feign;

import com.szcgc.comm.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author liaohong
 * @create 2022/9/26 16:28
 */
@FeignClient(
        value = AppConstant.APPLICATION_BPMN_NAME,
        fallback = IBpmnIdmClientFallback.class,
        contextId = "bpmn-idm-client"
)
public interface IBpmnIdmClient {

    String API_PREFIX = "/bpmn";
    String ASSIGN = API_PREFIX + "/assign";
    String NODE = API_PREFIX + "/node";

    /**
     * 查询任务处理人
     *
     * @param projectId
     * @param taskName
     * @param processName
     * @return
     * @throws Exception
     */
    @GetMapping(ASSIGN)
    int assign(@RequestParam("projectId") int projectId, @RequestParam("taskName") String taskName, @RequestParam("processName") String processName) throws Exception;

    /**
     * 获取节点所需信息
     * @param projectId
     * @param activityId
     * @param processName
     * @return
     * @throws Exception
     */
    @GetMapping(NODE)
    String node(@RequestParam("projectId") int projectId, @RequestParam("activityId") String activityId, @RequestParam("processName") String processName) throws Exception;
}
