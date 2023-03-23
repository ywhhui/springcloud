package com.szcgc.project.vo.preliminary;

import com.szcgc.project.vo.BaseInVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author liaohong
 * @create 2022/9/29 11:41
 */
@Data
public class SupervisorInVo extends BaseInVo {

    public static final int CATE_SELF = 1;

    @Schema(description = "分配类型 1本部门 2跨部门")
    private int cate;

    @Schema(description = "项目A角Id")
    private int roleAId;

    @Schema(description = "项目B角Id")
    private int roleBId;

    @Schema(description = "项目C角Id")
    private int roleCId;

    @Schema(description = "跨部门分配选择的部门Id")
    private int departmentId;

}
