package com.szcgc.finance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenjiaming
 * @date 2022-9-29 17:09:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigSubVo {

    @Schema(description = "值")
    private String value;

    @Schema(description = "名称")
    private String label;
}
