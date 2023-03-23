package com.szcgc.finance.constant;

import com.szcgc.comm.model.IbcTree;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.compress.utils.Lists;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 财务模块字典
 *
 * @author chenjiaming
 * @date 2022-9-30 11:48:47
 */
public class FinanceConfigDic {

    public static final FinanceConfigDic INSTANCE = new FinanceConfigDic();

    @Schema(description = "期次")
    public Map<String, String> period = new HashMap<>(4);
    public List<IbcTree> periodList = Lists.newArrayList();

    @Schema(description = "含无的评分")
    public Map<String, String> nothingScore = new HashMap<>(4);
    public List<IbcTree> nothingScoreList = Lists.newArrayList();

    @Schema(description = "评分")
    public Map<String, String> score = new HashMap<>(3);
    public List<IbcTree> scoreList = Lists.newArrayList();

    @Schema(description = "符合程度")
    public Map<String, String> conform = new HashMap<>(4);
    public List<IbcTree> conformList = Lists.newArrayList();

    @Schema(description = "学历水平")
    public Map<String, String> education = new HashMap<>(5);
    public List<IbcTree> educationList = Lists.newArrayList();

    @Schema(description = "金融专业")
    public Map<String, String> financeSpecialty = new HashMap<>(3);
    public List<IbcTree> financeSpecialtyList = Lists.newArrayList();

    @Schema(description = "工作时间")
    public Map<String, String> workTime = new HashMap<>(4);
    public List<IbcTree> workTimeList = Lists.newArrayList();

    @Schema(description = "规范程度")
    public Map<String, String> specification = new HashMap<>(4);
    public List<IbcTree> specificationList = Lists.newArrayList();

    @Schema(description = "现金流量评价")
    public Map<String, String> cashFlowEvaluation = new HashMap<>(4);
    public List<IbcTree> cashFlowEvaluationList = Lists.newArrayList();

    @Schema(description = "领导学历水平")
    public Map<String, String> leaderEducation = new HashMap<>(5);
    public List<IbcTree> leaderEducationList = Lists.newArrayList();

    @Schema(description = "领导信用")
    public Map<String, String> leaderCredit = new HashMap<>(4);
    public List<IbcTree> leaderCreditList = Lists.newArrayList();

    @Schema(description = "先进程度")
    public Map<String, String> advanced = new HashMap<>(4);
    public List<IbcTree> advancedList = Lists.newArrayList();

    @Schema(description = "市场状况")
    public Map<String, String> marketProfile = new HashMap<>(4);
    public List<IbcTree> marketProfileList = Lists.newArrayList();

    @Schema(description = "竞争状况")
    public Map<String, String> competitionSituation = new HashMap<>(3);
    public List<IbcTree> competitionSituationList = Lists.newArrayList();

    @Schema(description = "营销水平")
    public Map<String, String> marketingLevel = new HashMap<>(3);
    public List<IbcTree> marketingLevelList = Lists.newArrayList();

    @Schema(description = "综合评价")
    public Map<String, String> comprehensiveAssessment = new HashMap<>(4);
    public List<IbcTree> comprehensiveAssessmentList = Lists.newArrayList();

    @Schema(description = "风险预警等级")
    public Map<String, String> examineRiskLevel = new HashMap<>(4);
    public List<IbcTree> examineRiskLevelList = Lists.newArrayList();

    private FinanceConfigDic() {

        Arrays.stream(DicEnum.values()).forEach(item -> {
            if (item.getType().getType().equals(DicTypeEnum.PERIOD.getType())) {
                period.put(String.valueOf(item.getValue()), item.getName());
                periodList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.NOTHING_SCORE.getType())) {
                nothingScore.put(String.valueOf(item.getValue()), item.getName());
                nothingScoreList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.SCORE.getType())) {
                score.put(String.valueOf(item.getValue()), item.getName());
                scoreList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.CONFORM.getType())) {
                conform.put(String.valueOf(item.getValue()), item.getName());
                conformList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.EDUCATION.getType())) {
                education.put(String.valueOf(item.getValue()), item.getName());
                educationList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.FINANCE_SPECIALTY.getType())) {
                financeSpecialty.put(String.valueOf(item.getValue()), item.getName());
                financeSpecialtyList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.WORK_TIME.getType())) {
                workTime.put(String.valueOf(item.getValue()), item.getName());
                workTimeList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.SPECIFICATION.getType())) {
                specification.put(String.valueOf(item.getValue()), item.getName());
                specificationList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.CASH_FLOW_EVALUATION.getType())) {
                cashFlowEvaluation.put(String.valueOf(item.getValue()), item.getName());
                cashFlowEvaluationList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.LEADER_EDUCATION.getType())) {
                leaderEducation.put(String.valueOf(item.getValue()), item.getName());
                leaderEducationList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.LEADER_CREDIT.getType())) {
                leaderCredit.put(String.valueOf(item.getValue()), item.getName());
                leaderCreditList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.ADVANCED.getType())) {
                advanced.put(String.valueOf(item.getValue()), item.getName());
                advancedList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.MARKET_PROFILE.getType())) {
                marketProfile.put(String.valueOf(item.getValue()), item.getName());
                marketProfileList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.COMPETITION_SITUATION.getType())) {
                competitionSituation.put(String.valueOf(item.getValue()), item.getName());
                competitionSituationList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.MARKETING_LEVEL.getType())) {
                marketingLevel.put(String.valueOf(item.getValue()), item.getName());
                marketingLevelList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.COMPREHENSIVE_ASSESSMENT.getType())) {
                comprehensiveAssessment.put(String.valueOf(item.getValue()), item.getName());
                comprehensiveAssessmentList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().getType().equals(DicTypeEnum.EXAMINE_RISK_LEVEL.getType())) {
                examineRiskLevel.put(String.valueOf(item.getValue()), item.getName());
                examineRiskLevelList.add(IbcTree.of(item.getValue(), item.getName()));
            }
        });
    }
}
