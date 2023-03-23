package com.szcgc.third.tyc.feign;

import com.szcgc.third.tyc.model.gs.GsBaseDto;
import com.szcgc.third.tyc.model.holder.HolderDto;
import com.szcgc.third.tyc.model.search.SearchDto;
import org.springframework.stereotype.Component;

/**
 * @Author liaohong
 * @create 2022/9/19 11:20
 */
@Component
public class ITycClientFallback implements  ITycClient {

    @Override
    public GsBaseDto gs(String name) {
        return null;
    }

    @Override
    public HolderDto holder(String keyword, int pageNow, int pageSize) {
        return null;
    }

    @Override
    public SearchDto search(String keyword, int pageNow, int pageSize) {
        return null;
    }
}
