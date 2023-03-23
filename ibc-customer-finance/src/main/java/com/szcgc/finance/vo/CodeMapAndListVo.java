package com.szcgc.finance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "财务原始表字段说明")
public class CodeMapAndListVo {

    @Schema(description = "字段顺序")
    List<String> codeList;

    @Schema(description = "字段说明")
    Map<String, String> codeMap;
}
