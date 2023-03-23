package com.szcgc.customer.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szcgc.customer.annotation.Excel;
import com.szcgc.customer.model.FieldDetails;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 反射缓存工具类,减少反射调用次数提升性能
 *
 * @author chenjiaming
 * @date 2022-9-21 10:55:05
 */
public class ReflectCacheUtil {

    /**
     * 以class为key,反射字段详情对象为value
     */
    protected static Map<Class, List<FieldDetails>> fieldDetailsMap = new HashMap<>();

    /**
     * 以class为key,对象swagger注解description值为value
     */
    protected static Map<Class, String> objNameMap = new HashMap<>();

    /**
     * 根据class获取对象swagger注解description值
     *
     * @param cls class
     * @return 对象swagger注解description值
     */
    public static String getObjName(Class cls) {
        if (!objNameMap.containsKey(cls)) {
            Schema schema = (Schema) cls.getAnnotation(Schema.class);
            objNameMap.put(cls, ObjectUtil.isEmpty(schema) ? "" : schema.description());
        }
        return objNameMap.get(cls);
    }

    /**
     * 根据class获取字段对象excel注解对象map
     *
     * @param cls 类
     * @return 字段对象excel注解对象map
     */
    public static Map<Field, Excel> getFieldExcelMap(Class cls) {
        return getFieldList(cls).stream().collect(Collectors.toMap(FieldDetails::getField, FieldDetails::getExcel));
    }

    /**
     * 根据class获取反射字段对象列表
     *
     * @param cls 类
     * @return 反射字段对象列表
     */
    public static List<FieldDetails> getFieldList(Class cls) {
        if (!fieldDetailsMap.containsKey(cls)) {
            List<FieldDetails> list = getList(cls);
            fieldDetailsMap.put(cls, list);
            Class tempCls = cls;
            while (tempCls != null) {
                tempCls = tempCls.getSuperclass();
                if (tempCls.equals(Object.class)) {
                    break;
                }
                fieldDetailsMap.get(cls).addAll(getList(tempCls));
            }
        }
        return fieldDetailsMap.get(cls);
    }

    private static List<FieldDetails> getList(Class cls) {
        return CollectionUtil.toList(cls.getDeclaredFields()).stream().map(
                obj ->
                        FieldDetails.builder()
                                .field(obj)
                                .excel(obj.getAnnotation(Excel.class))
                                .schema(obj.getAnnotation(Schema.class))
                                .build()
        ).collect(Collectors.toList());
    }

    /**
     * 根据class获取字段对象swagger注解对象map
     *
     * @param cls 类
     * @return 字段对象swagger注解对象map
     */
    public static Map<Field, Schema> getFieldSchemaMap(Class cls) {
        return getFieldList(cls).stream().filter(obj -> ObjectUtil.isNotNull(obj.getSchema())).collect(Collectors.toMap(FieldDetails::getField, FieldDetails::getSchema));
    }
}
