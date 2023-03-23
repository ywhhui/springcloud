package com.szcgc.finance.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.constant.Const;
import com.szcgc.finance.model.FieldDetails;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

/**
 * 计算工具类
 *
 * @author chenjiaming
 * @date 2022-9-27 15:20:36
 */
public class CalcUtil {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public static final BigDecimal TEN_THOUSAND = new BigDecimal(10000);

    public static final BigDecimal TWO = new BigDecimal(2);

    /**
     * 减法得到的结果转百分比
     *
     * @param num 做减法数字的数组
     * @return 百分比字符串
     */
    public static String subToPer(BigDecimal... num) {
        return per(NumberUtil.sub(num));
    }

    /**
     * 减法
     *
     * @param num 做减法数字的数组
     * @return 保留两位数的结果字符串
     */
    public static String sub(BigDecimal... num) {
        return round(NumberUtil.sub(num));
    }

    /**
     * 转换单位为万
     *
     * @param num 数据
     * @return 四舍五入后的字符串
     */
    public static String tranUnit(BigDecimal num) {
        return round(NumberUtil.div(num, TEN_THOUSAND));
    }


    /**
     * 转换单位为万
     *
     * @param list 需要转换的集合
     */
    public static void tranUnit(List list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        list.forEach(obj -> {
            List<FieldDetails> fieldDetailsList = ReflectCacheUtil.getExcelFieldList(obj.getClass());
            for (FieldDetails fieldDetails : fieldDetailsList) {
                Field field = fieldDetails.getField();
                String value = (String) ReflectUtil.getFieldValue(obj, field);
                if (StrUtil.isNotBlank(value) && !Const.Symbol.SLASH.equals(value) && !value.contains(Const.Symbol.PERCENT) && fieldDetails.getExcel().trunUnit()) {
                    value = tranUnit(new BigDecimal(value));
                    ReflectUtil.setFieldValue(obj, field, value);
                }
            }
        });
    }

    /**
     * 保留两位小数的四舍五入
     *
     * @param num 数据
     * @return 四舍五入后的字符串
     */
    public static String round(BigDecimal num) {
        return round(num, 2);
    }

    /**
     * 四舍五入
     *
     * @param num    数据
     * @param places 小数点位数
     * @return 四舍五入后的字符串
     */
    public static String round(BigDecimal num, Integer places) {
        if (ObjectUtil.isNull(num)) {
            return Const.Number.ZERO;
        }

        BigDecimal setScale = num.setScale(places, BigDecimal.ROUND_HALF_UP);
        return String.format("%.nf".replace("n", String.valueOf(places)), setScale.doubleValue());
    }

    /**
     * 数值转百分比
     *
     * @param num 小数转百分比
     * @return 百分比字符串
     */
    public static String per(BigDecimal num) {
        return mul(num, ONE_HUNDRED).concat(Const.Symbol.PERCENT);
    }

    /**
     * 乘法
     *
     * @param num 需要做乘法的数组
     * @return 四舍五入后的字符串
     */
    public static String mul(BigDecimal... num) {
        return round(NumberUtil.mul(num));
    }

    /**
     * 加法
     *
     * @param num 需要做加法的数组
     * @return 四舍五入后的字符串
     */
    public static String add(BigDecimal... num) {
        return round(NumberUtil.add(num));
    }

    /**
     * 除法
     *
     * @param num1 被除数
     * @param num2 除数
     * @return 四舍五入后的字符串
     */
    public static String div(BigDecimal num1, BigDecimal num2) {
        if (ObjectUtil.isNull(num1) || ObjectUtil.isNull(num2) || isZero(num1) || isZero(num2)) {
            return Const.Symbol.SLASH;
        }

        return round(NumberUtil.div(num1, num2));
    }

    /**
     * 除法
     *
     * @param num1 被除数
     * @param num2 除数
     * @return 未四舍五入的数据
     */
    public static BigDecimal orDiv(BigDecimal num1, BigDecimal num2) {
        if (ObjectUtil.isNull(num1) || ObjectUtil.isNull(num2) || isZero(num1) || isZero(num2)) {
            return BigDecimal.ZERO;
        }

        return NumberUtil.div(num1, num2);
    }

    /**
     * 判断数字是否为0
     *
     * @param num 数字
     * @return true or false
     */
    public static boolean isZero(BigDecimal num) {
        return ObjectUtil.isNotNull(num) && BigDecimal.ZERO.compareTo(num) == 0;
    }

    /**
     * 除法所得小数转百分数
     *
     * @param num1 被除数
     * @param num2 除数
     * @return 百分数字符串
     */
    public static String divToPer(BigDecimal num1, BigDecimal num2) {
        if (ObjectUtil.isNull(num1) || ObjectUtil.isNull(num2) || isZero(num1) || isZero(num2)) {
            return Const.Symbol.SLASH;
        }
        return per(NumberUtil.div(num1, num2));
    }

    /**
     * 取绝对值
     *
     * @param num 数字
     * @return 数字绝对值
     */
    public static BigDecimal abs(BigDecimal num) {
        if (ObjectUtil.isNull(num) || isZero(num)) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(Math.abs(num.doubleValue()));
    }

    /**
     * 取绝对值
     *
     * @param num 数字字符串
     * @return 数字绝对值
     */
    public static BigDecimal abs(String num) {
        return BigDecimal.valueOf(Math.abs(new BigDecimal(num).doubleValue()));
    }

}
