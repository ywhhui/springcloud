package com.szcgc.third.tyc.request.gs;

import com.szcgc.third.tyc.business.TycRequest;
import com.szcgc.third.tyc.config.TycConfig;

/**
 * @Author liaohong
 * @create 2022/9/19 10:27
 */
public class GsBaseReq extends TycRequest {

    /**
     * name:姓名/全称
     */
    public String name;

    /**
     * id:公司Id
     */
    public String id;

    public GsBaseReq(String name, String id) {
        super(TycConfig.self.getGsBaseId(), TycConfig.self.getGsBaseUrl());
        this.name = name;
        this.id = id;
    }
}
