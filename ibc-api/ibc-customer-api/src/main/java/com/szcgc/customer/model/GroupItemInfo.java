package com.szcgc.customer.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/9/1 14:45
 * 原本是GrouInfo GroupItemInfo两个表来记录关联关系的，
 * 后来，简化成customerId mainId两个字段表示！
 * 即是，只有GroupItemInfo有效，而且只有customerId mainId两个字段有意义，其他暂时都空
 */
@Entity
@Table(name = "groupiteminfo", schema = "gmis_customer")
public class GroupItemInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Schema(description = "集团Id")
//    private int groupId;

    @Schema(description = "客户Id")
    private int customerId;

    @Schema(description = "核心客户Id")
    private int mainId;

//    @Schema(description = "关系类型")
//    @Column(length = 20)
//    private String cate;
//
//    @Schema(description = "股权比例")
//    @Column(length = 50)
//    private String relationship;

    @Schema(description = "创建人")
    private int createBy;

    @Schema(description = "创建时间")
    private LocalDateTime createAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getMainId() {
        return mainId;
    }

    public void setMainId(int mainId) {
        this.mainId = mainId;
    }

//    public int getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(int groupId) {
//        this.groupId = groupId;
//    }
//
//    public String getCate() {
//        return cate;
//    }
//
//    public void setCate(String cate) {
//        this.cate = cate;
//    }
//
//    public String getRelationship() {
//        return relationship;
//    }
//
//    public void setRelationship(String relationship) {
//        this.relationship = relationship;
//    }

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

    @Override
    public String toString() {
        return "GroupItemInfo{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", mainId=" + mainId +
                ", createBy=" + createBy +
                ", createAt=" + createAt +
                '}';
    }
}
