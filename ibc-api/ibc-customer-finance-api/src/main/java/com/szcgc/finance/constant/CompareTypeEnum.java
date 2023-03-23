package com.szcgc.finance.constant;

/**
 * @Author: liangzhongqiang
 * @Date: 2021/04/18 10:29
 * @Description:
 */
public enum CompareTypeEnum {

    Ascending("升序"),

    Descending("降序")

    ;


    private String cnName;

    CompareTypeEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }
}
