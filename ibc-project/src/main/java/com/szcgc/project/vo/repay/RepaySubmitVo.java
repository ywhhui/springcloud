package com.szcgc.project.vo.repay;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 提交vo
 *
 * @author chenjiaming
 * @date 2022-11-2 14:27:44
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepaySubmitVo {

    @Schema(description = "任务id", required = true)
    private String taskId;

    @Schema(description = "项目id", required = true)
    private int projectId;

    @Schema(description = "还款计划", required = true)
    private List<RepayPlanVo> repayPlanVoList;
}
