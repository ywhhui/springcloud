package com.szcgc.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author liaohong
 * @create 2022/10/13 9:15
 * 通用审批选项
 */
@Data
public class CommAuditInVo extends BaseInVo {

    @Schema(description = "审核意见")
    private String opinion;

    @Schema(description = "意见详情")
    private String remarks;

}
