package com.szcgc.project.service;

import com.szcgc.project.model.ReviewInfo;

/**
 * @Author liaohong
 * @create 2020/10/14 16:02
 */
public interface IReviewService extends IProjectSuperFind<ReviewInfo, Integer> {

    ReviewInfo findLastStopReason(int projectId);
}
