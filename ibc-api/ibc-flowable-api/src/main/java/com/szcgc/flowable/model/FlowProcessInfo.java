package com.szcgc.flowable.model;

/**
 * @Author liaohong
 * @create 2020/8/11 11:29
 */
public class FlowProcessInfo {

    private String processId;

    private int projectId;

    private String processName;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
