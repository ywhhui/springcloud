package com.szcgc.cougua.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.szcgc.cougua.model.MaterialInfo;
import lombok.Data;

import java.util.List;

/**
 *   评估师评估页面 列表返回
 * @Author liaohong
 * @create 2020/9/24 14:58
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaterialAssessedVo {

    /**
     * 待评估反担保物的总件数
     */
    private int count;

    /**
     * 已评估反担保物的件数
     */
    private int done;

    /**
     * 可担保额合计
     */
    private Double guaranteeSum;

    private List<MaterialInfo> list;

}
