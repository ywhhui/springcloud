package com.szcgc.project.vo.loan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.szcgc.project.model.CapitalRegister;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 提交vo
 *
 * @author chenjiaming
 * @date 2022-11-2 14:27:44
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitVo {

    @Schema(description = "任务id", required = true)
    private String taskId;

    @Schema(description = "项目id", required = true)
    private int projectId;

    @Schema(description = "意见")
    private String remark;

    @Schema(description = "登记信息")
    private CapitalRegister capitalRegister;
}
