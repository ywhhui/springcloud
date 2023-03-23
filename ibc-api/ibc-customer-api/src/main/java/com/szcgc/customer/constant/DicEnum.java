package com.szcgc.customer.constant;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 字典枚举
 *
 * @author chenjiaming
 * @date 2022-9-22 09:31:42
 */
public enum DicEnum {

    CUST_TYPE_COMPANY("企业", 2, DicTypeEnum.CUST_TYPE),
    CUST_TYPE_PERSON("个人", 1, DicTypeEnum.CUST_TYPE),

    SHAREHOLDER_TYPE_COMPANY("公司", 1, DicTypeEnum.SHAREHOLDER_TYPE),
    SHAREHOLDER_TYPE_PERSON("个人", 2, DicTypeEnum.SHAREHOLDER_TYPE),
    SHAREHOLDER_TYPE_OTHER("其他", 3, DicTypeEnum.SHAREHOLDER_TYPE),

    YES("是", 1, DicTypeEnum.WHETHER),
    NO("否", 2, DicTypeEnum.WHETHER),
    WH_OTHER("其他", 3, DicTypeEnum.WHETHER),

    CT_ID_CARD("身份证", 1, DicTypeEnum.CERTIFICATE_TYPE),
    CT_PASSPORT("护照", 2, DicTypeEnum.CERTIFICATE_TYPE),
    CT_HKMT_ID_CARD("港澳台居民身份证", 3, DicTypeEnum.CERTIFICATE_TYPE),
    CT_TRAVEL_CERTIFICATE("旅行证", 4, DicTypeEnum.CERTIFICATE_TYPE),
    CT_OTHER("其他", 5, DicTypeEnum.CERTIFICATE_TYPE),

    CHINA("中国", 1, DicTypeEnum.NATIONALITY),
    HONG_KONG("中国香港", 2, DicTypeEnum.NATIONALITY),
    MACAU("中国澳门", 3, DicTypeEnum.NATIONALITY),
    TAI_PEI("中国台北", 4, DicTypeEnum.NATIONALITY),
    JAPAN("日本", 5, DicTypeEnum.NATIONALITY),
    KOREA("韩国", 6, DicTypeEnum.NATIONALITY),
    MALAYSIA("马来西亚", 7, DicTypeEnum.NATIONALITY),
    PHILIPPINES("菲律宾", 8, DicTypeEnum.NATIONALITY),
    SINGAPORE("新加坡", 9, DicTypeEnum.NATIONALITY),
    THAILAND("泰国", 10, DicTypeEnum.NATIONALITY),
    RUSSIA("俄罗斯", 11, DicTypeEnum.NATIONALITY),
    ENGLAND("英国", 12, DicTypeEnum.NATIONALITY),
    AMERICA("美国", 13, DicTypeEnum.NATIONALITY),
    AUSTRALIAN("澳大利亚", 14, DicTypeEnum.NATIONALITY),
    CANADA("加拿大", 15, DicTypeEnum.NATIONALITY),
    FRANCE("法国", 16, DicTypeEnum.NATIONALITY),
    GERMANY("德国", 17, DicTypeEnum.NATIONALITY),
    INDONESIA("印度尼西亚", 18, DicTypeEnum.NATIONALITY),
    ITALY("意大利", 19, DicTypeEnum.NATIONALITY),
    NEW_ZEALAND("新西兰", 20, DicTypeEnum.NATIONALITY),
    NA_OTHER("其他国家和地区", 21, DicTypeEnum.NATIONALITY),
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

    /**
     * 根据type和value获取name
     *
     * @param value 值
     * @param type  类型
     * @return 值
     */
    public static String getNameByValueAndType(String value, DicTypeEnum type) {
        if (StrUtil.isBlank(value)) {
            return "";
        }
        for (DicEnum dicEnum : DicEnum.values()) {
            if (dicEnum.type != type) {
                continue;
            }
            if (dicEnum.value == Integer.parseInt(value)) {
                return dicEnum.name;
            }
        }
        return "";
    }
}
