package com.szcgc.project.model;

import com.szcgc.project.jpa.MeetingAttributeConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/15 16:52
 * 上会申请表
 */
@Data
@Entity
@Table(name = "attendinfo", schema = "gmis_project")
public class AttendInfo {

    public static final byte CONFIRM_YES = 1;

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "申请人Id")
    @Column(updatable = false)
    private int accountId;

    @Schema(description = "申请人名")
    @Column(length = 20, updatable = false)
    private String accountName;

    @Schema(description = "拟同意金额")
    @Column(updatable = false)
    private long amount;

//    @Schema(description = "申请期限")
//    @Column(updatable = false)
//    private int during;    //申请期限
//
//    @Schema(description = "申请期限单位")
//    @Column(updatable = false)
//    private int duringUnit;    //申请期限单位

    @Schema(description = "拟上会日期")
    @Column(updatable = false)
    private LocalDate meetingAt;

    @Schema(description = "会议组别")
    @Column(length = 20, updatable = false)
    private String opinion;

    @Schema(description = "意见详情")
    @Column(updatable = false, length = 200)
    private String remarks;

    @Column(updatable = false)
    private LocalDateTime createAt;

    @Schema(description = "会议Id")
    @Column
    private int meetingId;

    @Schema(description = "主持人Id")
    @Column
    private int hostId;

    @Schema(description = "评委Id列表")
    @Convert(converter = MeetingAttributeConverter.class)
    @Column
    private List<Integer> judgeIds;

    @Schema(description = "会议顺序")
    @Column
    private int meetingOrder;

    @Column
    @Schema(description = "是否可提交 0不可提交 1可提交")
    private byte confirmed;

    private int updateBy;

    private LocalDateTime updateAt;


    @Schema(description = "主持人名")
    @Transient
    private String hostName;

    @Schema(description = "评委列表名")
    @Transient
    private List<String> judgeNames;

    @Schema(description = "任务Id")
    @Transient
    private String taskId;

}
