package com.szcgc.third.tyc.model.holder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HolderItemDto {
	
	public long id;//	Number	bigint(20)	对应表id
	public String cgid;//	Number	bigint(20)	公司id
	public String hcgid;//	String	varchar(64)	人员hcgid
	public String logo;//	String	varchar(150)	logo
	public String name;//	String	varchar(50)	股东名
	public String alias;//	String	varchar(50)	简称
	public Integer type;// int(11)	股东类型 1-公司 2-人 3-其它
	public List<HolderCapitalDto> capital;	//认缴
	public List<HolderCapitalDto> capitalActl;	//实缴

}
