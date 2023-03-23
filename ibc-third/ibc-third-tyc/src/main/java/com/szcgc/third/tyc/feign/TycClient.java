package com.szcgc.third.tyc.feign;

import com.szcgc.third.tyc.business.TycApi;
import com.szcgc.third.tyc.model.gs.GsBaseDto;
import com.szcgc.third.tyc.model.holder.HolderDto;
import com.szcgc.third.tyc.model.search.SearchDto;
import com.szcgc.third.tyc.request.gs.GsBaseReq;
import com.szcgc.third.tyc.request.holder.HolderReq;
import com.szcgc.third.tyc.request.search.SearchReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liaohong
 * @create 2022/9/19 10:09
 */
@RestController
public class TycClient implements ITycClient {

    @Override
    @GetMapping(GS)
    public GsBaseDto gs(@RequestParam("name") String name) {
        try {
            return TycApi.interact(new GsBaseReq(name, null), GsBaseDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @GetMapping(HOLDER)
    public HolderDto holder(@RequestParam("keyword") String keyword, @RequestParam("pageNow") int pageNow, @RequestParam("pageSize") int pageSize) {
        try {
            return TycApi.interact(new HolderReq(keyword, pageNow, pageSize), HolderDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @GetMapping(SEARCH)
    public SearchDto search(@RequestParam("keyword") String keyword, @RequestParam("pageNow") int pageNow, @RequestParam("pageSize") int pageSize) {
        try {
            return TycApi.interact(new SearchReq(keyword, pageNow, pageSize), SearchDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
