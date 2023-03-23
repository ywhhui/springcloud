package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author JINLINGXUAN
 * @create 2021-03-23
 * 补充材料信息
 */

@Entity
@Table(name = "additionalinfo", schema = "gmis_project")
public class AdditionalInfo {

  @Id
  @Column(name = "id", length = 11)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Schema(description = "项目Id")
  @Column(updatable = false)
  private int projectId;

  @Schema(description = "材料名称")
  @Column(length = 100, updatable = false)
  private String fileName;

  @Schema(description = "文件Id")
  @Column(length = 11)
  private int fileId;

  @Schema(description = "确认归档人")
  private int archiveBy;

  @Schema(description = "确认归档操作时间")
  private LocalDateTime archiveAt;

  @Column(updatable = false)
  private int createBy;

  @Column(updatable = false)
  private LocalDateTime createAt;

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

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public int getFileId() {
    return fileId;
  }

  public void setFileId(int fileId) {
    this.fileId = fileId;
  }

  public int getArchiveBy() {
    return archiveBy;
  }

  public void setArchiveBy(int archiveBy) {
    this.archiveBy = archiveBy;
  }

  public LocalDateTime getArchiveAt() {
    return archiveAt;
  }

  public void setArchiveAt(LocalDateTime archiveAt) {
    this.archiveAt = archiveAt;
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

  @PrePersist
  public void onCreate() {
    createAt = LocalDateTime.now();
  }

  public boolean isConfirmed() {
    return archiveBy != 0 && archiveAt != null;
  }


}
