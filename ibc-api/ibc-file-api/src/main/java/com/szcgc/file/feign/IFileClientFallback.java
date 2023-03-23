package com.szcgc.file.feign;

import com.szcgc.comm.IbcResult;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.dto.UploadFileDto;
import com.szcgc.file.model.FileInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author liaohong
 * @create 2022/9/26 9:09
 */
public class IFileClientFallback implements IFileClient {

    @Override
    public IbcResult<Integer> add(FileInfo file) {
        return null;
    }

    @Override
    public IbcResult<Integer> edit(FileInfo file) {
        return null;
    }

    @Override
    public void del(int fileId) {

    }

    @Override
    public FileInfo findByPath(String path) {
        return null;
    }

    @Override
    public FileInfo findById(int fileId) {
        return null;
    }

    @Override
    public List<FileInfo> findByProject(int projectId, FileCateEnum cate) {
        return null;
    }

    @Override
    public FileInfo upload(UploadFileDto file) {
        return null;
    }

    @Override
    public Map<Integer, String> findPathByIds(String fileIds) {
        return null;
    }
}
