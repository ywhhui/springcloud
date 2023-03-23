package com.szcgc.cougua.jpa;

import com.szcgc.comm.util.JsonUtils;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.cougua.constant.CounterGuaranteeTypePropEnum;

import javax.persistence.AttributeConverter;
import java.util.EnumMap;

/**
 * @Author liaohong
 * @create 2021/5/2 15:06
 */
public class CutGuaMaterialPropAttributeConverter implements AttributeConverter<EnumMap<CounterGuaranteeTypePropEnum, String>,String> {


    @Override
    public EnumMap<CounterGuaranteeTypePropEnum, String> convertToEntityAttribute(String attribute) {
        if(!StringUtils.isEmpty(attribute)){
            return  mapStringToMap(attribute);
        }
        return null;
    }

    @Override
    public String convertToDatabaseColumn(EnumMap<CounterGuaranteeTypePropEnum, String> dbData) {
        if(null != dbData){
            return JsonUtils.toJSONString(dbData);
        }
        return null;
    }

    /**
     * 将Map字符串转换为Map
     *
     * @param str Map字符串
     * @return Map
     */
    public static EnumMap<CounterGuaranteeTypePropEnum, String> mapStringToMap(String str){
        str = str.substring(1, str.length()-1);
        String[] strs = str.split(",");
        EnumMap<CounterGuaranteeTypePropEnum,String> map = new EnumMap<CounterGuaranteeTypePropEnum, String>(CounterGuaranteeTypePropEnum.class);
        for (String string : strs) {
            String key = string.split(":")[0];
            String value = string.split(":")[1];
            // 去掉头部空格
            String key1 = key.trim();
            key1 =key1 .replace("\"", "");
            String value1 = value.trim();
            value1 = value1 .replace("\"", "");
            map.put(CounterGuaranteeTypePropEnum.valueOf(key1), value1);
        }
        return map;
    }



}
