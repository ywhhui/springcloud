package com.szcgc.flowable.feign;

import com.szcgc.flowable.model.FlowTaskInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author liaohong
 * @create 2022/9/26 18:18
 */
public class IFlowTaskClientFallback implements IFlowTaskClient {

    @Override
    public List<FlowTaskInfo> queryByProjectId(int projectId)  {
        return null;
    }

    @Override
    public List<FlowTaskInfo> queryByAccountId(int accountId)  {
        return null;
    }

    @Override
    public List<FlowTaskInfo> queryByAccountIdTask(int accountId, String taskName)  {
        return null;
    }

    @Override
    public FlowTaskInfo query(int projectId, int accountId, String taskName, String processName)  {
        return null;
    }

    @Override
    public FlowTaskInfo query(String taskId)  {
        return null;
    }

    @Override
    public boolean assign(String taskId, int accountId)  {
        return false;
    }

    @Override
    public boolean complete(String taskId, String variables)  {
        return false;
    }
}
