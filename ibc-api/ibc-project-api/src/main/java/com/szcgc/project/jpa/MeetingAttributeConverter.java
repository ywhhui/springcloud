package com.szcgc.project.jpa;


import com.szcgc.comm.util.StringUtils;
import com.szcgc.comm.util.SundryUtils;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2020/8/21 15:06
 */
public class MeetingAttributeConverter implements AttributeConverter<List<Integer>, String> {


    @Override
    public String convertToDatabaseColumn(List<Integer> ids) {
        if (ids == null || ids.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = ids.size(); i < j; i++) {
            sb.append(ids.get(i));
            if (i < j - 1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData))
            return Collections.EMPTY_LIST;
        List<Integer> ids = Arrays.stream(dbData.split(","))
                .map(item -> SundryUtils.tryGetInt(item, 0)).filter(item -> item > 0)
                .collect(Collectors.toList());
        return ids;
    }
}
