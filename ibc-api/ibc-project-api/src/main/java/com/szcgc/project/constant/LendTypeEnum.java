package com.szcgc.project.constant;

/**
 * @Author liaohong
 * @create 2020/10/19 16:28
 */
public enum LendTypeEnum {

    Repeatedly("多次放款"),

    Once("一次放款"),

    Other("其他"),

    ;

    private String cnName;

    LendTypeEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }

    public boolean isRepeat() {
        return this == Repeatedly;
    }
}
