package com.szcgc.account.constant;

/**
 * @Author liaohong
 * @create 2020/10/20 15:53
 */
public enum AccountRoleEnum {

    Chairman("董事长"),
    President("总经理", true),
    RiskOfficer("首席风险官"),

    AssetAssigner("资产评估分配人"),
    AssetAssessor("资产评估师"),

    Accountant("会计", true),
    Cashier("出纳", true),

    //评审
    MeetScheduler("会议安排人", true),
    AnnotateMeetRecorder("签批结论记录人", true),
    AnnotateRiskApprover("签批风控审批人", true),

    BhAnnotateRiskApprover("签批风控审批(保函总经理签批)", true),
    BhAnnotateBossApprover("签批条线领导(保函总经理签批)", true),
    BhAnnotateRiskApprover2("签批风控审批(保函风控签批)", true),
    BhAnnotateBossApprover2("签批条线领导(保函风控签批)", true),

    //签约
    GuaranteeIntentProcessor("签发担保意向书处理人"),

    //放款
    LoanAuditor("放款审核人", true),
    LoanIssuer("签发放款通知书处理人", true),
    LoanAgreementArchiver("合同归档专员", true),

    //在保
    RepayCertAuditor("还款证明书审核人", true),
    RepayCertConfirmer("保后检查确认人", true),
    ExamineAuditor("保后检查审核人", true),

    AgreementRiskAuditor("合同审核风控专员"),   //  @Deprecated
    LawyerManager("法务部门负责人"),   //  @Deprecated

    ;

    private String cnName;
    private boolean careBusiness;

    AccountRoleEnum(String cnName) {
        this.cnName = cnName;
    }

    AccountRoleEnum(String cnName, boolean careBusiness) {
        this.cnName = cnName;
        this.careBusiness = careBusiness;
    }

    public String getCnName() {
        return cnName;
    }

    public boolean isCareBusiness() {
        return careBusiness;
    }
}
