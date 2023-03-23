package com.szcgc.finance.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 参数验证工具类
 *
 * @author chenjiaming
 * @date 2022-9-21 10:07:06
 */
public class ParamVerifyUtil extends ReflectCacheUtil {

    /**
     * 传参校验
     *
     * @param t   参数对象
     * @param <T> 参数类型
     * @return 错误提示
     */
    public static <T> String verify(T t) {
        Map<Field, Schema> map = getFieldSchemaMap(t.getClass());

        String errorTip = ",";
        for (Map.Entry<Field, Schema> obj : map.entrySet()) {
            Schema schema = obj.getValue();
            // 没有swagger注解或者swagger注解没标注必填,则跳出循环
            if (ObjectUtil.isEmpty(schema) || !schema.required()) {
                continue;
            }
            // 标注了必填值为空的情况
            Object value = ReflectUtil.getFieldValue(t, obj.getKey());
            if (ObjectUtil.isEmpty(value)) {
                errorTip = errorTip.concat(schema.description()).concat("不能为空,");
            }
        }
        return errorTip.substring(1);
    }

    /**
     * 传参校验
     *
     * @param t   参数对象数组
     * @param <T> 参数类型
     * @return 错误提示
     */
    public static <T> String verify(List<T> t) {
        String errorTip = "";

        for (T obj : t) {
            errorTip = errorTip.concat(verify(obj));
        }

        if(StrUtil.isBlank(errorTip)){
            return errorTip;
        }

        return errorTip.substring(1);
    }


}
