package com.szcgc.account.model;

import com.szcgc.account.constant.AccountRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

/**
 * @Author liaohong
 * @create 2020/8/7 15:50
 * 组织结构(本质是用户角色表)
 * 在根据roleId查找accountId的时候，优先匹配department和businessType信息，没有匹配项的话，再考虑模糊匹配
 * 或者可以理解为，在满足department和businessType的前提下，role才生效!
 */
@Entity
@Table(name = "organizationinfo", schema = "gmis_account")
public class OrganizationInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "用户Id")
    private int accountId;  //用户Id

    @Schema(description = "用户名")
    private String accountName;

    @Schema(description = "角色Id")
    @Enumerated(EnumType.STRING)
    private AccountRoleEnum roleId; //角色Id

    @Schema(description = "业务发生部门")
    private int departmentId;

    @Schema(description = "业务发生分支")
    private int branchId;

    @Schema(description = "顶级业务范围")
    @Column(length = 10)
    private String businessTop;

    @Schema(description = "业务范围")
    @Column(length = 10)
    private String businessCate;

    @Schema(description = "业务品种")
    @Column(length = 20)
    private String businessType;

    @Schema(description = "表达式")
    @Column(length = 200)
    private String eval;

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

    public AccountRoleEnum getRoleId() {
        return roleId;
    }

    public void setRoleId(AccountRoleEnum roleId) {
        this.roleId = roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = AccountRoleEnum.valueOf(roleId);
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBusinessTop() {
        return businessTop;
    }

    public void setBusinessTop(String businessTop) {
        this.businessTop = businessTop;
    }

    public String getBusinessCate() {
        return businessCate;
    }

    public void setBusinessCate(String businessCate) {
        this.businessCate = businessCate;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getEval() {
        return eval;
    }

    public void setEval(String eval) {
        this.eval = eval;
    }

    public boolean withDepartOrBranch() {
        return departmentId > 0 || branchId > 0;
    }

    public boolean withEval() {
        return eval != null && eval.length() > 2;
    }

    public boolean withEval(String key) {
        return withEval() && eval.indexOf(key) > 0;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
