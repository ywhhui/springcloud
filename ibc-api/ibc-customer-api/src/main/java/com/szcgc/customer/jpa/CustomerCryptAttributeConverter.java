package com.szcgc.customer.jpa;

import com.szcgc.comm.util.CryptUtils;

import javax.persistence.AttributeConverter;

/**
 * @Author liaohong
 * @create 2021/5/2 15:06
 */
public class CustomerCryptAttributeConverter implements AttributeConverter<String, String> {

    private static final byte[] DFT_KEY = "0cE3@e3Tf3!e*78c".getBytes();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return CryptUtils.encryptAes(attribute, DFT_KEY);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return CryptUtils.decryptAes(dbData, DFT_KEY);
    }

}
