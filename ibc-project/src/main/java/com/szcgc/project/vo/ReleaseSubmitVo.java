package com.szcgc.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 解保 解除担保物和保证金 需要的参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseSubmitVo {

    @Schema(name = "项目Id")
    private int projectId;

    /**
     * 任务id
     */
    private String taskId;

    @Schema(description = "待解保的抵押物类型 1：反担保措施 2:保证金： 3：保理业务品种的应收账款")
    private String releaseType;

}
