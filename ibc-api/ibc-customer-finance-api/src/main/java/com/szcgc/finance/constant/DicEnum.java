package com.szcgc.finance.constant;

import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 字典枚举
 *
 * @author chenjiaming
 * @date 2022-9-22 09:31:42
 */
public enum DicEnum {

    THE_YEAR_BEFORE_LAST("前年", 1, DicTypeEnum.PERIOD),
    LAST_YEAR("去年", 2, DicTypeEnum.PERIOD),
    SAME_PERIOD_LAST_YEAR("去年同期", 3, DicTypeEnum.PERIOD),
    THIS_YEAR("今年", 4, DicTypeEnum.PERIOD),

    NS_GOOD("好", 1, DicTypeEnum.NOTHING_SCORE),
    NS_GENERAL("普通", 2, DicTypeEnum.NOTHING_SCORE),
    NS_BAD("差", 3, DicTypeEnum.NOTHING_SCORE),
    NS_NOTHING("无", 4, DicTypeEnum.NOTHING_SCORE),

    GOOD("好", 1, DicTypeEnum.SCORE),
    GENERAL("普通", 2, DicTypeEnum.SCORE),
    BAD("差", 3, DicTypeEnum.SCORE),

    PERFECT("完全相符", 1, DicTypeEnum.CONFORM),
    BASE("基本相符", 2, DicTypeEnum.CONFORM),
    PART("部分不符", 3, DicTypeEnum.CONFORM),
    SEVERITY("严重不符", 4, DicTypeEnum.CONFORM),

    GRADUATE("研究生", 1, DicTypeEnum.EDUCATION),
    BACHELOR("本科", 2, DicTypeEnum.EDUCATION),
    COLLEGE("大专", 3, DicTypeEnum.EDUCATION),
    SECONDARY("中专", 4, DicTypeEnum.EDUCATION),
    SECONDARY_FOLLOW("中专以下", 5, DicTypeEnum.EDUCATION),

    ACCOUNTING("财会类", 1, DicTypeEnum.FINANCE_SPECIALTY),
    ECONOMY("经济类", 2, DicTypeEnum.FINANCE_SPECIALTY),
    OTHER("其他类", 3, DicTypeEnum.FINANCE_SPECIALTY),

    FIVE("5年及以上", 1, DicTypeEnum.WORK_TIME),
    THREE("3年(含)至5年", 2, DicTypeEnum.WORK_TIME),
    ONE("1年(含)至3年", 3, DicTypeEnum.WORK_TIME),
    ONE_FOLLOW("1年以下", 4, DicTypeEnum.WORK_TIME),

    SPECIFICATION("规范", 1, DicTypeEnum.SPECIFICATION),
    THE_SPECIFICATION("较规范", 2, DicTypeEnum.SPECIFICATION),
    BASE_SPECIFICATION("基本规范", 3, DicTypeEnum.SPECIFICATION),
    NOT_SPECIFICATION("不规范", 4, DicTypeEnum.SPECIFICATION),

    CFE_01("经营性现金流量>0;净现金流量>0", 1, DicTypeEnum.CASH_FLOW_EVALUATION),
    CFE_02("经营性现金流量>0;净现金流量<0", 2, DicTypeEnum.CASH_FLOW_EVALUATION),
    CFE_03("经营性现金流量<0;净现金流量>0", 3, DicTypeEnum.CASH_FLOW_EVALUATION),
    CFE_04("经营性现金流量<0;净现金流量<0", 4, DicTypeEnum.CASH_FLOW_EVALUATION),

    LE_DOCTOR("博士", 1, DicTypeEnum.LEADER_EDUCATION),
    LE_MEM("硕士", 2, DicTypeEnum.LEADER_EDUCATION),
    LE_BACHELOR("本科", 3, DicTypeEnum.LEADER_EDUCATION),
    LE_COLLEGE("大专", 4, DicTypeEnum.LEADER_EDUCATION),
    LE_SECONDARY_FOLLOW("中专(含)以下", 5, DicTypeEnum.LEADER_EDUCATION),

    LC_HIGH("高", 1, DicTypeEnum.LEADER_CREDIT),
    LC_HIGHER("较高", 2, DicTypeEnum.LEADER_CREDIT),
    LC_GENERAL("一般", 3, DicTypeEnum.LEADER_CREDIT),
    LC_BAD("差", 4, DicTypeEnum.LEADER_CREDIT),

    ADVANCED("先进", 1, DicTypeEnum.ADVANCED),
    MORE_ADVANCED("较先进", 2, DicTypeEnum.ADVANCED),
    AD_GENERAL("一般", 3, DicTypeEnum.ADVANCED),
    BACKWARD("落后", 4, DicTypeEnum.ADVANCED),

    MP_GOOD("好", 1, DicTypeEnum.MARKET_PROFILE),
    MP_BETTER("较好", 2, DicTypeEnum.MARKET_PROFILE),
    MP_GENERAL("一般", 3, DicTypeEnum.MARKET_PROFILE),
    MP_BAD("差", 4, DicTypeEnum.MARKET_PROFILE),

    ADVANTAGE("优势", 1, DicTypeEnum.COMPETITION_SITUATION),
    AVERAGE("平均水平", 2, DicTypeEnum.COMPETITION_SITUATION),
    DISADVANTAGE("劣势", 3, DicTypeEnum.COMPETITION_SITUATION),

    ML_HIGH("高", 1, DicTypeEnum.MARKETING_LEVEL),
    ML_GENERAL("一般", 2, DicTypeEnum.MARKETING_LEVEL),
    ML_BAD("较差", 3, DicTypeEnum.MARKETING_LEVEL),

    CA_HIGH("高", 1, DicTypeEnum.COMPREHENSIVE_ASSESSMENT),
    CA_HIGHER("较高", 2, DicTypeEnum.COMPREHENSIVE_ASSESSMENT),
    CA_GENERAL("一般", 3, DicTypeEnum.COMPREHENSIVE_ASSESSMENT),
    CA_BAD("较差", 4, DicTypeEnum.COMPREHENSIVE_ASSESSMENT),

    ZC("正常",1,DicTypeEnum.EXAMINE_RISK_LEVEL),
    GZ("关注",2,DicTypeEnum.EXAMINE_RISK_LEVEL),
    YJYJ("一级预警",3,DicTypeEnum.EXAMINE_RISK_LEVEL),
    EJYJ("二级预警",4,DicTypeEnum.EXAMINE_RISK_LEVEL),
    SJYJ("三级预警",5,DicTypeEnum.EXAMINE_RISK_LEVEL),

    ;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private Integer value;

    /**
     * 类型枚举
     */
    private DicTypeEnum type;

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public DicTypeEnum getType() {
        return type;
    }

    DicEnum(String name, Integer value, DicTypeEnum type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    /**
     * 根据type获取所有name
     *
     * @param type 类型
     * @return 名称集合
     */
    public static List<String> getNamesByType(DicTypeEnum type) {
        List<String> names = Lists.newArrayList();
        for (DicEnum dicEnum : DicEnum.values()) {
            if (dicEnum.type != type) {
                continue;
            }
            names.add(dicEnum.name);
        }
        return names;
    }

    /**
     * 根据type和name获取value
     *
     * @param name 名称
     * @param type 类型
     * @return 值
     */
    public static Object getValueByNameAndType(Object name, DicTypeEnum type) {
        for (DicEnum dicEnum : DicEnum.values()) {
            if (dicEnum.type != type) {
                continue;
            }
            if (dicEnum.name.equals(name.toString())) {
                return dicEnum.value;
            }
        }
        return -1;
    }
}
