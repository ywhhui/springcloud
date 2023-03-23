package com.szcgc.finance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AnalysisVo {

    @Schema(description = "前年资产负债表数据id")
    private Integer id1;

    @Schema(description = "去年资产负债表数据id")
    private Integer id2;

    @Schema(description = "去年同期资产负债表数据id")
    private Integer id3;

    @Schema(description = "当期资产负债表数据id")
    private Integer id4;

}
