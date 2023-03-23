package com.szcgc.account.model.system;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

/**
 * @Author liaohong
 * @create 2020/10/20 15:58
 * 标签管理
 */
@Entity
@Table(name = "s_labelinfo", schema = "gmis_account")
public class LabelInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "标签名")
    @Column(length = 20)
    private String name;

    @Schema(description = "创建人")
    private int accountId;

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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
