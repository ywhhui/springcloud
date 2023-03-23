package com.szcgc.cougua.constant;

/**
 * @Author liaohong
 * @create 2020/11/16 15:09
 */
public enum CounterGuaranteeTypePropEnum {

    fdcEstateNo("不动产证号", CounterGuaranteeTypeCateEnum.FDCDY, 1, false, true),
    fdcObligeeName("权利人", CounterGuaranteeTypeCateEnum.FDCDY, 1),
    fdcAddress("详细地址", CounterGuaranteeTypeCateEnum.FDCDY, 1),
    fdcDistrict("行政区域", CounterGuaranteeTypeCateEnum.FDCDY, 1),
    fdcVillageName("小区名)", CounterGuaranteeTypeCateEnum.FDCDY, 2),
    fdcBuilding("楼栋", CounterGuaranteeTypeCateEnum.FDCDY, 1),
    fdcFloor("楼层", CounterGuaranteeTypeCateEnum.FDCDY, 1),
    fdcUnit("单元)", CounterGuaranteeTypeCateEnum.FDCDY, 2),
    fdcAreaMeter("面积(平方米)", CounterGuaranteeTypeCateEnum.FDCDY, 1),
    fdcCompleteDate("建成日期", CounterGuaranteeTypeCateEnum.FDCDY, 2),
    fdcPurpose("用途", CounterGuaranteeTypeCateEnum.FDCDY, 1),
    fdcUserStatus("使用状态", CounterGuaranteeTypeCateEnum.FDCDY, 2),
    fdcRemarks("备注", CounterGuaranteeTypeCateEnum.FDCDY, 2),
    fdcOriginalValue("原值", CounterGuaranteeTypeCateEnum.FDCDY, 2),

    fPriceOne("每平方米原值(元/平方米)", CounterGuaranteeTypeCateEnum.FDCDY, 3),
    fDiscountNo("打折系数(%)", CounterGuaranteeTypeCateEnum.FDCDY, 3),
    fDiscountRates("税率(%)", CounterGuaranteeTypeCateEnum.FDCDY, 3),
    fAssessValueOneNotRate("税前每平米评估值(元)", CounterGuaranteeTypeCateEnum.FDCDY, 2),
    fAssessValueOneOfRate("税后每平米评估值(元)", CounterGuaranteeTypeCateEnum.FDCDY, 3),
    fAssessValueNotRate("税前评估值(元)", CounterGuaranteeTypeCateEnum.FDCDY, 3),
    fAssessValueOfRate("税后评估值(元)", CounterGuaranteeTypeCateEnum.FDCDY, 3),
    fWarrantyArtNotRate("税前可担保额(元)", CounterGuaranteeTypeCateEnum.FDCDY, 3),
    fWarrantyArtOfRate("税后可担保额(元)", CounterGuaranteeTypeCateEnum.FDCDY, 3),

    zgcName("工程名称", CounterGuaranteeTypeCateEnum.ZJGC, 2),
    zgcUnitOwner("建设单位(业主)", CounterGuaranteeTypeCateEnum.ZJGC, 2),
    zgcUnit("建设单位", CounterGuaranteeTypeCateEnum.ZJGC, 2),
    zgcScale("工程规模", CounterGuaranteeTypeCateEnum.ZJGC, 3),
    zgcSchedule("工期", CounterGuaranteeTypeCateEnum.ZJGC, 3),
    zgcDescribe("描述", CounterGuaranteeTypeCateEnum.ZJGC, 3),

    jsqEstateNo("不动产证号", CounterGuaranteeTypeCateEnum.JSYDSYQ, 2),
    jsqObligeeName("权利人", CounterGuaranteeTypeCateEnum.JSYDSYQ, 2),
    jsqAddress("详细地址", CounterGuaranteeTypeCateEnum.JSYDSYQ, 2),
    jsqAreaMeter("面积(平方米)", CounterGuaranteeTypeCateEnum.JSYDSYQ, 3),
    jsqAreaMeters("总建筑面积(平方米)", CounterGuaranteeTypeCateEnum.JSYDSYQ, 3),
    jsqYear("使用年限", CounterGuaranteeTypeCateEnum.JSYDSYQ, 3),
    jsqCompleteDate("建成日期", CounterGuaranteeTypeCateEnum.JSYDSYQ, 3),
    jsqUserStatus("用途/目前状态", CounterGuaranteeTypeCateEnum.JSYDSYQ, 2),
    jsqRemarks("备注", CounterGuaranteeTypeCateEnum.JSYDSYQ, 2),
    jsqOriginalValue("原值", CounterGuaranteeTypeCateEnum.JSYDSYQ, 2),

    jPriceOne("每平方米原值(元/平方米)", CounterGuaranteeTypeCateEnum.JSYDSYQ, 3),
    jDiscountNo("打折系数(%)", CounterGuaranteeTypeCateEnum.JSYDSYQ, 3),
    jAssessValueOne("每平米评估值(元)", CounterGuaranteeTypeCateEnum.JSYDSYQ, 2),
    jAssessValue("评估值(元)", CounterGuaranteeTypeCateEnum.JSYDSYQ, 3),
    jWarrantyValueOne("每平米担保价(元)", CounterGuaranteeTypeCateEnum.JSYDSYQ, 3),
    jWarrantyArt("可担保额(元)", CounterGuaranteeTypeCateEnum.JSYDSYQ, 3),


    scsbEstateNo("设备名称", CounterGuaranteeTypeCateEnum.SCSBYBC, 2),
    scsbType("型号规格", CounterGuaranteeTypeCateEnum.SCSBYBC, 2),
    scsbNum("数量", CounterGuaranteeTypeCateEnum.SCSBYBC, 2),
    scsbDegree("成新率(%)", CounterGuaranteeTypeCateEnum.SCSBYBC, 3),
    scsbManufacturer("产地", CounterGuaranteeTypeCateEnum.SCSBYBC, 3),
    scsbAcquisitionDate("购置时间", CounterGuaranteeTypeCateEnum.SCSBYBC, 3),
    scsbProductionDate("出厂时间", CounterGuaranteeTypeCateEnum.SCSBYBC, 3),
    scsbPhoneNo("联系电话", CounterGuaranteeTypeCateEnum.SCSBYBC, 2),
    scsbContactName("联系人", CounterGuaranteeTypeCateEnum.SCSBYBC, 2),

    carOwner("车主", CounterGuaranteeTypeCateEnum.QICHE, 1),
    carBrandModels("品牌型号", CounterGuaranteeTypeCateEnum.QICHE, 1),
    carPlateNo("车牌", CounterGuaranteeTypeCateEnum.QICHE, 1),
    carValuationBase("计价基础", CounterGuaranteeTypeCateEnum.QICHE, 0),
    carDegree("成新率(%)", CounterGuaranteeTypeCateEnum.QICHE, 0),
    carDiscountRate("打折比例(%)", CounterGuaranteeTypeCateEnum.QICHE, 0),
    carType("车辆类型", CounterGuaranteeTypeCateEnum.QICHE, 0),
    carEngineNo("发动机号", CounterGuaranteeTypeCateEnum.QICHE, 0),
    carVin("车架号", CounterGuaranteeTypeCateEnum.QICHE, 0),
    carPurchasingDate("购买日期", CounterGuaranteeTypeCateEnum.QICHE, 0),
    carVehicleRegistration("机动车登记编号", CounterGuaranteeTypeCateEnum.QICHE, 0),
    carDocumentOfTitle("物权凭证登记号", CounterGuaranteeTypeCateEnum.QICHE, 0),
    carOthers("其他", CounterGuaranteeTypeCateEnum.QICHE, 0),

    shipRegisterNo("登记号码", CounterGuaranteeTypeCateEnum.CBHKQ, 1),
    shipName("船名", CounterGuaranteeTypeCateEnum.CBHKQ, 1),
    shipPortRegistry("船籍港", CounterGuaranteeTypeCateEnum.CBHKQ, 1),
    shipOwner("所有人", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipLegalRepresentative("法定代表人", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipType("船舶类型", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipMaterial("船体材料", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipAddress("造船地点", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipCompleteDate("建成日期", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipValue("船舶价值(元)", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipLength("船体长", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipWide("船体宽", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipHigh("船体高", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipTonnage("总吨位", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipTonnages("载重吨位", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipHostType("主机种类", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipEngineNo("发动机数量", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipPower("功率", CounterGuaranteeTypeCateEnum.CBHKQ, 0),
    shipVoucherNumber("物权凭证号", CounterGuaranteeTypeCateEnum.CBHKQ, 0),

    otherType("类型", CounterGuaranteeTypeCateEnum.QTCC, 0),
    otherDescribe("描述", CounterGuaranteeTypeCateEnum.QTCC, 0),


    receiveType("应收账款类型", CounterGuaranteeTypeCateEnum.YSZK, 2),
    receiveName("应收账款名称", CounterGuaranteeTypeCateEnum.YSZK, 2),
    receiveDesc("应收账款账期", CounterGuaranteeTypeCateEnum.YSZK, 2),

    patentName("专利名称", CounterGuaranteeTypeCateEnum.ZLQUAN, 2),
    patentType("专利类型", CounterGuaranteeTypeCateEnum.ZLQUAN, 2),
    patentUser("专利权人", CounterGuaranteeTypeCateEnum.ZLQUAN, 2),
    patentNo("专利号", CounterGuaranteeTypeCateEnum.ZLQUAN, 2),
    patentNoticeDate("授权公告日", CounterGuaranteeTypeCateEnum.ZLQUAN, 2),
    patentPublicNo("申请公布号", CounterGuaranteeTypeCateEnum.ZLQUAN, 2),

    sbRegisterNo("商标注册号", CounterGuaranteeTypeCateEnum.SB, 2),
    sbRegister("注册人", CounterGuaranteeTypeCateEnum.SB, 2),
    sbRegisterDate("注册日期", CounterGuaranteeTypeCateEnum.SB, 2),
    sbValidEndDate("有效期至", CounterGuaranteeTypeCateEnum.SB, 2),
    sbService("商品/服务", CounterGuaranteeTypeCateEnum.SB, 2),
    sbRegisterAddress("注册地址", CounterGuaranteeTypeCateEnum.SB, 2),

    softName("软件名称", CounterGuaranteeTypeCateEnum.ZZQUAN, 2),
    softUser("著作权人", CounterGuaranteeTypeCateEnum.ZZQUAN, 2),
    softNo("登记号", CounterGuaranteeTypeCateEnum.ZZQUAN, 2),
    softCertificateNo("证书号", CounterGuaranteeTypeCateEnum.ZZQUAN, 2),
    softCompleteDate("开发完成日期", CounterGuaranteeTypeCateEnum.ZZQUAN, 2),
    softPublicDate("首次发表日期", CounterGuaranteeTypeCateEnum.ZZQUAN, 2),
    softPowerDate("权利取得日期", CounterGuaranteeTypeCateEnum.ZZQUAN, 2),
    softPowerRange("权利范围", CounterGuaranteeTypeCateEnum.ZZQUAN, 2),

    propertyName("知识产权名称", CounterGuaranteeTypeCateEnum.QITAZSCQ, 2),
    propertyNo("知识产权专利登记号", CounterGuaranteeTypeCateEnum.QITAZSCQ, 2),
    propertyPublicDate("授权公告日", CounterGuaranteeTypeCateEnum.QITAZSCQ, 2),

    gpStockAccount("股票账号", CounterGuaranteeTypeCateEnum.GUPIAO, 1),
    gpStockName("股票名称", CounterGuaranteeTypeCateEnum.GUPIAO, 1, false, true),
    gpStockCode("股票代码", CounterGuaranteeTypeCateEnum.GUPIAO, 1, false, true),
    gpStockUnitPriceAmt("当天收单价(元)", CounterGuaranteeTypeCateEnum.GUPIAO, 1, false, true),
    gpStockNum("持有数量(股)", CounterGuaranteeTypeCateEnum.GUPIAO, 1),
    gpStockWarningLine("预警线(元)", CounterGuaranteeTypeCateEnum.GUPIAO, 2),
    gpStockOpenLine("平仓线(元)", CounterGuaranteeTypeCateEnum.GUPIAO, 2),
    gpStockContactName("质押人", CounterGuaranteeTypeCateEnum.GUPIAO, 2),

    gpDiscountNo("打折系数(%)", CounterGuaranteeTypeCateEnum.GUPIAO, 3),
    warrantyDiscountNo("担保额打折系数(%)", CounterGuaranteeTypeCateEnum.GUPIAO, 2),
    gpPriceOne("每股价格(元)", CounterGuaranteeTypeCateEnum.GUPIAO, 3),
    jgpAssessValue("评估值(元)", CounterGuaranteeTypeCateEnum.GUPIAO, 3),
    gpWarrantyArt("担保额(元)", CounterGuaranteeTypeCateEnum.GUPIAO, 3),


    stockInvestName("所投资企业", CounterGuaranteeTypeCateEnum.GUQUAN, 1, false, true),
    stockRate("股权比例(%)", CounterGuaranteeTypeCateEnum.GUQUAN, 1, false, true),
    stockInvestAmt("出资金额(元)", CounterGuaranteeTypeCateEnum.GUQUAN, 2),
    stockInvestType("出资方式", CounterGuaranteeTypeCateEnum.GUQUAN, 1),
    stockRegisterAmt("注册资本(元)", CounterGuaranteeTypeCateEnum.GUQUAN, 2),
    stockNetAsset("净资产(元)", CounterGuaranteeTypeCateEnum.GUQUAN, 2),

    fundDescribe("描述", CounterGuaranteeTypeCateEnum.JJFE, 2),

    bondName("账户名称", CounterGuaranteeTypeCateEnum.BZJ, 1),
    bondAmt("金额(元)", CounterGuaranteeTypeCateEnum.BZJ, 2),
    bondTerm("期限(天)", CounterGuaranteeTypeCateEnum.BZJ, 2),

    bankTicketDescribe("描述", CounterGuaranteeTypeCateEnum.BANKCHP, 2),

    busTicketDescribe("描述", CounterGuaranteeTypeCateEnum.SYHP, 2),

    benTicketDescribe("描述", CounterGuaranteeTypeCateEnum.BENP, 2),

    zhiTicketDescribe("描述", CounterGuaranteeTypeCateEnum.ZHIP, 2),

    accountBankName("银行名称", CounterGuaranteeTypeCateEnum.CUNKD, 1, false, true),
    accountName("账户名称", CounterGuaranteeTypeCateEnum.CUNKD, 1, false, true),
    accountDepositType("存款性质", CounterGuaranteeTypeCateEnum.CUNKD, 2),
    accountType("账户状态", CounterGuaranteeTypeCateEnum.CUNKD, 1),
    accountStartDate("起始日", CounterGuaranteeTypeCateEnum.CUNKD, 2),
    accountEndDate("终止日", CounterGuaranteeTypeCateEnum.CUNKD, 2),

    bondDescribe("描述", CounterGuaranteeTypeCateEnum.ZHAIJ, 2),

    cBillDescribe("描述", CounterGuaranteeTypeCateEnum.CANGD, 2),

    tBillDescribe("描述", CounterGuaranteeTypeCateEnum.TIDAN, 2),

    otherZsDescribe("描述", CounterGuaranteeTypeCateEnum.QTCCQL, 2),

    goodsName("货物名称", CounterGuaranteeTypeCateEnum.CHZY, 1, false, true),
    goodsOwner("所有人", CounterGuaranteeTypeCateEnum.CHZY, 1, false, true),
    goodsAddress("存放地点", CounterGuaranteeTypeCateEnum.CHZY, 2),
    goodsValues("价值", CounterGuaranteeTypeCateEnum.CHZY, 1),
    goodsUnit("计量单位", CounterGuaranteeTypeCateEnum.CHZY, 2),
    goodsNumber("数量", CounterGuaranteeTypeCateEnum.CHZY, 2),

    otherGoodsDescribe("描述", CounterGuaranteeTypeCateEnum.QTKZYDC, 2),

    ;

    private String cnName;
    private CounterGuaranteeTypeCateEnum cate;
    private int isRequire;  //1新增必填  2新增非必填  3新增不可填(即该字段由评估师来录入)
    private boolean isDetail;
    private boolean isTag;

    CounterGuaranteeTypePropEnum(String cnName, CounterGuaranteeTypeCateEnum cate, int isRequire) {
        this(cnName, cate, isRequire, true, false);
    }

    CounterGuaranteeTypePropEnum(String cnName, CounterGuaranteeTypeCateEnum cate, int isRequire, boolean isDetail, boolean isTag) {
        this.cnName = cnName;
        this.cate = cate;
        this.isRequire = isRequire;
        this.isDetail = isDetail;
        this.isTag = isTag;
    }

    public String getCnName() {
        return cnName;
    }

    public CounterGuaranteeTypeCateEnum getCate() {
        return cate;
    }

    public int getIsRequire() {
        return isRequire;
    }

    public boolean isDetail() {
        return isDetail;
    }

    public boolean isTag() {
        return isTag;
    }

    public boolean isEditable() {
        return isRequire == 1 || isRequire == 2;
    }
}
