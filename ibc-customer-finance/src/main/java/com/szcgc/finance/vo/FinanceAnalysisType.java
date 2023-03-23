package com.szcgc.finance.vo;

/**
 * @Author: chenxinli
 * @Date: 2020/12/9 17:47
 * @Description:
 */
public enum FinanceAnalysisType {


    BaseFinanceStat("基本财务数据",1,4),

    FinanceAnalysis("财务分析",3,3),

    BalanceBref("资产负债简表",1,4),

    IncomeStatementBref("损益简表",1,4),

    CashFlowsBref("现金流量简表",2,2),

    PostAnalysisBref("保后检查简表",1,4),

    QuantitiveAnalysis("定量分析",1,1),

    QualitativeAnalysis("定性分析",1,1),

    CreditRating("资信评分",1,1)
    ;


    private String cnName;

    private int minTermSize;

    private  int maxTermSize;

    FinanceAnalysisType(String cnName, int minTermSize, int maxTermSize) {
        this.cnName = cnName;
        this.minTermSize = minTermSize;
        this.maxTermSize = maxTermSize;
    }

    public String getCnName() {
        return cnName;
    }

    public int getMinTermSize() {
        return minTermSize;
    }

    public int getMaxTermSize() {
        return maxTermSize;
    }

}
