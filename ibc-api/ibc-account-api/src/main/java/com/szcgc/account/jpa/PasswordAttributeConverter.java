package com.szcgc.account.jpa;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.UnixCrypt;

import javax.persistence.AttributeConverter;

/**
 * @Author liaohong
 * @create 2020/8/21 15:06
 */
public class PasswordAttributeConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return UnixCrypt.crypt(attribute, DigestUtils.sha256Hex(attribute));
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return "******";
    }
}
