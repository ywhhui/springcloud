package com.szcgc.project.constant;

/**
 * @Author liaohong
 * @create 2020/9/25 15:58
 */
public enum RepayTypeEnum {

    HalfYear("每半年等额还款"),

    Daily("每日等额还款"),

    Weekly("每周等额还款"),

    Monthly("每月等额还款"),

    Quarterly("每季等额还款"),

    Once("到期一次还款"),

    MonthOnce("每月付息，到期一次还"),

    Other("其他"),

    ;

    private String cnName;

    RepayTypeEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }
}
