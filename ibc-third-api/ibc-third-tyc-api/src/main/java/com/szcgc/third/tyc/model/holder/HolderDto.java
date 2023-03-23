package com.szcgc.third.tyc.model.holder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 股东信息
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class HolderDto {
	
	public int total;
	public List<HolderItemDto> items;

}
