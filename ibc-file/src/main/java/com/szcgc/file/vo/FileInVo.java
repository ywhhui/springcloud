package com.szcgc.file.vo;

import com.szcgc.file.constant.FileCateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author liaohong
 * @create 2022/10/12 10:13
 */
@Data
public class FileInVo {

    @Schema(description = "项目Id")
    private int projectId;

    @Schema(description = "材料类型")
    private FileCateEnum cate;

    @Schema(description = "文件地址")
    private String path;

    @Schema(description = "备注")
    private String remarks;
}
