package com.szcgc.bpmn.model;

import com.szcgc.comm.util.StringUtils;
import com.szcgc.comm.util.SundryUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/8/20 19:46
 * 项目流程表
 */
@Entity
@Table(name = "processinfo", schema = "gmis_process")
public class ProjectProcessInfo {

    private static final int REMARK_MAX_LEN = 300;
    private static final String REMARKS_TAG_DEL = "del";
    private static final String REMARKS_TAG_SPD = "spd";
    private static final String REMARKS_TAG_ACT = "act";
    private static final String REMARKS_TAG_DEL_AUTO = "dat";
    private static final char REMARKS_TAG_SP1 = ',';
    private static final char REMARKS_TAG_SP2 = '|';

    private static final String PARAM_TAG_OVER = "over";
    private static final char PARAM_TAG_SP = '`';

    public static final int IBC_STATUS_NML = 1;    //流程运行中
    public static final int IBC_STATUS_END = 2;    //流程已结束
    public static final int IBC_STATUS_SPD = 3;    //流程已暂停
    public static final int IBC_STATUS_DEL = 4;    //流程已删除

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;  //项目Id

    @Schema(description = "流程名")
    @Column(length = 20, updatable = false)
    private String processName; //流程名

    @Schema(description = "流程Id")
    @Column(length = 50, updatable = false)
    private String processId; //流程Id,唯一索引

    @Schema(description = "流程输入")
    @Column(length = 100, updatable = false)
    private String startParam; //流程输入

    @Schema(description = "流程输出")
    @Column(length = 100)
    private String endParam; //流程输出

    @Schema(description = "流程状态")
    private int ibcStatus; //流程状态

    @Schema(description = "备注")
    @Column(length = 500)
    private String remarks;    //流程发起人(用户发起，系统发起，流程调用)

    @Column(updatable = false)
    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStartParam() {
        return startParam;
    }

    public void setStartParam(String startParam) {
        this.startParam = startParam;
    }

    public String getEndParam() {
        return endParam;
    }

    public void setEndParam(String endParam) {
        this.endParam = endParam;
    }

    public int getIbcStatus() {
        return ibcStatus;
    }

    public void setIbcStatus(int ibcStatus) {
        this.ibcStatus = ibcStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    private void appendRemarks(String key, int id, boolean rst, String desc) {
        StringBuilder sb = new StringBuilder();
        if (this.remarks != null) {
            sb.append(this.remarks);
        }
        sb.append(REMARKS_TAG_SP2);
        sb.append(key);
        sb.append(REMARKS_TAG_SP1);
        sb.append(id);
        sb.append(REMARKS_TAG_SP1);
        sb.append(rst);
        if (desc != null && desc.length() > 0) {
            sb.append(REMARKS_TAG_SP1);
            sb.append(desc);
        }
        this.remarks = sb.toString();
        if (this.remarks.length() > REMARK_MAX_LEN) {
            this.remarks = StringUtils.ending(this.remarks, REMARK_MAX_LEN);
        }
    }

    public void setIbcStatusNormal() {
        this.ibcStatus = IBC_STATUS_NML;
    }

    public void remarksNormal(int id, boolean rst) {
        appendRemarks(REMARKS_TAG_ACT, id, rst, null);
    }

    public void setIbcStatusEnd() {
        this.ibcStatus = IBC_STATUS_END;
    }

    public void setIbcStatusSuspend() {
        this.ibcStatus = IBC_STATUS_SPD;
    }

    public void remarksSuspend(int id, boolean rst) {
        appendRemarks(REMARKS_TAG_SPD, id, rst, null);
    }

    public boolean isSuspendBy(int id) {
        String key = REMARKS_TAG_SPD + REMARKS_TAG_SP1 + id + REMARKS_TAG_SP1;
        return this.remarks != null && this.remarks.contains(key);
    }

    public void setIbcStatusDelete() {
        this.ibcStatus = IBC_STATUS_DEL;
    }

    public void remarksDelete(int id, boolean rst, boolean hasSuper) {
        appendRemarks(REMARKS_TAG_DEL, id, rst, hasSuper ? REMARKS_TAG_DEL_AUTO : null);
    }

    public boolean isIbcStatusNormal() {
        return this.ibcStatus == IBC_STATUS_NML;
    }

    public boolean isIbcStatusActive() {
        return this.ibcStatus == IBC_STATUS_NML || this.ibcStatus == IBC_STATUS_SPD;
    }

    public boolean isManual() {
        if (startParam == null || startParam.length() <= 0)
            return false;
        int sp = startParam.indexOf(PARAM_TAG_SP);
        if (sp <= 0)
            return false;
        int accountId = SundryUtils.tryGetInt(startParam.substring(0, sp), 0);
        return accountId > 0;
    }

    public boolean hasSuper() {
        if (startParam == null || startParam.length() <= 0)
            return false;
        int sp = startParam.indexOf(PARAM_TAG_SP);
        if (sp <= 0)
            return false;
        int sp2 = startParam.indexOf(PARAM_TAG_SP, sp + 1);
        String processId = sp2 > 0 ? startParam.substring(sp + 1, sp2) : startParam.substring(sp + 1);
        return processId.length() > 10;
    }

    public boolean isOver() {
        return endParam != null && endParam.indexOf(PARAM_TAG_OVER) >= 0;
    }
}
