package com.szcgc.project.constant;

/**
 * @Author liaohong
 * @create 2020/11/24 16:17
 */
public enum FileCateTopEnum {

    CL("材料"),
    HT("合同"),
    BG("报告"),
    ;

    private String cnName;

    FileCateTopEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }

    public boolean isCl() {
        return this == CL;
    }

    public boolean isHt() {
        return this == HT;
    }

    public boolean isBg() {
        return this == BG;
    }
}
