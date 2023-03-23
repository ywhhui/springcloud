package com.szcgc.account.model.system;

import com.szcgc.comm.util.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/8/7 15:48
 */
@Entity
@Table(name = "s_agreetempinfo", schema = "gmis_account")
public class AgreementTemplateInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "名称")
    @Column(length = 50, updatable = false)
    private String name;

    @Schema(description = "备注")
    @Column(length = 200, updatable = false)
    private String remarks;

    @Schema(description = "模板文件地址")
    @Column(length = 300, updatable = false)
    private String path;    //如果不是模板文件，而是文件夹，则这个字段为空

    @Schema(description = "状态,1:正常,2:隐藏;3:删除")
    private int usable;

    @Schema(description = "父级节点")
    private int templateId;    //如果是文件夹，则表示父级节点。如果是模板文件，则表示所属文件夹

    @Schema(description = "排序")
    private int orderNo;

    @Schema(description = "腾讯合同管理模板No")
    @Column(length = 300, updatable = false)
    private String tencentTemplateNo;

    @Schema(description = "腾讯合同填充字段")
    @Column(length = 2000, updatable = false)
    private String tencentTemplateStr;

//    @Schema(description = "合同字号类型")
//    @Enumerated(EnumType.STRING)
//    private ContractNoEnum contractNo;

    @Column(updatable = false)
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getUsable() {
        return usable;
    }

    public void setUsable(int usable) {
        this.usable = usable;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isNormal() {
        return usable == 1;
    }

    public boolean isFile() {
        return !StringUtils.isEmpty(path);
    }

    public String getTencentTemplateNo() {
        return tencentTemplateNo;
    }

    public void setTencentTemplateNo(String tencentTemplateNo) {
        this.tencentTemplateNo = tencentTemplateNo;
    }

    public String getTencentTemplateStr() {
        return tencentTemplateStr;
    }

    public void setTencentTemplateStr(String tencentTemplateStr) {
        this.tencentTemplateStr = tencentTemplateStr;
    }

}
