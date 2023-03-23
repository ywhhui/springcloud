package com.szcgc.file.dto;

import com.szcgc.file.constant.FileCateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author liaohong
 * @create 2022/9/30 15:19
 */
@Data
public class UploadFileDto {

    @Schema(description = "上传用户Id")
    private int accountId;

    @Schema(description = "项目Id")
    private int projectId;

    @Schema(description = "文件名")
    private String name;

    @Schema(description = "材料类型")
    private FileCateEnum cate;

    @Schema(description = "文件Base64编码")
    private String content;

    @Schema(description = "备注")
    private String remarks;

}
