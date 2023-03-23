package com.szcgc.project.model;

import com.szcgc.project.constant.SubjectEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: chenxinli
 * @Date: 2020/10/22 16:03
 * @Description:
 */

@Entity
@Table(schema = "gmis_project", name = "repayplaninfo")
@Schema(description = "项目还款还息计划表")
public class RepayPlanInfo {

    public static int AUTOMATE = 1;

    public static int MANUAL = 0;

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Schema(description = "项目id")
    private int projectId;

    @Column
    @Schema(description = "客户id")
    private int customerId;

    @Column
    @Enumerated(EnumType.STRING)
    @Schema(description = "类型")
    private SubjectEnum subject;

    @Column
    @Schema(description = "金额")
    private long amount;

    @Column
    @Schema(description = "还款时间")
    private LocalDate returnDate;

    @Column
    @Schema(description = "创建类型（0：手动创建，1：系统自动创建）")
    private int createType;

    @Column
    @Schema(description = "创建时间")
    private LocalDateTime createAt;

    public RepayPlanInfo(int projectId, int customerId, SubjectEnum subjectEnum, long amount, LocalDate returnDate, int createType) {
        this.projectId = projectId;
        this.customerId = customerId;
        this.subject = subjectEnum;
        this.amount = amount;
        this.returnDate = returnDate;
        this.createType = createType;
    }
    public RepayPlanInfo(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SubjectEnum getSubject() {
        return subject;
    }

    public void setSubject(SubjectEnum type) {
        this.subject = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public int getCreateType() {
        return createType;
    }

    public void setCreateType(int createType) {
        this.createType = createType;
    }

}
