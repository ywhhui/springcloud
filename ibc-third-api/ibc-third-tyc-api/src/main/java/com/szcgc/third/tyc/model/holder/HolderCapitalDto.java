package com.szcgc.third.tyc.model.holder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HolderCapitalDto {
	public String amomon;//	String	varchar(1000)	出资金额
	public String time;//	String	日期	出资时间
	public String percent;//	String	varchar(50)	占比
	public String paymet;//	String	varchar(1000)	实缴方式
}
