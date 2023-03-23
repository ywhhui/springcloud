package com.szcgc.customer.annotation;


import com.szcgc.customer.constant.DicTypeEnum;

import java.lang.annotation.*;

/**
 * 模板下载excel注解
 *
 * @author chenjiaming
 * @date 2022-9-21 10:50:16
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {

    /**
     * 模板下载列名,如果同个属性上有swagger的schema注解,该值可以不填,取schema注解的description值
     *
     * @return
     */
    String name() default "";

    /**
     * 模板下载示例数据
     *
     * @return
     */
    String sample() default "";

    /**
     * 列顺序
     *
     * @return
     */
    int sort() default 0;

    /**
     * 是否必填,如果同个属性上有swagger的schema注解,优先取schema注解的required值
     *
     * @return
     */
    boolean required() default false;

    /**
     * 下拉类型,会从字典枚举中获取对应值去生成模板
     *
     * @return
     */
    DicTypeEnum comboxType() default DicTypeEnum.UNDEFINED;

    /**
     * 公式,支持动态占位符 ${colIdx} ${rowIdx} ${colIdx + 10}占位符
     *
     * @return
     */
    String formula() default "";

    /**
     * 是否允许修改
     *
     * @return
     */
    boolean edit() default true;
}
