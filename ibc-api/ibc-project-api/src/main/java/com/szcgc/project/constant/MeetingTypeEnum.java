package com.szcgc.project.constant;

import com.szcgc.comm.util.SundryUtils;

import java.time.LocalDate;
import java.util.Random;

/**
 * @Author: chenxinli
 * @Date: 2020/10/13 15:06
 * @Description:
 */
public enum MeetingTypeEnum {

    one("一组(直接上会)", 0),

    two("二组", 0),

    third("三组", 0),

    tech("科技通", 0),

    spec("一组(专审会)", 0),

    concordance("协调会", 0);

    private String cnName;
    private int strategy;
    private int[] accountIds;

    private int temp;

    MeetingTypeEnum(String cnName, int accountId) {
        this.cnName = cnName;
        this.accountIds = new int[]{accountId};
    }

    public String getCnName() {
        return cnName;
    }

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public int[] getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(int[] accountIds) {
        this.accountIds = accountIds;
    }

    public void setAccountIds(String accountStr) {
//        this.accountIds = Arrays.stream(accountStr.split(","))
//                .map(item -> SundryUtils.tryGetInt(item, 0)).filter(item -> item > 0)
//                .toArray(int[]::new);
        String[] arr = accountStr.split(",");
        accountIds = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            accountIds[i] = SundryUtils.tryGetInt(arr[i], 0);
        }
    }

    public int getAccountId() {
        if (accountIds == null || accountIds.length == 0)
            return 0;
        if (strategy == 0 || accountIds.length == 1)
            return accountIds[0];
        if (strategy == 1) {//随机
            temp = new Random().nextInt(accountIds.length);
            return accountIds[temp];
        } else if (strategy == 2) {//轮循
            temp++;
            if (temp >= accountIds.length) {
                temp = 0;
            }
            return accountIds[temp];
        } else if (strategy == 3) { //单双月
            temp = LocalDate.now().getMonthValue();
            return accountIds[temp % 2];
        }
        return 0;
    }
}
