package com.szcgc.project.constant;

/**
 * @Author liaohong
 * @create 2020/9/25 15:50
 */
public enum InterestTypeEnum {

    OnceLending("放款前一次性付息"),

    OnceLended("放款后一次性付息"),

    Month("月付"),

    Season("季付"),

    Other("其他"),

    ;

    private String cnName;

    InterestTypeEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }
}
