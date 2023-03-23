package com.szcgc.finance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "历史数据导入对象")
public class HistoryImportVo {

    @Schema(description = "项目id", required = true)
    private Integer projectId;

    @Schema(description = "客户id", required = true)
    private Integer customerId;

    @Schema(description = "导入数据id集合", required = true)
    private List<Integer> ids;

}
