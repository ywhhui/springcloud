package com.szcgc.finance.vo;

/**
 * @Author: chenxinli
 * @Date: 2020/12/10 9:24
 * @Description:
 */
public enum PostAnalysis {

    CCE("货币资金"),

    AR("应收"),

    FD("存货"),

    DE("待摊费用"),

    LTI("长期投资"),

    FA("固定资产"),

    TA("总资产"),

    LSL("借款"),

    PAY("应付"),

    AT("应交税金"),

    CUC("实收资本"),

    PIS("资本公积"),

    RE("留存收益"),

    LAE("负债及权益"),

    OI("销售收入"),

    CAT("成本税金"),

    PE("期间费用"),

    IFI("投资收益"),

    IT("所得税"),

    NeP("净利润"),

    ;


    private String cnName;

    PostAnalysis(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }
}
