package com.szcgc.project.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/17 16:48
 * //考虑是否把bussinessType定义为enum，启动的时候，利用BusinessSetting进行初始化
 */
public enum BusinessTypeCateEnum {

    DKDB("贷款担保", BusinessTypeCateTopEnum.RongZi, true),

    SXDB("综合授信担保", BusinessTypeCateTopEnum.RongZi, true),

    FZDB("发债担保", BusinessTypeCateTopEnum.RongZi, true),

    CYZJ("市产业技术进步资金委托贷款", BusinessTypeCateTopEnum.RongZi),

    KYZJ("市科技研发资金委托贷款", BusinessTypeCateTopEnum.RongZi),

    WTDK("委托贷款", BusinessTypeCateTopEnum.RongZi),

    RZBH("融资性保函", BusinessTypeCateTopEnum.RongZi, true),

    XEDK("小额贷款", BusinessTypeCateTopEnum.RongZi),

    DD("典当", BusinessTypeCateTopEnum.RongZi),

    BL("保理", BusinessTypeCateTopEnum.RongZi),

    BH("保函", BusinessTypeCateTopEnum.BaoHan, true),

    RZZL("融资租赁", BusinessTypeCateTopEnum.RongZi),

    CXB("诚信榜", BusinessTypeCateTopEnum.RongZi),

    CYDBD("创业担保贷", BusinessTypeCateTopEnum.RongZi),


    LSYL("历史遗留", BusinessTypeCateTopEnum.RongZi, 1),
    ;

    private String cnName;

    private boolean isDeprecated;

    private boolean isSyncCapital;

    private BusinessTypeCateTopEnum top;

    private List<BusinessTypeEnum> children;

    BusinessTypeCateEnum(String cnName, BusinessTypeCateTopEnum top) {
        this(cnName, top, false);
    }

    BusinessTypeCateEnum(String cnName, BusinessTypeCateTopEnum top, boolean isSyncCapital) {
        this.cnName = cnName;
        this.isSyncCapital = isSyncCapital;
        this.top = top;
        this.children = new ArrayList<>(5);
    }

    BusinessTypeCateEnum(String cnName, BusinessTypeCateTopEnum top, int isDeprecated) {
        this.cnName = cnName;
        this.isDeprecated = isDeprecated == 1;
        this.top = top;
        this.children = new ArrayList<>(5);
    }

    public String getCnName() {
        return cnName;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public boolean isSyncCapital() {
        return isSyncCapital;
    }

    public BusinessTypeCateTopEnum getTop() {
        return top;
    }

    public List<BusinessTypeEnum> getChildren() {
        return children;
    }

    public void addChildren(BusinessTypeEnum children) {
        if (children.getCate() != this)
            return;
        this.children.add(children);
    }
}
