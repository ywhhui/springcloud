package com.szcgc.account.model;

import com.szcgc.account.jpa.PasswordAttributeConverter;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/8/7 15:47
 */
@Entity
@Table(name = "accountinfo", schema = "gmis_account")
public class AccountInfo {

    public static final int STATUS_NORMAL = 0;  //默认状态(正常状态)
    public static final int STATUS_LOCK = 1;    //锁定状态

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "用户名")
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    @Schema(description = "密码")
    @Column(updatable = false, length = 200, nullable = false)
    @Convert(converter = PasswordAttributeConverter.class)
    private String password;

    @Schema(description = "真实姓名")
    @Column(length = 10, nullable = false)
    private String realName;

    @Schema(description = "手机号")
    @Column(length = 20, nullable = false)
    private String phone;

    @Schema(description = "头像")
    @Column(length = 100)
    private String avatar;

    @Schema(description = "所属部门")
    private int departmentId;

    @Schema(description = "状态")
    private int ibcStatus;

    @Schema(description = "用户OAId")
    private int oaId;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getIbcStatus() {
        return ibcStatus;
    }

    public void setIbcStatus(int ibcStatus) {
        this.ibcStatus = ibcStatus;
    }

    public boolean isEnable() {
        return ibcStatus == STATUS_NORMAL;
    }

    public int getOaId() {
        return oaId;
    }

    public void setOaId(int oaId) {
        this.oaId = oaId;
    }

    private int createBy;
    private LocalDateTime createAt;
    private int updateBy;
    private LocalDateTime updateAt;

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @PrePersist
    public void onCreate(){
        createAt = LocalDateTime.now();
        updateAt= createAt;
    }

    @PreUpdate
    public void onUpdate(){
        updateAt = LocalDateTime.now();
    }

}
