package com.szcgc.customer.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 提交vo
 *
 * @author chenjiaming
 * @date 2022-10-12 19:43:36
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustSubmitVo {

    @Schema(description = "任务id", required = true)
    private String taskId;

    @Schema(description = "项目id", required = true)
    private int projectId;
}
