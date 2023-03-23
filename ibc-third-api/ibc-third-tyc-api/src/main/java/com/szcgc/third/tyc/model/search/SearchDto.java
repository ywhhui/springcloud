package com.szcgc.third.tyc.model.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/9/20 18:07
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SearchDto {

    public int total;
    public List<SearchItemDto> items;
}
