package com.szcgc.project.constant;

/**
 * @author liaohong
 * @create 2021-06-18
 */
public enum SigningTypeEnum {

    Face("面签"),

    Video("视频签约"),

    Other("其他方式"),

    ;

    private String cnName;

    SigningTypeEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }
}
