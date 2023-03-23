package com.szcgc.project.model;

import com.szcgc.project.jpa.MeetingAttributeConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @Author: chenxinli
 * @Date: 2020/7/29 18:11
 * @Description:
 */
@Data
@Entity
@Table(schema = "gmis_project", name = "meetinginfo")
public class MeetingInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Schema(description = "会议类型")
    private String meetType;

    @Column
    @Schema(description = "会议日期")
    private LocalDate meetDate;

    @Column
    @Schema(description = "开始时间")
    private LocalTime startTime;

    @Column
    @Schema(description = "结束时间")
    private LocalTime endTime;

    @Column
    @Schema(description = "会议地点")
    private String location;

    @Column(length = 100)
    @Schema(description = "备注")
    private String remark;

    @Column
    @Schema(description = "主持人Id")
    private int hostId;

    @Column
    @Schema(description = "评委Id列表")
    @Convert(converter = MeetingAttributeConverter.class)
    private List<Integer> judgeIds;

    @Column
    @Schema(description = "记录员Id")
    private int recorderId;

    @Column(updatable = false)
    private int createBy;

    @Column(updatable = false)
    private LocalDateTime createAt;

    @Column
    @Schema(description = "会议是否提交 0未提交 1已提交")
    private byte confirmed;

    private int updateBy;

    private LocalDateTime updateAt;

//    @JsonIgnore
//    public List<Integer> getJudgeIdList() {
//        if (StringUtils.isEmpty(judgeIds))
//            return Collections.EMPTY_LIST;
//        List<Integer> ids = Arrays.stream(judgeIds.split(","))
//                .map(item -> SundryUtils.tryGetInt(item, 0)).filter(item -> item > 0)
//                .collect(Collectors.toList());
//        return ids;
//    }
//
//    @JsonIgnore
//    public void setJudgeIds(List<Integer> ids) {
//        if (ids == null || ids.isEmpty()) {
//            judgeIds = "";
//            return;
//        }
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0, j = ids.size(); i < j; i++) {
//            sb.append(ids.get(i));
//            if (i < j - 1) {
//                sb.append(',');
//            }
//        }
//        judgeIds = sb.toString();
//    }

    @Schema(description = "主持人名")
    @Transient
    private String hostName;

    @Schema(description = "评委列表名")
    @Transient
    private List<Integer> judgeNames;


    @Schema(description = "记录员名")
    @Transient
    private String recorderName;
}
