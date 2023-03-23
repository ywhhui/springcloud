package com.szcgc.customer.constant;

/**
 * 字典类型枚举
 *
 * @author chenjiaming
 * @date 2022-9-22 09:31:42
 */
public enum DicTypeEnum {
    UNDEFINED("undefined", "未知字典类型"),
    CUST_TYPE("custType", "客户类型"),
    WHETHER("whether", "是否类型"),
    CERTIFICATE_TYPE("certificateType", "证件类型"),
    NATIONALITY("nationality", "国籍"),
    SHAREHOLDER_TYPE("shareholder_type", "股东类型"),
    ;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型值
     */
    private String type;

    public String getTypeName() {
        return typeName;
    }

    public String getType() {
        return type;
    }

    DicTypeEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}
