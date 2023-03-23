package com.szcgc.project.vo;

import com.szcgc.project.constant.ProjectActEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请款提交 需要的参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteLoanVo {

    @Schema(name = "项目Id")
    private int projectId;

    /**
     * 任务id
     */
    private String taskId;

    @Schema(description = "收款开户行")
    private String inviteBankName;

    @Schema(description = "收款账号")
    private String inviteBankAccount;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "操作类型(1:通过,2:退回)")
    private String auditRst;

    @Schema(description = "意见详情")
    private String confirmOpinion;

    private ProjectActEnum projectActEnum;


}
