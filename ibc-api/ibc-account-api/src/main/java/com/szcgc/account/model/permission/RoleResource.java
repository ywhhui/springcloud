package com.szcgc.account.model.permission;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

/**
 * @Author liaohong
 * @create 2020/8/24 17:40
 */
@Entity
@Table(name = "p_roleres", schema = "gmis_account")
public class RoleResource {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "角色Id")
    private int sysRoleId;

    @Schema(description = "资源Id")
    private int sysResourceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(int sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public int getSysResourceId() {
        return sysResourceId;
    }

    public void setSysResourceId(int sysResourceId) {
        this.sysResourceId = sysResourceId;
    }
}
