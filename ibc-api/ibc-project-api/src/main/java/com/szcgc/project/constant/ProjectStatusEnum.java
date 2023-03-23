package com.szcgc.project.constant;

/**
 * @Author liaohong
 * @create 2020/9/4 10:51
 */
public enum ProjectStatusEnum {

    Init("初始化"),

    Preliminary("受理"),  //1

    Evaluate("评审"),     //2

    Signing("签约"),      //3

    Loan("放款"),         //4

    Ongoing("在保"),      //5

    Terminate("解保"),      //6

    Finished("完结"),     //7

    Stop("暂缓"),         //8
    ;

    private String cnName;

    ProjectStatusEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }

    public boolean isInit() {
        return this == Init;
    }

    public boolean isPreliminary() {
        return this == Preliminary;
    }

    public boolean isEvaluate() {
        return this == Evaluate;
    }

    public boolean isSigning() {
        return this == Signing;
    }

    public boolean isLoan() {
        return this == Loan;
    }

    public boolean isOngoing() {
        return this == Ongoing;
    }

    public boolean isFinished() {
        return this == Finished;
    }

    public boolean isStop() {
        return this == Stop;
    }

    /**
     * 是否有责任人
     *
     * @return
     */
    public boolean isWithSupervisor() {
        return isEvaluate() || isSigning() || isLoan() || isOngoing() || isFinished();
    }

    /**
     * 是否有签约信息
     *
     * @return
     */
    public boolean isWithSigning() {
        return isSigning() || isLoan() || isOngoing() || isFinished();
    }

    /**
     * 是否有承保信息
     *
     * @return
     */
    public boolean isWithLoan() {
        return isLoan() || isOngoing() || isFinished();
    }

    /**
     * 是否有在保信息
     *
     * @return
     */
    public boolean isWithOngoing() {
        return isOngoing() || isFinished();
    }
}