package com.szcgc.project.constant;

import com.szcgc.comm.IbcCase;

/**
 * @Author liaohong
 * @create 2021/4/16 10:47
 */
public enum ProjectBehaviour implements IbcCase {

    preliminary("受理"),

    extend("展期"),

    split("分拆"),

    evaluate("重新上会"),

    loan("放款"),

    fee("收费"),

    stop("暂缓"),

    modifyConclusion("修改评审结论"),

    other("其他"),

    ;

    private String caseName;
    private String caseDesc;

    ProjectBehaviour(String caseName) {
        this(caseName, "");
    }


    ProjectBehaviour(String caseName, String caseDesc) {
        this.caseName = caseName;
        this.caseDesc = caseDesc;
    }

    @Override
    public String getCaseName() {
        return caseName;
    }

    @Override
    public String getCaseDesc() {
        return "";
    }
}
