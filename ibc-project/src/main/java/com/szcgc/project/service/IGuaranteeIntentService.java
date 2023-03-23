package com.szcgc.project.service;

import com.szcgc.project.model.GuaranteeIntentInfo;

/**
 * @Author liaohong
 * @create 2020/10/15 15:07
 */
public interface IGuaranteeIntentService extends IProjectSuperFind<GuaranteeIntentInfo, Integer> {

    int countBankFail(int projectId);

}
