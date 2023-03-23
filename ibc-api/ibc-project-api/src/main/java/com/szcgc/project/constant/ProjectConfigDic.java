package com.szcgc.project.constant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.szcgc.comm.model.IbcTree;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2022/9/21 14:13
 */
public class ProjectConfigDic {

    public static final ProjectConfigDic INSTANCE = new ProjectConfigDic();

    @Schema(description = "项目-状态")
    public Map<String, String> projectStatus = new HashMap<>(ProjectStatusEnum.values().length);

    @Schema(description = "项目-业务品种")
    public List<IbcTree> projectBusinessType;

    @Schema(description = "项目-期限单位")
    public Map<String, String> projectDuringUnit = new HashMap<>(2);
    public List<IbcTree> projectDuringUnitList;

    @Schema(description = "项目-企业规模类型")
    public Map<String, String> enterprisesType = new HashMap<>(EnterprisesTypeEnum.values().length);

    @Schema(description = "项目方案-还款方式")
    public Map<String, String> projectRepayType = new HashMap<>(RepayTypeEnum.values().length);
    @Schema(description = "项目方案-付息方式")
    public Map<String, String> projectInterestType = new HashMap<>(InterestTypeEnum.values().length);

    @Schema(description = "项目方案-放款方式")
    public Map<String, String> projectLendType = new HashMap<>(LendTypeEnum.values().length);

    @Schema(description = "费用收取-费用类型")
    public Map<String, String> feeType = new HashMap<>(FeeTypeEnum.values().length);
    @Schema(description = "费用收取-收款方式")
    public Map<String, String> feeCollectionType = new HashMap<>(FeeCollectionTypeEnum.values().length);

    @Schema(description = "项目-审批-部门预审")
    public Map<String, String> audit4Department = new HashMap<>(2);
    public List<IbcTree> audit4DepartmentList;

    @Schema(description = "项目-审批-会议类型")
    public Map<String, String> meetingType = new LinkedHashMap<>();  //对顺序有要求
    public List<IbcTree> meetingTypeList;

    @Schema(description = "项目-审批-上会选项")
    @JsonIgnore
    public Map<String, String> meetingOpinion = new LinkedHashMap<>();  //对顺序有要求
    @JsonIgnore
    public List<IbcTree> meetingOpinionList;
    @JsonIgnore
    public List<IbcTree> meetingOpinionList_no_spec;
    @JsonIgnore
    public List<IbcTree> meetingOpinionList_no_one;

    @Schema(description = "项目-审批-签批")
    public Map<String, String> annotateOpinion = new LinkedHashMap<>();  //对顺序有要求
    public List<IbcTree> annotateOpinionList;
    @JsonIgnore
    public List<IbcTree> annotateOpinionListFull;

    @Schema(description = "流程节点名称数据字典")
    public Map<String, String> projectActEnum = new HashMap<>(ProjectActEnum.values().length);
    public List<IbcTree> projectActEnumList;

    @Schema(description = "还本方案")
    public Map<String, String> repayLoan = new HashMap<>(8);
    public List<IbcTree> repayLoanList = new ArrayList<>(8);

    @Schema(description = "请款流程审批类型")
    public Map<String, String> auditRst = new HashMap<>(2);
    public List<IbcTree> auditRstList;

    private ProjectConfigDic() {

        Arrays.stream(ProjectStatusEnum.values()).forEach(status -> projectStatus.put(status.name(), status.getCnName()));
        projectDuringUnit.put(String.valueOf(ProjectConstants.DURING_UNIT_DAY), "日");
        projectDuringUnit.put(String.valueOf(ProjectConstants.DURING_UNIT_MONTH), "月");
        projectDuringUnit.put(String.valueOf(ProjectConstants.DURING_UNIT_YEAR), "年");
        projectDuringUnitList = IbcTree.of(projectDuringUnit);

        projectBusinessType = new ArrayList<>(50);
        Arrays.stream(BusinessTypeCateEnum.values()).forEach(type -> {
            List<IbcTree> children = type.getChildren().stream().map(cate -> IbcTree.of(cate.name(), cate.getCnName())).collect(Collectors.toList());
            projectBusinessType.add(IbcTree.of(type.name(), type.getCnName(), children));
        });

        Arrays.stream(EnterprisesTypeEnum.values()).forEach(type -> enterprisesType.put(type.name(), type.getCnName()));

        Arrays.stream(RepayTypeEnum.values()).forEach(type -> projectRepayType.put(type.name(), type.getCnName()));
        Arrays.stream(InterestTypeEnum.values()).forEach(type -> projectInterestType.put(type.name(), type.getCnName()));
        Arrays.stream(LendTypeEnum.values()).forEach(type -> projectLendType.put(type.name(), type.getCnName()));
        //Arrays.stream(MeetingTypeEnum.values()).forEach(type -> meetingType.put(type.name(), type.getCnName()));
        //Arrays.stream(MeetingStatusEnum.values()).forEach(type -> meetingStatus.put(type.name(), type.getCnName()));
        Arrays.stream(FeeTypeEnum.values()).forEach(type -> feeType.put(type.name(), type.getCnName()));
        Arrays.stream(FeeCollectionTypeEnum.values()).forEach(type -> feeCollectionType.put(type.name(), type.getCnName()));

//        projectProposalEvaluateFlow.put(ProjectConstants.PROPOSAL_FLOW_MEETING, "上会");
//        projectProposalEvaluateFlow.put(ProjectConstants.PROPOSAL_FLOW_ANNOTATE, "签批");
//        projectProposalEvaluateFlow.put(ProjectConstants.PROPOSAL_FLOW_REVIEW, "专审");
//        projectProposalEvaluateFlow.put(ProjectConstants.PROPOSAL_FLOW_STOP, "暂缓");
//
//        projectEvaluateEvaluateFlow.put(ProjectConstants.EVALUATE_FLOW_PASS, "通过");
//        projectEvaluateEvaluateFlow.put(ProjectConstants.EVALUATE_FLOW_HOLD, "补充调研");
//        projectEvaluateEvaluateFlow.put(ProjectConstants.EVALUATE_FLOW_STOP, "暂缓");
//
//        annotateFlow.put(ProjectConstants.ANNOTATE_FLOW_MEETING, "上会");
//        annotateFlow.put(ProjectConstants.ANNOTATE_FLOW_STOP, "暂缓");
//        annotateFlow.put(ProjectConstants.ANNOTATE_FLOW_PASS, "通过");
//        annotateFlow.put(ProjectConstants.ANNOTATE_FLOW_RETRY, "补充调研");
//
//        for (int i = 1; i < ProjectConstants.operateObject.length; i++) {
//            projectOperateObject.put(i, ProjectConstants.operateObject[i]);
//        }

        audit4Department.put(String.valueOf(ProjectConstants.AUDIT_PASS), "通过");
        audit4Department.put(String.valueOf(ProjectConstants.AUDIT_HOLD), "评审信息录入");
        audit4DepartmentList = IbcTree.of(audit4Department);

        meetingType.put(ProjectConstants.MEET_SPEC, "一组(专审)");
        meetingType.put(ProjectConstants.MEET_ONE, "一组(直接上会)");
        meetingType.put(ProjectConstants.MEET_TWO, "二组");
        meetingType.put(ProjectConstants.MEET_TECH, "科技通");
        meetingTypeList = IbcTree.of(meetingType);


        meetingOpinion.put(ProjectConstants.MEET_SPEC, "一组(专审)");
        meetingOpinion.put(ProjectConstants.MEET_ONE, "一组(直接上会)");
        meetingOpinion.put(ProjectConstants.MEET_TWO, "二组");
        meetingOpinion.put(ProjectConstants.MEET_TECH, "科技通");
        meetingOpinion.put(ProjectConstants.MEET_ANNOTATE, "签批");
        meetingOpinion.put(ProjectConstants.MEET_STOP, "暂缓");
        meetingOpinionList = IbcTree.of(meetingOpinion);
        meetingOpinionList_no_spec = new ArrayList<>(meetingOpinionList);
        meetingOpinionList_no_one = new ArrayList<>(meetingOpinionList);
        meetingOpinionList_no_spec.remove(1);
        meetingOpinionList_no_one.remove(0);

        annotateOpinion.put(ProjectConstants.ANNOTATE_FLOW_PASS, "通过");
        annotateOpinion.put(ProjectConstants.ANNOTATE_FLOW_MEETING, "上会");
        annotateOpinion.put(ProjectConstants.ANNOTATE_FLOW_STOP, "暂缓");
        annotateOpinion.put(ProjectConstants.ANNOTATE_FLOW_RETRY, "补充调研");
        annotateOpinion.put(ProjectConstants.ANNOTATE_FLOW_GROUP, "转集团审批");
        annotateOpinionList = IbcTree.of(annotateOpinion);
        annotateOpinionListFull = new ArrayList<>(annotateOpinionList);
        annotateOpinionList.remove(annotateOpinionList.size() - 1);

        Arrays.stream(ProjectActEnum.values()).forEach(actEnum -> projectActEnum.put(actEnum.name(), actEnum.getCaseName()));
        projectActEnumList = IbcTree.of(projectActEnum);

        Arrays.stream(RepayLoanEnum.values()).forEach(obj -> {
            repayLoan.put(obj.getValue(), obj.getName());
            IbcTree tree = IbcTree.of(obj.getValue(), obj.getName());
            tree.setChildren(RepayInterestEnum.getListByRepayLoan(obj).stream().map(sub -> IbcTree.of(sub.getValue(), sub.getName())).collect(Collectors.toList()));
            repayLoanList.add(tree);
        });

        auditRst.put(ProjectConstants.QK_PASS, "通过");
        auditRst.put(ProjectConstants.QK_RETURN, "退回");
        auditRstList = IbcTree.of(auditRst);
    }
}
