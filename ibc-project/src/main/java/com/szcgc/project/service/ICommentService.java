package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.model.CommentInfo;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/10/11 19:19
 */
public interface ICommentService extends IbcService<CommentInfo, Integer> {

    List<CommentInfo> findByProjectId(int projectId);
}
