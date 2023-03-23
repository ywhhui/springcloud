package com.szcgc.project.constant;

/**
 * @Author liaohong
 * @create 2020/11/18 17:01
 */
public enum ExamineRiskLevelEnum {

    ZC("正常"),

    GZ("关注"),

    YJYJ("一级预警"),

    EJYJ("二级预警"),

    SJYJ("三级预警"),

    ;

    private String cnName;

    ExamineRiskLevelEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }

}
