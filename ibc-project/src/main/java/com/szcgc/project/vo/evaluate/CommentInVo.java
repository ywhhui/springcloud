package com.szcgc.project.vo.evaluate;

import com.szcgc.project.vo.BaseInVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author liaohong
 * @create 2022/10/11 19:14
 */
@Data
public class CommentInVo extends BaseInVo {

    @Schema(description = "B角意见")
    private String opinion;

    @Schema(description = "B角意见详情")
    private String remarks;

    @Schema(description = "B角报告文件")
    private String filePath;
}
