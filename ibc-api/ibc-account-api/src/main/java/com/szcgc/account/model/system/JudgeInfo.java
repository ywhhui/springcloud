package com.szcgc.account.model.system;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: liaohong
 * @Date: 2020/7/29 15:27
 * @Description: 评委实体类
 */

@Entity
@Table(name = "s_judgeinfo", schema = "gmis_account")
public class JudgeInfo {

    @Id
    @Column(name = "id", length = 11)
    private int id;

    @Column(length = 50)
    @Schema(description = "评委名")
    private String name;

    @Column(length = 50)
    @Schema(description = "可参与的会议类型ID，用#分隔，例：1#2#4")
    private String meetingType; //可参与的会议类型，用#分割。例：1#2#4

    @Column(updatable = false)
    private LocalDateTime createAt;

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

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public List<String> getMeeting() {
        return Arrays.stream(meetingType.split("#")).map(s -> s.trim()).collect(Collectors.toList());
    }

    public boolean contains(String type) {
        return meetingType.indexOf(type) >= 0;
    }

    @PrePersist
    public void onCreate() {
        createAt = LocalDateTime.now();
    }
}
