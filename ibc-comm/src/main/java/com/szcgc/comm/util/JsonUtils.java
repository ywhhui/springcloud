package com.szcgc.comm.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author liaohong
 * @create 2022/9/21 15:11
 */
public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        // 添加此配置解决字符串有的字段实体类没有该字段而报错的问题
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJSONString(Object o) {
        if (o == null)
            return null;
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseObject(String s, Class<T> tClass) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        try {
            return mapper.readValue(s, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
