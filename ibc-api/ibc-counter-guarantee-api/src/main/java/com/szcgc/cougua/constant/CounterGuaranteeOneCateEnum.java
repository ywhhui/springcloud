package com.szcgc.cougua.constant;

/**
 * 反担保一级类型
 * @Author liaohong
 * @create 2020/9/24 17:13
 */
public enum CounterGuaranteeOneCateEnum {

    DIYA("抵押", false),
    QLZY("权利质押", false),
    DCZY("动产质押",false),
    ;

    private String cnName;
    private boolean isDeprecated;

    CounterGuaranteeOneCateEnum(String cnName) {
        this.cnName = cnName;
        this.isDeprecated = true;
    }

    CounterGuaranteeOneCateEnum(String cnName, boolean isDeprecated) {
        this.cnName = cnName;
        this.isDeprecated = isDeprecated;
    }

    public String getCnName() {
        return cnName;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

}
