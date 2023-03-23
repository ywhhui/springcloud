package com.szcgc.project.service;

import com.szcgc.project.model.EvaluateInfo;

/**
 * @Author liaohong
 * @create 2020/9/17 17:01
 */
public interface IEvaluateService extends IProjectSuperFind<EvaluateInfo, Integer> {

    EvaluateInfo findByProjectIdAndTimes(int projectId, int times);

    EvaluateInfo findByProjectIdAndTaskId(int projectId, String taskId);

    boolean isRepeatLend(int projectId);

    int countTimes(int projectId);

}
