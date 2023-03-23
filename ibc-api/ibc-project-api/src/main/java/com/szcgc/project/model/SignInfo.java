package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/12/4 17:12
 */
@Entity
@Table(name = "signinfo", schema = "gmis_project")
public class SignInfo extends ProjectSuperInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "签约日期")
    //@Column(updatable = false)
    private LocalDate signDate;

    @Schema(description = "签订人")
    //@Column(updatable = false)
    private int accountId;

    @Schema(description = "签约金额")
    //@Column(updatable = false)
    private long signAmount;

    @Schema(description = "银行支行Id")
    //@Column(updatable = false)
    private int bankBranchId;

    @Schema(description = "签约说明")
    //@Column(length = 200, updatable = false)
    @Column(length = 200)
    private String remarks;

    @Schema(description = "确认合同文件")
    //@Column(length = 200, updatable = false)
    @Column(length = 200)
    private String agreementIds;

    @Schema(description = "签约文件")
    //@Column(length = 200, updatable = false)
    @Column(length = 200)
    private String fileIds;

    @Schema(description = "借款合同号")
    @Column(length = 200)
    private String loanAgreementCode;

    @Schema(description = "保证合同号")
    @Column(length = 200)
    private String guaranteeAgreementCode;

    @Schema(description = "委托保证合同号")
    @Column(length = 200)
    private String delegateAgreementCode;

    @Schema(description = "创建时间")
    @Column(updatable = false)
    private LocalDateTime createAt;

    @Schema(description = "更新时间")
    private LocalDateTime updateAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getSignDate() {
        return signDate;
    }

    public void setSignDate(LocalDate signDate) {
        this.signDate = signDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public long getSignAmount() {
        return signAmount;
    }

    public void setSignAmount(long signAmount) {
        this.signAmount = signAmount;
    }

    public int getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(int bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAgreementIds() {
        return agreementIds;
    }

    public void setAgreementIds(String agreementIds) {
        this.agreementIds = agreementIds;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getLoanAgreementCode() {
        return loanAgreementCode;
    }

    public void setLoanAgreementCode(String loanAgreementCode) {
        this.loanAgreementCode = loanAgreementCode;
    }

    public String getGuaranteeAgreementCode() {
        return guaranteeAgreementCode;
    }

    public void setGuaranteeAgreementCode(String guaranteeAgreementCode) {
        this.guaranteeAgreementCode = guaranteeAgreementCode;
    }

    public String getDelegateAgreementCode() {
        return delegateAgreementCode;
    }

    public void setDelegateAgreementCode(String delegateAgreementCode) {
        this.delegateAgreementCode = delegateAgreementCode;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

}
