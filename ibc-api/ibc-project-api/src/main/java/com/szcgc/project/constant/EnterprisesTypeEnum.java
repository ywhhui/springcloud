package com.szcgc.project.constant;

/**
 * @author JINLINGXUAN
 * @create 2021-04-21
 */
public enum EnterprisesTypeEnum {

    MINI("微型企业"),

    SMALL("小型企业"),

    MEDIUM("中型企业"),

    LARGE("大型企业"),
    ;

    private String cnName;

    EnterprisesTypeEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }

}
