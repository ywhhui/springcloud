package com.szcgc.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 企业简略信息
 *
 * @autor chenjiaming
 * @date 2022-9-29 11:00:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "企业简略信息")
@EqualsAndHashCode(callSuper = false)
public class CompanyVo {

    @Schema(description = "客户id,有客户id说明是在库客户")
    private Integer custId;

    @Schema(description = "企业名称")
    private String name;

    @Schema(description = "管户人id")
    private Integer custodian;

}
