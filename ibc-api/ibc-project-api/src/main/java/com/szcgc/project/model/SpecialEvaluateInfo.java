package com.szcgc.project.model;

import com.szcgc.project.constant.FileCateEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/9/15 16:38
 * 上会申请表
 */
@Entity
@Table(name = "attendinfo", schema = "gmis_project")
public class SpecialEvaluateInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "文件名")
    @Column(length = 50, updatable = false)
    private String name;

    @Schema(description = "材料类型")
    @Column(length = 20, updatable = false)
    @Enumerated(EnumType.STRING)
    private FileCateEnum cate;

    @Schema(description = "文件地址")
    @Column(length = 300)
    private String path;

    @Schema(description = "文件后缀")   //文件后缀
    @Column(length = 10, updatable = false)
    private String suffix;

    @Schema(description = "备注")
    @Column(length = 100)
    private String remarks;

    @Schema(description = "加密盐")
    @Column(length = 200, updatable = false)
    private String salt;

    @Schema(description = "归档人")
    private int archiveBy;

    @Schema(description = "归档时间")
    private LocalDate archiveDate;

    @Schema(description = "归档操作时间")
    private LocalDateTime archiveAt;

    @Schema(description = "确认人")
    private int confirmBy;

    @Schema(description = "是否确认(1:已确认,0:未确认)")
    private int confirmed;

    @Schema(description = "确认操作时间")
    private LocalDateTime confirmAt;

    @Column(updatable = false)
    private int createBy;

    @Column(updatable = false)
    private LocalDateTime createAt;

    private int updateBy;

    private LocalDateTime updateAt;

    private int versionTag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public FileCateEnum getCate() {
        return cate;
    }

    public void setCate(FileCateEnum cate) {
        this.cate = cate;
    }

    public void setCate(String cate) {
        this.cate = FileCateEnum.valueOf(cate);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public LocalDate getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(LocalDate archiveDate) {
        this.archiveDate = archiveDate;
    }

    public LocalDateTime getArchiveAt() {
        return archiveAt;
    }

    public void setArchiveAt(LocalDateTime archiveAt) {
        this.archiveAt = archiveAt;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public LocalDateTime getConfirmAt() {
        return confirmAt;
    }

    public void setConfirmAt(LocalDateTime confirmAt) {
        this.confirmAt = confirmAt;
    }

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

    public int getArchiveBy() {
        return archiveBy;
    }

    public void setArchiveBy(int archiveBy) {
        this.archiveBy = archiveBy;
    }

    public int getConfirmBy() {
        return confirmBy;
    }

    public void setConfirmBy(int confirmBy) {
        this.confirmBy = confirmBy;
    }

    public int getVersionTag() {
        return versionTag;
    }

    public void setVersionTag(int versionTag) {
        this.versionTag = versionTag;
    }

    @PrePersist
    public void onCreate() {
        createAt = LocalDateTime.now();
        updateAt = createAt;
        updateBy = createBy;
        versionTag = 1;
    }

    @PreUpdate
    public void onUpdate() {
        updateAt = LocalDateTime.now();
        versionTag++;
    }

    public boolean isArchived() {
        return archiveBy != 0 && archiveAt != null;
    }

    public boolean isConfirmed() {
        return confirmBy != 0 && confirmAt != null;
    }

    public boolean isDoc() {
        return suffix != null && (suffix.equals(".doc") || suffix.equals(".docx"));
    }
}
