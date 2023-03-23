package com.szcgc.project.business.plan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.szcgc.comm.IbcResult;
import com.szcgc.project.constant.BusinessTypeCateEnum;
import com.szcgc.project.constant.RepayTypeEnum;

/**
 * 还款计划生成
 *
 * @author liaohong
 */
public class RepayTool {

    public static final int REPAY_DAY = 20;

    public RepayParam param;
    public List<RepayParamItem> editItems;

    public String rst;
    public List<RepayItem> items;

    public boolean isEditable() {
        return RepayTypeEnum.Monthly.equals(param.repayType) || RepayTypeEnum.Quarterly.equals(param.repayType) || RepayTypeEnum.MonthOnce.equals(param.repayType);
    }

    /**
     * @param param     基本参数
     * @param editItems 手动修改后的参数(可选)
     * @return
     */
    public IbcResult<String> calculate(RepayParam param, List<RepayParamItem> editItems) {
        this.param = param;
        this.editItems = editItems;
        if (this.param.amount <= 0) {
            return IbcResult.error("放款金额不能为0");
        }
        if (!this.param.endDate.isAfter(this.param.beginDate)) {
            return IbcResult.error("项目截止日数据错误");
        }
        if (this.param.interest <= 0) {
            return IbcResult.error("利息不能为0");
        }
        this.items = new ArrayList<>();
        if (this.param.businessType.getCate() == BusinessTypeCateEnum.XEDK) {
            doCalculateXd();
        } else {
            doCalculate();
        }
        if (rst != null && rst.length() > 0) {
            return IbcResult.error(rst);
        }
        items.stream().forEach(item -> {
            item.projectId = param.projectId;
            item.customerId = param.customerId;
            item.loanId = param.loanId;
        });
        return IbcResult.OK();
    }

    public void doCalculate() {
        switch (param.repayType) {
            case HalfYear:
                doCalculateHalfYear();
                break;
            case Quarterly:
                doCalculateQuarterly();
                break;
            case Monthly:
                doCalculateMonthly();
                break;
            case Weekly:
                doCalculateWeekly();
                break;
            case Daily:
                doCalculateDaily();
                break;
            case Once:
                doCalculateOnce();
                break;
            case MonthOnce:
                doCalculateMonthOnce();
                break;
            default:
                break;
        }
    }

    public void doCalculateXd() {
        switch (param.repayType) {
            case Quarterly:
                doCalculateQuarterly();
                break;
            case Monthly:
                doCalculateMonthlyXd();
                break;
            case MonthOnce:
                doCalculateMonthOnceXd();
                break;
            default:
                break;
        }
    }

    public void doCalculateOnce() {
        RepayItem item = new RepayItem();
        item.repayDate = param.endDate;
        item.days = (int) ChronoUnit.DAYS.between(param.beginDate, param.endDate);
        item.amount = param.amount;
        item.principalAmt = item.amount;
        item.interestAmt = Math.round(item.amount * item.days * param.interest / 360);
        items.add(item);
    }

    public void doCalculateMonthOnce() {
        LocalDate repayDate = LocalDate.of(param.beginDate.getYear(), param.beginDate.getMonthValue(), REPAY_DAY);
        if (!repayDate.isAfter(param.beginDate)) {
            repayDate = repayDate.plusMonths(1);
        }
        LocalDate payedAt = param.beginDate;
        while (repayDate.isBefore(param.endDate)) {
            RepayItem item = new RepayItem();
            items.add(item);
            item.repayDate = repayDate;
            item.days = (int) ChronoUnit.DAYS.between(payedAt, item.repayDate);
            item.amount = param.amount;
            item.principalAmt = 0;
            item.interestAmt = Math.round(item.amount * item.days * param.interest / 360);
            repayDate = repayDate.plusMonths(1);
            payedAt = item.repayDate;
        }

        RepayItem item = new RepayItem();
        item.repayDate = param.endDate;
        item.days = (int) ChronoUnit.DAYS.between(payedAt, param.endDate);
        item.amount = param.amount;
        item.principalAmt = item.amount;
        item.interestAmt = Math.round(item.amount * item.days * param.interest / 360);
        items.add(item);
    }

    public void doCalculateMonthOnceXd() {
        LocalDate repayDate = LocalDate.of(param.beginDate.getYear(), param.beginDate.getMonthValue(), REPAY_DAY);
        if (!repayDate.isAfter(param.beginDate) || param.beginDate.getDayOfMonth() > 10) {
            repayDate = repayDate.plusMonths(1);
        }
        LocalDate payedAt = param.beginDate;
        while (repayDate.isBefore(param.endDate)) {
            RepayItem item = new RepayItem();
            items.add(item);
            item.repayDate = repayDate;
            item.days = (int) ChronoUnit.DAYS.between(payedAt, item.repayDate);
            item.amount = param.amount;
            item.principalAmt = 0;
            item.interestAmt = Math.round(item.amount * item.days * param.interest / 360);
            repayDate = repayDate.plusMonths(1);
            payedAt = item.repayDate;
        }

        RepayItem item = new RepayItem();
        item.repayDate = param.endDate;
        item.days = (int) ChronoUnit.DAYS.between(payedAt, param.endDate);
        item.amount = param.amount;
        item.principalAmt = item.amount;
        item.interestAmt = Math.round(item.amount * item.days * param.interest / 360);
        items.add(item);
    }

    public void doCalculateDaily() {
        //TODO
    }

    public void doCalculateWeekly() {
        //TODO
    }

    public void doCalculateMonthly() {
        if (param.monthPay <= 0) {
            rst = "按月还款必须填写月还金额";
            return;
        }
        LocalDate repayDate = LocalDate.of(param.beginDate.getYear(), param.beginDate.getMonthValue(), REPAY_DAY);
        if (!repayDate.isAfter(param.beginDate)) {
            repayDate = repayDate.plusMonths(1);
        }
        LocalDate payedAt = param.beginDate;
        long payedAmt = 0;
        while (repayDate.isBefore(param.endDate)) {
            RepayItem item = new RepayItem();
            items.add(item);
            item.repayDate = repayDate;
            item.principalAmt = (items.size() == 1 && item.days < 30) ? 0 : param.monthPay;
            if (editItems != null && editItems.size() >= items.size()) {
                RepayParamItem updateItem = editItems.get(items.size() - 1);
                if (!item.repayDate.isEqual(updateItem.repayDateAt)) {
                    item.repayDate = updateItem.repayDateAt;
                }
                if (item.principalAmt != updateItem.principalAmt) {
                    item.principalAmt = updateItem.principalAmt;
                }
            }

            item.days = (int) ChronoUnit.DAYS.between(payedAt, item.repayDate);
            item.amount = param.amount - payedAmt;
            item.interestAmt = Math.round(item.amount * item.days * param.interest / 360);
            repayDate = repayDate.plusMonths(1);
            payedAt = item.repayDate;
            payedAmt += item.principalAmt;
            if (payedAmt >= param.amount) {
                break;
            }
        }
        if (payedAmt < param.amount) {
            RepayItem item = new RepayItem();
            item.repayDate = param.endDate; // date.plusMonths(during).minusDays(1);
            item.days = (int) ChronoUnit.DAYS.between(payedAt, item.repayDate);
            item.amount = param.amount - payedAmt;
            item.principalAmt = item.amount;
            item.interestAmt = Math.round(item.amount * item.days * param.interest / 360);
            items.add(item);
        }
    }

    public void doCalculateMonthlyXd() {
        if (param.monthPay <= 0) {
            rst = "按月还款必须填写月还金额";
            return;
        }
        LocalDate repayDate = LocalDate.of(param.beginDate.getYear(), param.beginDate.getMonthValue(), REPAY_DAY);
        if (!repayDate.isAfter(param.beginDate) || param.beginDate.getDayOfMonth() > 10) {
            repayDate = repayDate.plusMonths(1);
        }
        LocalDate payedAt = param.beginDate;
        long payedAmt = 0;
        while (repayDate.isBefore(param.endDate)) {
            RepayItem item = new RepayItem();
            items.add(item);
            item.repayDate = repayDate;
            item.principalAmt = (items.size() == 1 && item.days < 30) ? 0 : param.monthPay;
            if (editItems != null && editItems.size() >= items.size()) {
                RepayParamItem updateItem = editItems.get(items.size() - 1);
                if (!item.repayDate.isEqual(updateItem.repayDateAt)) {
                    item.repayDate = updateItem.repayDateAt;
                }
                if (item.principalAmt != updateItem.principalAmt) {
                    item.principalAmt = updateItem.principalAmt;
                }
            }

            item.days = (int) ChronoUnit.DAYS.between(payedAt, item.repayDate);
            item.amount = param.amount - payedAmt;
            item.interestAmt = Math.round(item.amount * item.days * param.interest / 360);
            repayDate = repayDate.plusMonths(1);
            payedAt = item.repayDate;
            payedAmt += item.principalAmt;
            if (payedAmt >= param.amount) {
                break;
            }
        }
        if (payedAmt < param.amount) {
            RepayItem item = new RepayItem();
            item.repayDate = param.endDate; // date.plusMonths(during).minusDays(1);
            item.days = (int) ChronoUnit.DAYS.between(payedAt, item.repayDate);
            item.amount = param.amount - payedAmt;
            item.principalAmt = item.amount;
            item.interestAmt = Math.round(item.amount * item.days * param.interest / 360);
            items.add(item);
        }
    }

    public void doCalculateQuarterly() {
        if (param.monthPay <= 0) {
            rst = "按季还款必须填写季还金额";
            return;
        }
        LocalDate repayDate = param.beginDate.plusMonths(3).withDayOfMonth(REPAY_DAY);
        LocalDate payedAt = param.beginDate;
        long payedAmt = 0;
        while (repayDate.isBefore(param.endDate)) {
            RepayItem item = new RepayItem();
            items.add(item);
            item.repayDate = repayDate;
            item.principalAmt = param.monthPay;
            if (editItems != null && editItems.size() >= items.size()) {
                RepayParamItem updateItem = editItems.get(items.size() - 1);
                if (!item.repayDate.isEqual(updateItem.repayDateAt)) {
                    item.repayDate = updateItem.repayDateAt;
                }
                if (item.principalAmt != updateItem.principalAmt) {
                    item.principalAmt = updateItem.principalAmt;
                }
            }

            item.days = (int) ChronoUnit.DAYS.between(payedAt, item.repayDate);
            item.amount = param.amount - payedAmt;
            item.interestAmt = Math.round(item.amount * item.days * param.interest / 360);
            repayDate = repayDate.plusMonths(3);
            payedAt = item.repayDate;
            payedAmt += item.principalAmt;
            if (payedAmt >= param.amount) {
                break;
            }
        }
        if (payedAmt < param.amount) {
            RepayItem item = new RepayItem();
            item.repayDate = param.endDate; // date.plusMonths(during).minusDays(1);
            item.days = (int) ChronoUnit.DAYS.between(payedAt, item.repayDate);
            item.amount = param.amount - payedAmt;
            item.principalAmt = item.amount;
            item.interestAmt = Math.round(item.amount * item.days * param.interest / 360);
            items.add(item);
        }
    }

    public void doCalculateHalfYear() {
        //TODO
    }

}
