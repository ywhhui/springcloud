package com.szcgc.project.model;

import com.szcgc.project.constant.SubjectEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/10/21 10:45
 */
@Entity
@Table(name = "repayrcdinfo", schema = "gmis_project")
public class RepayRecordInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "客户Id")
    @Column(updatable = false)
    private int customerId;

    @Schema(description = "还款金额")
    @Column(updatable = false)
    private long amount;

    @Schema(description = "还款科目")
    @Column(updatable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private SubjectEnum subject;

    @Schema(description = "还款时间")
    @Column(updatable = false)
    private LocalDate repayAt;

    @Schema(description = "录入用户Id")
    @Column(updatable = false)
    private int accountId;

    @Schema(description = "创建时间")
    @Column(updatable = false)
    private LocalDateTime createAt;

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public SubjectEnum getSubject() {
        return subject;
    }

    public void setSubject(SubjectEnum subject) {
        this.subject = subject;
    }

    public void setSubject(String subject) {
        this.subject = SubjectEnum.valueOf(subject);
    }

    public LocalDate getRepayAt() {
        return repayAt;
    }

    public void setRepayAt(LocalDate repayAt) {
        this.repayAt = repayAt;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

}
