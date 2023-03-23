package com.szcgc.finance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 带财报数据的客户
 *
 * @author chenjiaming
 * @date 2022-10-14 11:52:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceCustomerVo {

    private Integer id;

    @Schema(description = "客户名称")
    private String name;

    @Schema(description = "项目信息")
    private List<FinanceProjectVo> projectList;

}
