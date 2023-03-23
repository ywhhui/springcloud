package com.szcgc.file.repository;

import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.model.FileInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/18 10:20
 */
public interface FileRepository extends PagingAndSortingRepository<FileInfo, Integer> {

    List<FileInfo> findByIdIn(Collection<Integer> ids);

    List<FileInfo> findByProjectId(int projectId);

    List<FileInfo> findByProjectIdAndCate(int projectId, FileCateEnum cate);

    List<FileInfo> findByProjectIdAndCateIn(int projectId, Collection<FileCateEnum> cates);

    FileInfo findByPath(String path);
}
