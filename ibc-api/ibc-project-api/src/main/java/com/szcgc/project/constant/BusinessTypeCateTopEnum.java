package com.szcgc.project.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2020/9/27 16:45
 */
public enum BusinessTypeCateTopEnum {

    RongZi("融资"),

    BaoHan("保函"),

    Touzi("投资"),
    ;

    private String cnName;
    private List<BusinessTypeEnum> children;

    BusinessTypeCateTopEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }

    public List<BusinessTypeEnum> getChildren() {
        if (children == null) {
            children = Arrays.stream(BusinessTypeEnum.values()).filter(item -> item.getCate().getTop() == this).collect(Collectors.toList());
        }
        return children;
    }

    public boolean isRz() {
        return this == RongZi;
    }

    public boolean isBh() {
        return this == BaoHan;
    }

}
