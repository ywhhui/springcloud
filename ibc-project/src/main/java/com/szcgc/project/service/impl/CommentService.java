package com.szcgc.project.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.project.model.CommentInfo;
import com.szcgc.project.repository.CommentRepository;
import com.szcgc.project.service.ICommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/10/11 19:19
 */
@Service
public class CommentService extends BaseService<CommentRepository, CommentInfo, Integer> implements ICommentService {

    @Override
    public List<CommentInfo> findByProjectId(int projectId) {
        return repository.findByProjectId(projectId);
    }
}
