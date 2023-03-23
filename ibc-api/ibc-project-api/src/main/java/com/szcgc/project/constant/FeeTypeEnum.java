package com.szcgc.project.constant;

/**
 * @Author: liaohong
 * @Date: 2020/10/20 20:22
 * @Description:
 */
public enum FeeTypeEnum {

    guarantee("担保费"),

    review("评审费"),

    service("融资顾问费"),

    smallservice("小贷服务费"),

    banksz("代收深圳行费用"),

    bankother("代收转开行费用"),

    deposit("保证金"),

    other("其他费用")

    ;

    private String cnName;

    FeeTypeEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }

}
