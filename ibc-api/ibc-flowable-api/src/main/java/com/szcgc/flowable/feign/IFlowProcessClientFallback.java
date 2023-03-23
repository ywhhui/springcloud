package com.szcgc.flowable.feign;

import com.szcgc.flowable.model.FlowProcessInfo;

/**
 * @Author liaohong
 * @create 2022/9/26 18:19
 */
public class IFlowProcessClientFallback implements IFlowProcessClient {

    @Override
    public FlowProcessInfo start(String processName, int projectId, int accountId)  {
        return null;
    }

    @Override
    public boolean jump(String processId, String activityId)  {
        return false;
    }

    @Override
    public boolean move(String processId, String currentActivityId, String newActivityId)  {
        return false;
    }

    @Override
    public boolean suspend(String processId)  {
        return false;
    }

    @Override
    public boolean activate(String processId)  {
        return false;
    }

    @Override
    public boolean delete(String processId)  {
        return false;
    }
}
