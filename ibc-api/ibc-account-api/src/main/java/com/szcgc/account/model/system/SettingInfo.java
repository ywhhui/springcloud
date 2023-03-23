package com.szcgc.account.model.system;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2021/3/22 16:08
 */
@Entity
@Table(name = "s_settinginfo", schema = "gmis_account")
public class SettingInfo {

    public static final String MEET = "MeetingTypeEnum";
    public static final String MeetingProhibition = "MeetingProhibition";
    //public static final String MeetingProhibition_Time = "nomeetingtime";
    public static final String ReportAllowAny = "ReportAllowAny";
    //public static final String ReportAllowDepart = "ReportAllowDepart";

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "设置分组")
    @Column(length = 20)
    private String cate;

    @Schema(description = "设置Key")
    @Column(length = 50)
    private String setKey;

    @Schema(description = "设置Value")
    private int setValue1;

    @Schema(description = "设置Value")
    @Column(length = 400)
    private String setValue2;

    @Schema(description = "备注")
    //@Column(length = 400)
    @Transient
    private String remarks;

    private LocalDateTime createAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getSetKey() {
        return setKey;
    }

    public void setSetKey(String setKey) {
        this.setKey = setKey;
    }

    public int getSetValue1() {
        return setValue1;
    }

    public void setSetValue1(int setValue1) {
        this.setValue1 = setValue1;
    }

    public String getSetValue2() {
        return setValue2;
    }

    public void setSetValue2(String setValue2) {
        this.setValue2 = setValue2;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public boolean isMeeting() {
        return MEET.equals(cate);
    }
}
