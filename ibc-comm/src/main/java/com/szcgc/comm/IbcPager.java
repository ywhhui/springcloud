package com.szcgc.comm;

import org.springframework.data.domain.Page;

import java.util.List;

public class IbcPager<T> {

    public List<T> content;
    public int totalCount;
    public int totalPage;
    //public int pageNo;
    //public int pageSize;

    public IbcPager(List<T> content, int totalCount, int totalPage) {
        this.content = content;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
    }

    public static <T> IbcPager of(Page<T> page) {
        return new IbcPager(page.getContent(), (int) page.getTotalElements(), page.getTotalPages());
    }

    public static <T> IbcPager of(List<T> content) {
        return new IbcPager(content, content.size(), 1);
    }
}
