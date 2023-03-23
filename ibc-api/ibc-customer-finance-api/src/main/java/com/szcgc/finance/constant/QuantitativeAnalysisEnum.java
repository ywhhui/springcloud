package com.szcgc.finance.constant;

public enum QuantitativeAnalysisEnum {

    NA("净资产", new double[]{500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000}, CompareTypeEnum.Ascending),

    ALR("资产负债率", new double[]{30, 35, 40, 45, 50, 55, 60, 65, 70, 75}, CompareTypeEnum.Descending),

    LR("流动比率", new double[]{1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2}, CompareTypeEnum.Ascending),

    QR("速动比率", new double[]{0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 1}, CompareTypeEnum.Ascending),

    LTR("长期资产适宜率", new double[]{1.05, 1.1, 1.15, 1.2, 1.25, 1.3, 1.35, 1.4, 1.45, 1.5}, CompareTypeEnum.Ascending),

    GR("齿轮比率", new double[]{25, 30, 35, 40, 45, 50, 55, 60, 65, 70}, CompareTypeEnum.Descending),

    CLR("或有负债比率", new double[]{20, 25, 30, 35, 40, 45, 50, 55, 60, 65}, CompareTypeEnum.Descending),

    LRS("贷款按期偿还率", new double[]{55, 60, 65, 70, 75, 80, 85, 90, 95, 100}, CompareTypeEnum.Ascending),

    AR("年营业收入", new double[]{800, 1600, 2400, 3200, 4000, 4800, 5600, 6400, 7200, 8000}, CompareTypeEnum.Ascending),

    SIR("销售利润率", new double[]{3, 6, 9, 12, 15, 18, 21, 24, 27, 30}, CompareTypeEnum.Ascending),

    ART("应收帐款周转率", new double[]{1.4, 1.8, 2.2, 2.6, 3, 3.4, 3.8, 4.2, 4.6, 5}, CompareTypeEnum.Ascending),

    TRI("存货周转率", new double[]{1.3, 1.6, 1.9, 2.2, 2.5, 2.8, 3.1, 3.4, 3.7, 4}, CompareTypeEnum.Ascending),

    RNA("净资产回报率", new double[]{3, 6, 9, 12, 15, 18, 21, 24, 27, 30}, CompareTypeEnum.Ascending),

    MIG("利息保障倍数", new double[]{2, 4, 6, 8, 10, 12, 14, 16, 18, 20}, CompareTypeEnum.Ascending),

    NAGR("净资产增长率", new double[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50}, CompareTypeEnum.Ascending),

    GSR("销售收入增长率", new double[]{6, 12, 18, 24, 30, 36, 42, 48, 54, 60}, CompareTypeEnum.Ascending),

    PGR("利润增长率", new double[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50}, CompareTypeEnum.Ascending),

    PG("利润增长额", new double[]{50, 100, 150, 200, 250, 300, 350, 400, 450, 500}, CompareTypeEnum.Ascending),

    ;

    private String cnName;

    private double[] scoringCriteria;

    private CompareTypeEnum compareTypeEnum;


    QuantitativeAnalysisEnum(String cnName, double[] scoringCriteria, CompareTypeEnum compareTypeEnum) {
        this.cnName = cnName;
        this.scoringCriteria = scoringCriteria;
        this.compareTypeEnum = compareTypeEnum;
    }

    public String getCnName() {
        return cnName;
    }

    public double[] getScoringCriteria() {
        return scoringCriteria;
    }

    public CompareTypeEnum getCompareTypeEnum() {
        return compareTypeEnum;
    }

}
