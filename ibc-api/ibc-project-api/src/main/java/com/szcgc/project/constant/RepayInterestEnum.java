package com.szcgc.project.constant;

import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 还息方案
 */
public enum RepayInterestEnum {

    I01("到期一次性还息", "I01", RepayLoanEnum.C01),
    I03("按月还息", "I03", RepayLoanEnum.C01, RepayLoanEnum.C03, RepayLoanEnum.C05, RepayLoanEnum.C07,
            RepayLoanEnum.C08),
    I04("按季还息", "I04", RepayLoanEnum.C01, RepayLoanEnum.C05, RepayLoanEnum.C07, RepayLoanEnum.C08),
    I05("按半年还息", "I05", RepayLoanEnum.C01, RepayLoanEnum.C07, RepayLoanEnum.C08),
    I07("按年还息", "I07", RepayLoanEnum.C01, RepayLoanEnum.C08),
    I08("等额本金", "I08", RepayLoanEnum.C09),
    I09("等额本息", "I09", RepayLoanEnum.C10),
    I99("其他", "I99", RepayLoanEnum.C01, RepayLoanEnum.C03, RepayLoanEnum.C05, RepayLoanEnum.C07,
            RepayLoanEnum.C08, RepayLoanEnum.C99),
    ;

    private String name;

    private String value;

    private List<RepayLoanEnum> repayLoanEnum;

    RepayInterestEnum(String name, String value, RepayLoanEnum... repayLoanEnum) {
        this.name = name;
        this.value = value;
        List<RepayLoanEnum> list = Lists.newArrayList();
        for (RepayLoanEnum repayLoan : repayLoanEnum) {
            list.add(repayLoan);
        }
        this.repayLoanEnum = list;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public List<RepayLoanEnum> getRepayLoanEnum() {
        return repayLoanEnum;
    }

    /**
     * 根据还本方案获取还息方案
     *
     * @param repayLoanEnum 还本方案
     * @return 还息方案集合
     */
    public static List<RepayInterestEnum> getListByRepayLoan(RepayLoanEnum repayLoanEnum) {
        List<RepayInterestEnum> list = Lists.newArrayList();
        if (repayLoanEnum == null) {
            return list;
        }
        for (RepayInterestEnum repayInterestEnum : values()) {
            if (repayInterestEnum.repayLoanEnum.contains(repayLoanEnum)) {
                list.add(repayInterestEnum);
            }
        }

        return list;
    }
}
