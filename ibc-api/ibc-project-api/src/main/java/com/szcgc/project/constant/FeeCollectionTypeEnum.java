package com.szcgc.project.constant;

/**
 * @Author: chenxinli
 * @Date: 2020/10/20 20:37
 * @Description:
 */
public enum FeeCollectionTypeEnum {

    transfer("银行转账"),

    cash("现金"),

    other("其他")



    ;

    private String cnName;

    FeeCollectionTypeEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }
}
