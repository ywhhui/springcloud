package com.szcgc.message.send;

import com.szcgc.bpmn.feign.IBpmnProjectClient;
import com.szcgc.comm.message.IProjectSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @Author liaohong
 * @create 2022/9/29 16:33
 */
@Component
@ConditionalOnProperty(name = "ibc.message.project.sender.feign", havingValue = "true")
public class ProjectMsgSender implements IProjectSender {

    @Autowired
    IBpmnProjectClient bpmnClient;

    @Override
    public void projectAction(int accountId, int projectId, String caseName, String opinion, String remarks, String taskId, String extra) {
        bpmnClient.projectAction(accountId, projectId, caseName, opinion, remarks, taskId, extra);
    }

    @Override
    public void projectBehaviour(int accountId, int projectId, String param) {
        bpmnClient.projectBehaviour(accountId, projectId, param);
    }

//    @Override
//    public void projectFile(int accountId, int projectId, String param) {
//
//    }
}
