package com.szcgc.cougua.constant;

/**
 * 反担保二级类型
 * @Author liaohong
 * @create 2020/9/24 17:13
 */
public enum CounterGuaranteeTwoCateEnum {

    FDCDY("房地产抵押", CounterGuaranteeOneCateEnum.DIYA,false),
    ZJGC("在建工程",CounterGuaranteeOneCateEnum.DIYA,false),
    JSYDSYQ("建设用地使用权",CounterGuaranteeOneCateEnum.DIYA,false),
    SCSBYBC("生产设备、原材料、半成品、产品",CounterGuaranteeOneCateEnum.DIYA,false),
    QICHE("汽车",CounterGuaranteeOneCateEnum.DIYA,false),
    CBHKQ("船舶、航空器", CounterGuaranteeOneCateEnum.DIYA,false),
    QTCC("其他财产",CounterGuaranteeOneCateEnum.DIYA,false),

    YSZK("应收账款", CounterGuaranteeOneCateEnum.QLZY,false),
    ZSCQ("知识产权", CounterGuaranteeOneCateEnum.QLZY,false),
    GUPIAO("股票", CounterGuaranteeOneCateEnum.QLZY,false),
    KJJGQ("可以转让的基金份额、股权", CounterGuaranteeOneCateEnum.QLZY,false),
    BZJ("保证金", CounterGuaranteeOneCateEnum.QLZY,false),
    PJL("票据类", CounterGuaranteeOneCateEnum.QLZY,false),
    ZQCKD("债券、存款单", CounterGuaranteeOneCateEnum.QLZY,false),
    CDTDZY("仓单、提单质押", CounterGuaranteeOneCateEnum.QLZY,false),
    QTCCQL("其他财产权利", CounterGuaranteeOneCateEnum.QLZY,false),

    CHZY("存货质押", CounterGuaranteeOneCateEnum.DCZY,false),
    QTKZYDC("其他可质押动产", CounterGuaranteeOneCateEnum.DCZY,false),
    ;
    private String cnName;
    private boolean isDeprecated;
    private CounterGuaranteeOneCateEnum cate;
    private boolean isChild;

    CounterGuaranteeTwoCateEnum(String cnName) {
        this.cnName = cnName;
        this.isDeprecated = true;
    }

    CounterGuaranteeTwoCateEnum(String cnName, boolean isDeprecated) {
        this.cnName = cnName;
        this.isDeprecated = isDeprecated;
    }

    CounterGuaranteeTwoCateEnum(String cnName, CounterGuaranteeOneCateEnum cate, boolean isChild) {
        this.cnName = cnName;
        this.cate = cate;
        this.isChild = isChild;
    }


    public String getCnName() {
        return cnName;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public CounterGuaranteeOneCateEnum getCate() {
        return cate;
    }

    public void setCate(CounterGuaranteeOneCateEnum cate) {
        this.cate = cate;
    }


}
