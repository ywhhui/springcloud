package com.szcgc.account.model.permission;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

/**
 * @Author liaohong
 * @create 2020/8/24 17:39
 */
@Entity
@Table(name = "p_userole", schema = "gmis_account")
public class UserRole {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "系统角色Id")
    private int sysRoleId;

    @Schema(description = "账号Id")
    private int accountId;

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

    public int getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(int sysRoleId) {
        this.sysRoleId = sysRoleId;
    }
}
