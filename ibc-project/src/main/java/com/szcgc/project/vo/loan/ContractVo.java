package com.szcgc.project.vo.loan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资金成本登记-放款申请信息
 *
 * @author chenjiaming
 * @date 2022-11-4 09:59:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractVo {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "合同编号")
    private String code;

    @Schema(description = "审批状态")
    private Integer approveStatus;

    @Schema(description = "年份")
    private String year;

    @Schema(description = "编号")
    private String no;

}
