package com.szcgc.file.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.comm.util.SundryUtils;
import com.szcgc.file.business.FileUtils;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.model.FileInfo;
import com.szcgc.file.repository.FileRepository;
import com.szcgc.file.service.IFileService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author liaohong
 * @create 2020/9/17 17:37
 */
@Service
public class FileService extends BaseService<FileRepository, FileInfo, Integer> implements IFileService {

    @Override
    public FileInfo add(int accountId, int projectId, FileCateEnum cate, String name, String path, String remarks) {
        FileInfo info = new FileInfo();
        info.setCreateBy(accountId);
        info.setProjectId(projectId);
        info.setName(name);
        info.setCate(cate);
        info.setCateName(cate.getCnName());
        info.setPath(path);
        info.setRemarks(remarks);
        info.setCreateAt(LocalDateTime.now());
        info.setUpdateBy(accountId);
        info.setUpdateAt(info.getCreateAt());
        return insert(info);
    }

    @Override
    public FileInfo insert(FileInfo entity) {
        if (entity.getCateName() == null) {
            entity.setCateName(entity.getCate().getCnName());
        }
        if (entity.getCreateAt() == null) {
            entity.setCreateAt(LocalDateTime.now());
        }
        if (entity.getName() == null) {
            int index = entity.getPath().lastIndexOf(FileUtils.URL_SEP);
            if (index >= 0) {
                entity.setName(entity.getPath().substring(index + 1));
            } else {
                entity.setName(StringUtils.first(entity.getPath(), 50));
            }
        }
        return super.insert(entity);
    }

    @Override
    public List<FileInfo> findByIds(List<Integer> ids) {
        if (ids == null || ids.size() <= 0)
            return null;
        return repository.findByIdIn(ids);
    }

    @Override
    public List<FileInfo> findByProjectId(int projectId) {
        return repository.findByProjectId(SundryUtils.requireId(projectId));
    }

    @Override
    public List<FileInfo> findByProjectIdAndCate(int projectId, FileCateEnum cate) {
        if (cate == null)
            return findByProjectId(projectId);
        return repository.findByProjectIdAndCate(projectId, cate);
    }

    @Override
    public List<FileInfo> findByProjectIdAndCates(int projectId, List<FileCateEnum> cates) {
        if (cates == null || cates.size() <= 0)
            return findByProjectId(projectId);
        return repository.findByProjectIdAndCateIn(projectId, cates);
    }

    @Override
    public FileInfo findByPath(String path) {
        return repository.findByPath(path);
    }

}
