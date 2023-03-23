package com.szcgc.project.model;

import com.szcgc.project.constant.ProjectConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/11/12 15:23
 */
@Entity
@Table(name = "annotateinfo", schema = "gmis_project")
public class AnnotateInfo extends ProjectSuperInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Schema(description = "银行支行Id")
//    @Column(updatable = false)
//    private int bankBranchId;

    @Schema(description = "负责人审批Id")
    private int leaderBy;

    @Schema(description = "负责人审批意见")
    @Column(length = 10)
    private String leaderRst;

    @Schema(description = "负责人审批详细意见")
    @Column(length = 100)
    private String leaderRemarks;

    @Schema(description = "负责人审批时间")
    private LocalDateTime leaderAt;

    @Schema(description = "风控领导审批Id")
    private int riskBy;

    @Schema(description = "风控领导审批意见")
    @Column(length = 10)
    private String riskRst;

    @Schema(description = "风控领导审批详细意见")
    @Column(length = 100)
    private String riskRemarks;

    @Schema(description = "风控领导审批时间")
    private LocalDateTime riskAt;

    @Schema(description = "条线领导审批Id")
    private int bossBy;

    @Schema(description = "条线领导审批意见")
    @Column(length = 10)
    private String bossRst;

    @Schema(description = "条线领导详细意见")
    @Column(length = 100)
    private String bossRemarks;

    @Schema(description = "条线领导审批时间")
    private LocalDateTime bossAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getBankBranchId() {
//        return bankBranchId;
//    }
//
//    public void setBankBranchId(int bankBranchId) {
//        this.bankBranchId = bankBranchId;
//    }

    public int getLeaderBy() {
        return leaderBy;
    }

    public void setLeaderBy(int leaderBy) {
        this.leaderBy = leaderBy;
    }

    public String getLeaderRst() {
        return leaderRst;
    }

    public void setLeaderRst(String leaderRst) {
        this.leaderRst = leaderRst;
    }

    public LocalDateTime getLeaderAt() {
        return leaderAt;
    }

    public void setLeaderAt(LocalDateTime leaderAt) {
        this.leaderAt = leaderAt;
    }

    public int getRiskBy() {
        return riskBy;
    }

    public void setRiskBy(int riskBy) {
        this.riskBy = riskBy;
    }

    public String getRiskRst() {
        return riskRst;
    }

    public void setRiskRst(String riskRst) {
        this.riskRst = riskRst;
    }

    public LocalDateTime getRiskAt() {
        return riskAt;
    }

    public void setRiskAt(LocalDateTime riskAt) {
        this.riskAt = riskAt;
    }

    public int getBossBy() {
        return bossBy;
    }

    public void setBossBy(int bossBy) {
        this.bossBy = bossBy;
    }

    public String getBossRst() {
        return bossRst;
    }

    public void setBossRst(String bossRst) {
        this.bossRst = bossRst;
    }

    public LocalDateTime getBossAt() {
        return bossAt;
    }

    public void setBossAt(LocalDateTime bossAt) {
        this.bossAt = bossAt;
    }

    public String getLeaderRemarks() {
        return leaderRemarks;
    }

    public void setLeaderRemarks(String leaderRemarks) {
        this.leaderRemarks = leaderRemarks;
    }

    public String getRiskRemarks() {
        return riskRemarks;
    }

    public void setRiskRemarks(String riskRemarks) {
        this.riskRemarks = riskRemarks;
    }

    public String getBossRemarks() {
        return bossRemarks;
    }

    public void setBossRemarks(String bossRemarks) {
        this.bossRemarks = bossRemarks;
    }

    public boolean isLeaderOver() {
        return ProjectConstants.ANNOTATE_FLOW_STOP.equals(leaderRst);
    }

    public boolean isLeaderMeeting() {
        return ProjectConstants.ANNOTATE_FLOW_MEETING.equals(leaderRst);
    }

    public boolean isLeaderTry() {
        return ProjectConstants.ANNOTATE_FLOW_RETRY.equals(leaderRst);
    }

    public boolean isLeaderPass() {
        return ProjectConstants.ANNOTATE_FLOW_PASS.equals(leaderRst);
    }

    public boolean isRiskOver() {
        return ProjectConstants.ANNOTATE_FLOW_STOP.equals(riskRst);
    }

    public boolean isRiskMeeting() {
        return ProjectConstants.ANNOTATE_FLOW_MEETING.equals(riskRst);
    }

    public boolean isRiskTry() {
        return ProjectConstants.ANNOTATE_FLOW_RETRY.equals(riskRst);
    }

    public boolean isRiskPass() {
        return ProjectConstants.ANNOTATE_FLOW_PASS.equals(riskRst);
    }

    public boolean isBossOver() {
        return ProjectConstants.ANNOTATE_FLOW_STOP.equals(bossRst);
    }

    public boolean isBossMeeting() {
        return ProjectConstants.ANNOTATE_FLOW_MEETING.equals(bossRst);
    }

    public boolean isBossTry() {
        return ProjectConstants.ANNOTATE_FLOW_RETRY.equals(bossRst);
    }

    public boolean isBossPass() {
        return ProjectConstants.ANNOTATE_FLOW_PASS.equals(bossRst);
    }

    public boolean isOver() {
        return isLeaderOver() || isLeaderMeeting() || isLeaderTry()
                || isRiskOver() || isRiskMeeting() || isRiskTry()
                || bossBy > 0;
    }

    public boolean isRiskable() {
        return leaderBy > 0 && leaderAt != null && riskBy == 0 && riskAt == null && bossBy == 0 && bossAt == null;
    }

    public boolean isBossable() {
        return leaderBy > 0 && leaderAt != null && bossBy == 0 && bossAt == null;
    }
}
