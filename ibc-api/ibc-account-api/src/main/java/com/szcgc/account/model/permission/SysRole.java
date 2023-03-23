package com.szcgc.account.model.permission;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

/**
 * @Author liaohong
 * @create 2020/8/24 17:39
 */
@Entity
@Table(name = "p_sysrole", schema = "gmis_account")
public class SysRole {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "系统角色名")
    @Column(length = 50)
    private String name;    //系统角色名

    @Schema(description = "系统角色描述")
    @Column(length = 200)
    private String description; //系统角色描述

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
