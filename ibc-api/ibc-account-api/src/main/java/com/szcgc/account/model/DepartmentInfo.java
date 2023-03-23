package com.szcgc.account.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

/**
 * @Author liaohong
 * @create 2020/8/7 15:47
 */
@Entity
@Table(name = "departmentinfo", schema = "gmis_account")
public class DepartmentInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "部门名")
    @Column(length = 50)
    private String name;    //部门名

    @Schema(description = "分支Id")
    private int branchId;

    @Schema(description = "部门负责人")
    private int managerId;     //部门负责人Id

//    @Column(length = 20)
//    private String managerName;

    @Schema(description = "部门分管领导")
    private int leaderId;     //分管领导Id

//    @Column(length = 20)
//    private String leaderName;

    @Schema(description = "部门法务经理")
    private int legalId;

    @Column(length = 20)
    private String legalName;

    @Schema(description = "部门合同审核员")
    private int agreementId;

    @Column(length = 20)
    private String agreementName;

    @Schema(description = "部门法律专员")
    private int lawyerId;

    @Column(length = 20)
    private String lawyerName;

    @Schema(description = "部门法务部负责人")
    private int legalHeadId;

    @Column(length = 20)
    private String legalHeadName;

    @Schema(description = "部门风控签批专员")
    private int riskAnnotateId;//已经弃用

    @Column(length = 20)
    private String riskAnnotateName;

    @Schema(description = "部门签批条线领导")
    private int bossAnnotateId;

    @Column(length = 20)
    private String bossAnnotateName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    public int getLegalId() {
        return legalId;
    }

    public void setLegalId(int lawyerId) {
        this.legalId = lawyerId;
    }

    public int getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public int getRiskAnnotateId() {
        return riskAnnotateId;
    }

    public void setRiskAnnotateId(int riskAnnotateId) {
        this.riskAnnotateId = riskAnnotateId;
    }

    public int getBossAnnotateId() {
        return bossAnnotateId;
    }

    public void setBossAnnotateId(int bossAnnotateId) {
        this.bossAnnotateId = bossAnnotateId;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public int getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(int layerId) {
        this.lawyerId = layerId;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public void setLawyerName(String layerName) {
        this.lawyerName = layerName;
    }

    public int getLegalHeadId() {
        return legalHeadId;
    }

    public void setLegalHeadId(int legalHeadId) {
        this.legalHeadId = legalHeadId;
    }

    public String getLegalHeadName() {
        return legalHeadName;
    }

    public void setLegalHeadName(String legalHeadName) {
        this.legalHeadName = legalHeadName;
    }

    public String getRiskAnnotateName() {
        return riskAnnotateName;
    }

    public void setRiskAnnotateName(String riskAnnotateName) {
        this.riskAnnotateName = riskAnnotateName;
    }

    public String getBossAnnotateName() {
        return bossAnnotateName;
    }

    public void setBossAnnotateName(String bossAnnotateName) {
        this.bossAnnotateName = bossAnnotateName;
    }
}
