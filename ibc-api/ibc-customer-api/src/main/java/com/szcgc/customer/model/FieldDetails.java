package com.szcgc.customer.model;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.customer.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

/**
 * 对象详情
 *
 * @author chenjiaming
 * @date 2022-9-21 14:09:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldDetails {

    private Field field;

    private Excel excel;

    private Schema schema;

    public String getExcelName() {
        if (schema != null && StrUtil.isNotBlank(schema.description())) {
            return schema.description();
        }

        return ObjectUtil.isNotNull(excel) ? excel.name() : "";
    }

}
