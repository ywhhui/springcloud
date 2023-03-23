package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/9/25 17:13
 * 原始申请信息
 */
@Entity
@Table(name = "originalinfo", schema = "gmis_project")
public class OriginalInfo extends ProjectSuperInfo {

    @Id
    @Column(name = "id", length = 11)
    private int id;

    @Schema(description = "生成时间")
    @Column(updatable = false)
    private LocalDateTime createAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
