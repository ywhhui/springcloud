package com.szcgc.bpmn.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/10/22 14:44
 */
@Entity
@Table(name = "taskinfo", schema = "gmis_process")
public class ProjectTaskInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "用户Id")
    @Column(updatable = false)
    private int accountId;  //用户Id

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;  //项目Id

    @Schema(description = "流程名")
    @Column(length = 20, updatable = false)
    private String processName; //流程名

    @Schema(description = "流程Id")
    @Column(length = 50, updatable = false)
    private String processId;

    @Schema(description = "任务名")
    @Column(length = 20, updatable = false)
    private String taskName; //流程名

    @Schema(description = "任务Id")
    @Column(length = 50, updatable = false)
    private String taskId;

    @Schema(description = "创建时间")
    @Column(updatable = false)
    private LocalDateTime createAt;

    private int actionId;

    private LocalDateTime updateAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

}
