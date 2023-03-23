package com.szcgc.project.constant;

import com.szcgc.comm.IbcCase;

/**
 * @Author liaohong
 * @create 2020/8/5 9:38
 */
public enum ProjectActEnum implements IbcCase {

    AG_Make("合同制作"),
    AG_Audit("制式合同审核"),
    AG_Part("部门法务审核"),
    AG_Risk("风控专员复核"),
    AG_Lawyer("法务负责人审批"),
    AG_Chief("首席风险官审批"),
    AG_Boss("部门分管领导审批"),

    AT_Leader("部门负责人审批"),
    AT_Risk("风控审批"),
    AT_Boss("条线领导审批"),

    AE_Counter("登记反担保措施"),
    AE_Distribute("分配资产评估师"),
    AE_Scoring("资产评估"),
    AE_ReScoring("复评师评估"),

    EV_Comment("提交B角意见"),
    EV_Suggest("提交调研结论"),
    EV_Department_Audit("部门预审"),
    EV_Input("录入评审信息"),
    EV_Attend("选择会议"),
    EV_Audit_Meeting("上会审批"),
    EV_Meeting("安排评审会议"),
    EV_Record("记录评审结论"),
    EV_Record_Bh("记录评审结论"),
    EV_Review("会前专审"),
    EV_Addendum_Bh("补充评审信息"),

    EX_Enroll("登记保后检查信息"),
    EX_Audit("审核保后检查信息"),

    FE_Confirm("确认收费标准"),
    FE_Charge("费用收取"),

    GI_Apply("申请担保意向书"),
    GI_Issue("签发担保意向书"),
    GI_Enroll("登记银行回复"),

    LA_Apply("呈请放款"),
    LA_Audit("放款审核"),
    LA_Issue("签发放款通知书"),
    LA_Confirm_Bh("确认电子保函信息(出函审核)"),
    LA_BANK_Bh("等待银行审核结果"),

    LC_Bxsx("保险手续确认"),
    LC_Jgsx("监管手续确认"),
    LC_Szdzy("上载抵质押登记证"),
    LC_Qrdzy("抵质押手续确认"),
    LC_Htbl("合同办理完毕确认"),
    LC_Ysdd("预收典当确认"),
    LC_Clwz("材料完整确认"),
    LC_Bzj("保证金确认"),

    LN_Check("放款条件确认"),
    LN_Enroll("登记放款回执"),
    LN_Cjcbdj("资金成本登记"),
    LN_Archive_Agr("归档合同资料"),
    LN_Archive_Pro("归档项目资料"),
    LN_Enroll_Bh("登记出函回执"),
    LN_Confirm_Bh("项目信息确认"),
    LN_Audit_Addition_Bh("审核待补充资料"),

    OG_Enroll("登记还款还息"),
    OG_Enroll_Cert("登记还款证明书"),
    OG_Audit("审核还款证明书"),
    OG_Enroll_Cert_Bh("登记解保"),
    OG_Audit_Bh("审核解保"),
    OG_Archive_Agr_Bh("归档合同资料"),
    OG_Confirm_Bh("确认解保"),

    CT_Archive_Agr("归档合同资料"),
    CT_Archive_Pro("归档项目资料"),


    SN_Enroll("登记签约"),
    SN_Upload("上传签约照片"),
    SN_Bank("选择银行"),
    SN_Confirm_Bh("确认电子保函信息"),
    SN_Enroll_Bh("登记签约"),
    SN_Deal_Agreement_Bh("合同办理完毕确认"),

    MC_Audit("领导审批"),
    MC_Modify("修改结论"),
    MC_Modify_Bh("修改结论"),
    MC_Approval("领导审核"),
    MC_Approval_Bh("领导审核"),

    ST_Audit("部门负责人审批"),
    ST_Back("费用退还"),
    ST_Absolve("解除反担保物"),

    TM_Absolve("解除反担保物"),
    TM_Back("退还保证金"),

    PL_Assign("分配项目经理"),

    QK_Cwjlsp("财务经理审批(请款)"),
    QK_Cwjlsps("财务经理审批(请款)"),//融资金额大于等于25000000
    QK_Bcqkzl("补充请款资料(请款)"),
    QK_Cwfzrsp("财务负责人审批(请款)"),//融资金额小于25000000
    QK_Gszjlsp("公司总经理审批(请款)"),
    QK_Fgldsp("分管领导审批(请款)"),
    QK_Cwfgld("财务分管领导(请款)"),
    QK_Cwzjsp("财务总监审批(请款)"),
    QK_Jtzjlsp("集团总经理审批(请款)"),
    QK_Jtdszsp("集团董事长审批(请款)"),
    QK_Djczjg("登记出账结果"),

    PH_NONE("PH");

    private String caseName;
    private String caseDesc;

    private ProjectActEnum(String caseName) {
        this.caseName = caseName;
    }

    @Override
    public String getCaseName() {
        return caseName;
    }

    @Override
    public String getCaseDesc() {
        return caseDesc;
    }
}
