package com.szcgc.file.feign;

import com.szcgc.comm.IbcResult;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.file.business.FileUtils;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.dto.UploadFileDto;
import com.szcgc.file.model.FileInfo;
import com.szcgc.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2022/9/26 10:09
 */
@RestController
public class FileClient implements IFileClient {

    @Value("${file.upload.path}")
    String uploadPath;

    @Autowired
    IFileService fileService;

    @Override
    @PostMapping(API_PREFIX + WebConstants.INSERT)
    public IbcResult<Integer> add(@RequestBody FileInfo file) {
        FileInfo info = fileService.insert(file);
        return IbcResult.ok(info.getId());
    }

    @Override
    @PostMapping(API_PREFIX + WebConstants.UPDATE)
    public IbcResult<Integer> edit(@RequestBody FileInfo file) {
        FileInfo info = fileService.update(file);
        return IbcResult.ok(info.getId());
    }

    @Override
    @PostMapping(API_PREFIX + WebConstants.DELETE)
    public void del(@RequestParam("fileId") int fileId) {
        fileService.delete(fileId);
    }

    @Override
    @GetMapping(PATH)
    public FileInfo findByPath(@RequestParam("path") String path) {
        return fileService.findByPath(path);
    }

    @Override
    @GetMapping(API_PREFIX + WebConstants.DETAIL)
    public FileInfo findById(@RequestParam("fileId") int fileId) {
        return fileService.find(fileId).get();
    }

    @Override
    @GetMapping(PROJECT)
    public List<FileInfo> findByProject(@RequestParam("projectId") int projectId, @RequestParam(value = "cate", required = false) FileCateEnum cate) {
        return fileService.findByProjectIdAndCate(projectId, cate);
    }

    @Override
    @PostMapping(UPLOAD)
    public FileInfo upload(@RequestBody UploadFileDto file) {
        try {
            IbcResult<String> rst = FileUtils.saveFile(file.getName(), file.getContent(), uploadPath);
            if (!rst.isOk())
                return null;
            FileInfo info = fileService.add(file.getAccountId(), file.getProjectId(), file.getCate(), file.getName(), rst.getValue(), file.getRemarks());
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @GetMapping(BATCH_PATH)
    public Map<Integer, String> findPathByIds(@RequestParam("fileIds") String fileIds) {
        if (StringUtils.isEmpty(fileIds))
            return Collections.EMPTY_MAP;
        List<Integer> ids = Arrays.stream(fileIds.split(",")).filter(item -> !StringUtils.isEmpty(item)).map(Integer::valueOf).collect(Collectors.toList());
        List<FileInfo> files = fileService.findByIds(ids);
        return files.stream().collect(Collectors.toMap(FileInfo::getId, FileInfo::getPath));
    }
}
