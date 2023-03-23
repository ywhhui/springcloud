package com.szcgc.file.service;

import com.szcgc.comm.IbcService;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.model.FileInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author liaohong
 * @create 2022/9/26 9:15
 */
public interface IFileService extends IbcService<FileInfo, Integer> {

    FileInfo add(int accountId, int projectId, FileCateEnum cate, String name, String path, String remarks);

    List<FileInfo> findByIds(List<Integer> ids);

    List<FileInfo> findByProjectId(int projectId);

    List<FileInfo> findByProjectIdAndCate(int projectId, FileCateEnum cate);

    List<FileInfo> findByProjectIdAndCates(int projectId, List<FileCateEnum> cates);

    FileInfo findByPath(String path);
}
