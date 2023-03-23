package com.szcgc.project.repository;

import com.szcgc.project.model.CommentInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/10/11 19:19
 */
public interface CommentRepository extends PagingAndSortingRepository<CommentInfo, Integer> {

    List<CommentInfo> findByProjectId(int projectId);
}
