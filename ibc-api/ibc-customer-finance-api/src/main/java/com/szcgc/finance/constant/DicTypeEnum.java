package com.szcgc.finance.constant;

/**
 * 字典类型枚举
 *
 * @author chenjiaming
 * @date 2022-9-22 09:31:42
 */
public enum DicTypeEnum {
    UNDEFINED("undefined", "未知字典类型"),
    PERIOD("period", "期次"),
    NOTHING_SCORE("nothingScore", "含无的评分"),
    SCORE("score", "评分"),
    CONFORM("conform", "符合程度"),
    EDUCATION("education", "学历水平"),
    FINANCE_SPECIALTY("financeSpecialty", "金融专业"),
    WORK_TIME("workTime", "工作时间"),
    SPECIFICATION("specification", "规范程度"),
    CASH_FLOW_EVALUATION("cashFlowEvaluation", "现金流量评价"),
    LEADER_EDUCATION("leaderEducation", "领导学历水平"),
    LEADER_CREDIT("leaderCredit", "领导信用"),
    ADVANCED("advanced", "先进程度"),
    MARKET_PROFILE("marketProfile", "市场状况"),
    COMPETITION_SITUATION("competitionSituation", "竞争状况"),
    MARKETING_LEVEL("marketingLevel", "营销水平"),
    COMPREHENSIVE_ASSESSMENT("comprehensiveAssessment", "综合评价"),
    EXAMINE_RISK_LEVEL("examineRiskLevel", "风险预警等级"),
    ;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型值
     */
    private String type;

    public String getTypeName() {
        return typeName;
    }

    public String getType() {
        return type;
    }

    DicTypeEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}
