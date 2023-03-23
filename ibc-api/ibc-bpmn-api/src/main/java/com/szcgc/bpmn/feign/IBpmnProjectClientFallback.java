package com.szcgc.bpmn.feign;

/**
 * @Author liaohong
 * @create 2022/9/28 10:26
 */
public class IBpmnProjectClientFallback implements IBpmnProjectClient {

    @Override
    public void projectAction(int accountId, int projectId, String caseName, String opinion, String remarks, String taskId, String extra) {

    }

    @Override
    public void projectBehaviour(int accountId, int projectId, String param) {

    }

}
