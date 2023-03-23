package com.szcgc.account.model.permission;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

/**
 * @Author liaohong
 * @create 2020/8/24 17:39
 */
@Entity
@Table(name = "p_sysres", schema = "gmis_account")
public class SysResource {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int cate;  //系统资源类型(保留)

    @Column(length = 20, updatable = false)
    private String methodName;  //methodName

    @Column(length = 30, updatable = false)
    private String ctrlName;  //

    @Column(length = 100, updatable = false)
    private String reqMapping;  //

    @Column(length = 10, updatable = false)
    private String reqMethod;  //

    @Schema(description = "系统资源描述")
    @Column(length = 50)
    private String description;  //系统资源描述

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getCtrlName() {
        return ctrlName;
    }

    public void setCtrlName(String ctrlName) {
        this.ctrlName = ctrlName;
    }

    public String getReqMapping() {
        return reqMapping;
    }

    public void setReqMapping(String reqMapping) {
        this.reqMapping = reqMapping;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return ctrlName + methodName + reqMapping;
    }

}
