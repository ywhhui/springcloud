package com.szcgc.file.controller;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.model.FileInfo;
import com.szcgc.file.service.IFileService;
import com.szcgc.file.vo.FileInVo;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author liaohong
 * @create 2022/10/10 14:58
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("files")
public class FileController {

    @Autowired
    IFileService fileService;

    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<String> edit(@IbcId @Parameter(hidden = true) int accountId, @RequestBody FileInfo file) {
        file.setUpdateBy(accountId);
        file.setUpdateAt(LocalDateTime.now());
        fileService.update(file);
        return IbcResponse.ok();
    }

    @PostMapping(WebConstants.INSERT)
    public IbcResponse<String> insert(@IbcId @Parameter(hidden = true) int accountId, @RequestBody FileInVo vo) {
        FileInfo file = new FileInfo();
        BeanUtils.copyProperties(vo, file);
        file.setCreateBy(accountId);
        fileService.insert(file);
        return IbcResponse.ok();
    }

    @PostMapping(WebConstants.DELETE)
    public IbcResponse<String> del(@RequestParam("fileId") int fileId) {
        fileService.delete(fileId);
        return IbcResponse.ok();
    }

//    @GetMapping("path")
//    public FileInfo findByPath(@RequestParam("path") String path) {
//        return fileService.findByPath(path);
//    }

    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<FileInfo> findById(@RequestParam("fileId") int fileId) {
        return IbcResponse.ok(fileService.find(fileId).get());
    }

    @GetMapping("project")
    public IbcResponse<List<FileInfo>> findByProject(@RequestParam("projectId") int projectId, @RequestParam(value = "cate", required = false) FileCateEnum cate) {
        return IbcResponse.ok(fileService.findByProjectIdAndCate(projectId, cate));
    }
}
