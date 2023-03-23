package com.szcgc.account.model.system;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author liaohong
 * @create 2020/9/23 14:56
 * 银行信息
 */
@Entity
@Table(name = "s_bankinfo", schema = "gmis_account")
public class BankInfo {

    @Id
    @Column(name = "id", length = 11)
    private int id; //id非自增，取值为国家分配该银行的编码

    @Schema(description = "银行名(全称)")
    @Column(length = 50)
    private String fullName;

    @Schema(description = "银行名(简称称)")
    @Column(length = 20)
    private String name;

    @Schema(description = "信用额度")
    private int creditLine;

    @Schema(description = "还款日")
    private int repayDay;

    @Schema(description = "是否支持电子保函(0:不支持, 1:用户可选, 2:必须是电子保函)")
    private int elecBh;

    @Schema(description = "备注")
    @Column(length = 200)
    private String remarks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(int creditLine) {
        this.creditLine = creditLine;
    }

    public int getRepayDay() {
        return repayDay;
    }

    public void setRepayDay(int repayDay) {
        this.repayDay = repayDay;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getElecBh() {
        return elecBh;
    }

    public void setElecBh(int elecBh) {
        this.elecBh = elecBh;
    }
}
