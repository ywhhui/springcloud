package com.szcgc.project.business.plan;

import java.time.LocalDate;

/**
 * @Author liaohong
 * @create 2022/10/25 15:57
 */
public class RepayParamItem {

    public long principalAmt; // 还款本金
    public LocalDate repayDateAt;// 还款日期
//        public void adaptor() {
//            if (repayDate != null && repayDate.length() == 10) {
//                repayDateAt = LocalDate.parse(repayDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//            }
//        }
}
