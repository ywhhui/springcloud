package com.szcgc.bpmn.feign;

/**
 * @Author liaohong
 * @create 2022/9/28 10:23
 */
public class IBpmnFlowClientFallback implements IBpmnFlowClient {

    @Override
    public void processStarted(int projectId, String processName, String processId, String param) {

    }

    @Override
    public void processEnding(int projectId, String processName, String processId, String param) {

    }

    @Override
    public void taskCreated(int accountId, int projectId, String processName, String processId, String taskName, String taskId) {

    }
}
