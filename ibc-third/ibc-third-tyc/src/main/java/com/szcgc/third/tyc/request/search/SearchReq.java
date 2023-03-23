package com.szcgc.third.tyc.request.search;

import com.szcgc.third.tyc.business.TycRequest;
import com.szcgc.third.tyc.config.TycConfig;

/**
 * @Author liaohong
 * @create 2022/9/19 10:34
 */
public class SearchReq extends TycRequest {

    public String word;
    public int pageNum; // 当前页
    public int pageSize;// 每页条数（最大10条）

    public SearchReq(String word, int pageNum, int pageSize) {
        super(TycConfig.self.getSearchId(), TycConfig.self.getSearchUrl());
        this.word =word;
        this.pageNum=pageNum;
        this.pageSize=pageSize;
    }

}
