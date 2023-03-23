package com.szcgc.file.jpa;

import javax.persistence.AttributeConverter;

/**
 * @Author liaohong
 * @create 2022/10/12 10:20
 */
public class PathAttributeConvert implements AttributeConverter<String, String> {

    public static String prefix;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null)
            return null;
        if (prefix == null)
            return attribute;
        if (attribute.startsWith(prefix))
            return attribute.substring(prefix.length());
        return attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        if (prefix == null)
            return dbData;
        return prefix + dbData;
    }
}
