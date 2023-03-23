package com.szcgc.third.tyc.request.holder;

import com.szcgc.third.tyc.business.TycRequest;
import com.szcgc.third.tyc.config.TycConfig;

/**
 * @Author liaohong
 * @create 2022/9/19 10:34
 */
public class HolderReq extends TycRequest {

    public String keyword;
    public int pageNum; // 当前页
    public int pageSize;// 每页条数（最大10条）

    public HolderReq(String keyword,int pageNum, int pageSize) {
        super(TycConfig.self.getHolderId(), TycConfig.self.getHolderUrl());
        this.keyword=keyword;
        this.pageNum=pageNum;
        this.pageSize=pageSize;
    }

}
