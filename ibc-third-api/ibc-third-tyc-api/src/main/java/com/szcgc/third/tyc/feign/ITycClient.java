package com.szcgc.third.tyc.feign;

import com.szcgc.comm.constant.AppConstant;
import com.szcgc.third.tyc.model.gs.GsBaseDto;
import com.szcgc.third.tyc.model.holder.HolderDto;
import com.szcgc.third.tyc.model.search.SearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author liaohong
 * @create 2022/9/19 9:52
 */
@FeignClient(
        value = AppConstant.APPLICATION_THIRD_TYC_NAME,
        fallback = ITycClientFallback.class
)
public interface ITycClient {

    String API_PREFIX = "/tyc";
    String GS = API_PREFIX + "/gs";
    String HOLDER = API_PREFIX + "/holder";
    String SEARCH = API_PREFIX
            + "/search";

    /**
     * 工商信息
     *
     * @param name
     * @return
     */
    @GetMapping(GS)
    GsBaseDto gs(@RequestParam("name") String name);

    /**
     * 股东信息
     *
     * @param keyword
     * @param pageNow
     * @param pageSize
     * @return
     */
    @GetMapping(HOLDER)
    HolderDto holder(@RequestParam("keyword") String keyword, @RequestParam("pageNow") int pageNow, @RequestParam("pageSize") int pageSize);

    /**
     * 模糊查询企业名
     * @param keyword
     * @return
     */
    @GetMapping(SEARCH)
    SearchDto search(@RequestParam("keyword") String keyword, @RequestParam("pageNow") int pageNow, @RequestParam("pageSize") int pageSize);
}
