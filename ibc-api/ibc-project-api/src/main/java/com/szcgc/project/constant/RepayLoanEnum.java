package com.szcgc.project.constant;

/**
 * 还本方案
 */
public enum RepayLoanEnum {

    C01("到期一次性还本","C01"),
    C03("按月等额还本","C03"),
    C05("按季等额还本","C05"),
    C07("按半年等额还本","C07"),
    C08("按年等额还本","C08"),
    C09("等额本金","C09"),
    C10("等额本息","C10"),
    C99("其他","C99"),
    ;

    RepayLoanEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
