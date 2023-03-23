package com.szcgc.bpmn.constant;

/**
 * @Author liaohong
 * @create 2022/9/28 11:31
 */
public enum FlowEnum {

    preliminary("受理"),
    evaluate("评审"),
    signing("签约"),
    loan("放款"),
    ongoing("在保"),
    terminate("解保"),

    credit("额度"),

    assetsEvaluate("资产评估"),
    annotate("签批"),
    fee("收费"),
    agreement("制作合同"),
    guaranteeIntent("签发担保意向书"),
    loanApprove("放款审批"),
    loanCondition("放款条件确认"),
    examine("保后检查"),

    modifyConclusion("修改评审结论"),
    stop("项目暂缓"),

    ;


    private String cnName;

    FlowEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }
}
