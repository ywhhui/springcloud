package com.szcgc.project.constant;

/**
 * @Author liaohong
 * @create 2020/9/24 15:13
 */
public enum SupervisorRoleEnum {

    duty_a("责任A角"),
    duty_b("责任B角"),
    duty_c("责任C角"),
    duty_d("责任D角"),
    duty_e("责任E角"),
    deal_a("处理A角"),
    deal_b("处理B角"),
    deal_c("处理C角"),
    deal_d("处理D角"),
    deal_e("处理E角"),
    lawyer("法务经理"),
    ;

    private String cnName;

    SupervisorRoleEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }

    public boolean isLawyer() {
        return this == lawyer;
    }
}
