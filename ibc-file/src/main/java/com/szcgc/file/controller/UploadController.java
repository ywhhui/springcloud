package com.szcgc.file.controller;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.IbcResult;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.file.business.FileUtils;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.dto.UploadFileDto;
import com.szcgc.file.jpa.PathAttributeConvert;
import com.szcgc.file.model.FileInfo;
import com.szcgc.file.service.IFileService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author liaohong
 * @create 2022/9/26 9:48
 */
@Api(tags = "文件上传")
@RestController
public class UploadController {

    @Value("${file.upload.path}")
    String uploadPath;

    @Value("${file.url.prefix}")
    String urlPrefix;

    @Operation(summary = "文件上传", description = "前端Input组件名需要为file")
    @PostMapping("upload")
    public IbcResponse<String> fileUpload(@RequestParam("file") MultipartFile mFile, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        //response.setContentType("text/html; charset=UTF-8");
        //PrintWriter writer = response.getWriter();
        if (mFile != null && !mFile.getOriginalFilename().isEmpty()) {
            IbcResult<String> rst = FileUtils.saveFile(mFile, uploadPath);
            if (rst.isOk())
                return IbcResponse.ok(urlPrefix + rst.getValue());
            return IbcResponse.error500(rst.getValue());
        }
        return IbcResponse.error400("找不到上传文件");
    }

    @PostConstruct
    public void init() {
        PathAttributeConvert.prefix = urlPrefix;
    }

}
