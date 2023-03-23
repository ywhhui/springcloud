package com.szcgc.comm.message;

/**
 * @Author liaohong
 * @create 2020/8/20 10:41
 */
public interface IFlowSender {

    void processStarted(int projectId, String processName, String processId, String param);

    void processEnding(int projectId, String processName, String processId, String param);

    void taskCreated(int accountId, int projectId, String processName, String processId, String taskName, String taskId);
}
