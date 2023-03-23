package com.szcgc.finance.vo;

import com.google.common.collect.Lists;
import com.szcgc.finance.constant.DicEnum;
import com.szcgc.finance.constant.DicTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiaming
 * @date 2022-9-29 17:09:05
 */
public class ConfigVo {
    public static final ConfigVo INSTANCE = new ConfigVo();
    @Schema(description = "期次")
    public Map<String, String> period = new HashMap<>(4);
    @Schema(description = "期次")
    public List<ConfigSubVo> periodList = Lists.newArrayList();

    private ConfigVo() {

        Arrays.stream(DicEnum.values()).forEach(item -> {
            if (item.getType().getType().equals(DicTypeEnum.PERIOD.getType())) {
                period.put(String.valueOf(item.getValue()), item.getName());
                periodList.add(ConfigSubVo.builder()
                        .label(item.getName())
                        .value(String.valueOf(item.getValue()))
                        .build());
            }
        });
    }
}
