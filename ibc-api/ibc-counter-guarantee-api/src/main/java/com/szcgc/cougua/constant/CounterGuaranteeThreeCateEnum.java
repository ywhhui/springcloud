package com.szcgc.cougua.constant;

/**
 * 反担保三级级类型
 * @Author liaohong
 * @create 2020/9/24 17:13
 */
public enum CounterGuaranteeThreeCateEnum {

    ZLQUAN("专利权（发明、实用新型、外观设计）", CounterGuaranteeTwoCateEnum.ZSCQ,true),
    SB("商标",CounterGuaranteeTwoCateEnum.ZSCQ,true),
    ZZQUAN("著作权",CounterGuaranteeTwoCateEnum.ZSCQ,true),
    QITAZSCQ("其他知识产权", CounterGuaranteeTwoCateEnum.ZSCQ,true),

    GUQUAN("股权",CounterGuaranteeTwoCateEnum.KJJGQ,true),
    JJFE("基金份额",CounterGuaranteeTwoCateEnum.KJJGQ,true),

    BANKCHP("银行承兑汇票",CounterGuaranteeTwoCateEnum.PJL,true),
    SYHP("商业承兑汇票",CounterGuaranteeTwoCateEnum.PJL,true),
    BENP("本票",CounterGuaranteeTwoCateEnum.PJL,true),
    ZHIP("支票",CounterGuaranteeTwoCateEnum.PJL,true),

    CUNKD("存款单",CounterGuaranteeTwoCateEnum.ZQCKD,true),
    ZHAIJ("债券",CounterGuaranteeTwoCateEnum.ZQCKD,true),

    CANGD("仓单",CounterGuaranteeTwoCateEnum.CDTDZY,true),
    TIDAN("提单",CounterGuaranteeTwoCateEnum.CDTDZY,true),
    ;

    private String cnName;
    private boolean isDeprecated;
    private CounterGuaranteeTwoCateEnum cate;
    private boolean isChild;

    CounterGuaranteeThreeCateEnum(String cnName, CounterGuaranteeTwoCateEnum cate, boolean isChild) {
        this.cnName = cnName;
        this.cate = cate;
        this.isChild = isChild;
    }

    CounterGuaranteeThreeCateEnum(String cnName) {
        this.cnName = cnName;
        this.isDeprecated = true;
    }

    CounterGuaranteeThreeCateEnum(String cnName, boolean isDeprecated) {
        this.cnName = cnName;
        this.isDeprecated = isDeprecated;
    }

    public String getCnName() {
        return cnName;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public CounterGuaranteeTwoCateEnum getCate() {
        return cate;
    }

    public void setCate(CounterGuaranteeTwoCateEnum cate) {
        this.cate = cate;
    }

}
