package com.szcgc.bpmn.feign;

/**
 * @Author liaohong
 * @create 2022/9/26 17:15
 */
public class IBpmnIdmClientFallback implements IBpmnIdmClient {

    @Override
    public int assign(int projectId, String taskName, String processName) throws Exception {
        return 0;
    }

    @Override
    public String node(int projectId, String activityId, String processName) throws Exception {
        return null;
    }
}
