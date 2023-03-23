package com.szcgc.project.vo.preliminary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author liaohong
 * @create 2022/9/30 10:31
 */
@Data
public class GsBaseVo {
    @Schema(description = "组织机构代码证")
    public String creditCode;// String varchar(255) 统一社会信用代码
    //public String orgNumber;// String varchar(31) 组织机构代码
    @Schema(description = "法人")
    public String legalPersonName;// String varchar(120) 法人
    @Schema(description = "联系方式")
    public String phoneNumber;// String	varchar(255)	企业联系方式
}
