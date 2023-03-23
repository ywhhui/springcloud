package com.szcgc.cougua.constant;

/**
 * @Author liaohong
 * @create 2020/9/24 17:13
 */
public enum CounterGuaranteeTypeCateEnum {

    FDCDY("房地产抵押", CounterGuaranteeOneCateEnum.DIYA,false),
    ZJGC("在建工程",CounterGuaranteeOneCateEnum.DIYA,false),
    JSYDSYQ("建设用地使用权",CounterGuaranteeOneCateEnum.DIYA,false),
    SCSBYBC("生产设备、原材料、半成品、产品",CounterGuaranteeOneCateEnum.DIYA,false),
    QICHE("汽车",CounterGuaranteeOneCateEnum.DIYA,false),
    CBHKQ("船舶、航空器", CounterGuaranteeOneCateEnum.DIYA,false),
    QTCC("其他财产",CounterGuaranteeOneCateEnum.DIYA,false),

    YSZK("应收账款", CounterGuaranteeOneCateEnum.QLZY,false),

    ZLQUAN("专利权（发明、实用新型、外观设计）", CounterGuaranteeTwoCateEnum.ZSCQ,true),
    SB("商标",CounterGuaranteeTwoCateEnum.ZSCQ,true),
    ZZQUAN("著作权",CounterGuaranteeTwoCateEnum.ZSCQ,true),
    QITAZSCQ("其他知识产权", CounterGuaranteeTwoCateEnum.ZSCQ,true),

    GUPIAO("股票", CounterGuaranteeOneCateEnum.QLZY,false),

    GUQUAN("股权",CounterGuaranteeTwoCateEnum.KJJGQ,true),
    JJFE("基金份额",CounterGuaranteeTwoCateEnum.KJJGQ,true),

    BZJ("保证金", CounterGuaranteeOneCateEnum.QLZY,false),

    BANKCHP("银行承兑汇票",CounterGuaranteeTwoCateEnum.PJL,true),
    SYHP("商业承兑汇票",CounterGuaranteeTwoCateEnum.PJL,true),
    BENP("本票",CounterGuaranteeTwoCateEnum.PJL,true),
    ZHIP("支票",CounterGuaranteeTwoCateEnum.PJL,true),

    CUNKD("存款单",CounterGuaranteeTwoCateEnum.ZQCKD,true),
    ZHAIJ("债券",CounterGuaranteeTwoCateEnum.ZQCKD,true),

    CANGD("仓单",CounterGuaranteeTwoCateEnum.CDTDZY,true),
    TIDAN("提单",CounterGuaranteeTwoCateEnum.CDTDZY,true),

    QTCCQL("其他财产权利", CounterGuaranteeOneCateEnum.QLZY,false),

    CHZY("存货质押", CounterGuaranteeOneCateEnum.DCZY,false),
    QTKZYDC("其他可质押动产", CounterGuaranteeOneCateEnum.DCZY,false),
    ;

    private String cnName;
    private boolean isDeprecated;
    private CounterGuaranteeTwoCateEnum twoCate;
    private CounterGuaranteeOneCateEnum onecate;
    private boolean isChild;

    CounterGuaranteeTypeCateEnum(String cnName, CounterGuaranteeTwoCateEnum twoCate, boolean isChild) {
        this.cnName = cnName;
        this.twoCate = twoCate;
        this.isChild = isChild;
    }

    CounterGuaranteeTypeCateEnum(String cnName, CounterGuaranteeOneCateEnum onecate, boolean isChild) {
        this.cnName = cnName;
        this.onecate = onecate;
        this.isChild = isChild;
    }

    CounterGuaranteeTypeCateEnum(String cnName) {
        this.cnName = cnName;
        this.isDeprecated = true;
    }

    CounterGuaranteeTypeCateEnum(String cnName, boolean isDeprecated) {
        this.cnName = cnName;
        this.isDeprecated = isDeprecated;
    }

    public String getCnName() {
        return cnName;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public CounterGuaranteeTwoCateEnum getTwoCate() {
        return twoCate;
    }

    public void setTwoCate(CounterGuaranteeTwoCateEnum twoCate) {
        this.twoCate = twoCate;
    }

    public CounterGuaranteeOneCateEnum getOnecate() {
        return onecate;
    }

    public void setOnecate(CounterGuaranteeOneCateEnum onecate) {
        this.onecate = onecate;
    }

}
