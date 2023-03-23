package com.szcgc.customer.model.factor;

import com.szcgc.customer.model.base.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 股权结构
 * @Author liaohong
 * @create 2022/9/20 9:00
 */
@Entity
@Table(name = "equityinfo", schema = "gmis_customer")
public class EquityInfo extends FactorInfo {

    @Schema(description = "主体名称")
    @Column(length = 50)
    private String name;

    @Schema(description = "企业类型")
    @Column(length = 20)
    private String cate;

    @Schema(description = "股权比例")
    private int proportion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public int getProportion() {
        return proportion;
    }

    public void setProportion(int proportion) {
        this.proportion = proportion;
    }
}
