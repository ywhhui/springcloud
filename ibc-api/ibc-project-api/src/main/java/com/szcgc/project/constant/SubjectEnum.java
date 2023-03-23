package com.szcgc.project.constant;

/**
 * @Author liaohong
 * @create 2020/10/21 10:47
 * 还款科目
 */
public enum SubjectEnum {

    Principal("本金"),

    Interest("利息"),

    Other("其他"),

    ;

    private String cnName;

    SubjectEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }

    public boolean isPrincipal() {
        return this == Principal;
    }

    public boolean isInterest() {
        return this == Interest;
    }
}
