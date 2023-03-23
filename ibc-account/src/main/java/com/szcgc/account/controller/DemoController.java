package com.szcgc.account.controller;

import com.szcgc.comm.IbcResult;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.dto.UploadFileDto;
import com.szcgc.file.feign.IFileClient;
import com.szcgc.file.model.FileInfo;
import com.szcgc.third.tyc.feign.ITycClient;
import com.szcgc.third.tyc.model.gs.GsBaseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * @Author liaohong
 * @create 2022/9/14 15:47
 */
@Tag(name = "AccountTest")
@RestController
public class DemoController {

    @Operation(summary = "test1")
    @GetMapping("debug/isok")
    public String isOk() {
        return "AccountApplication is ok " + LocalDateTime.now();
    }


    @Operation(summary = "test1")
    @GetMapping("debug/isok2")
    public String isOk2() {
        return "AccountApplication is ok " + LocalDateTime.now();
    }

    @Operation(summary = "test2")
    @GetMapping("debug/{id}")
    public String testId(@PathVariable int id) {
        return "AccountApplication TestId " + id;
    }

    @Autowired
    IFileClient fileClient;

    @Operation(summary = "文件测试新增")
    @GetMapping("debug/file/add")
    public String testFileAdd() {
        FileInfo file = new FileInfo();
        file.setProjectId(11);
        file.setName("文件名");
        file.setPath("path");
        file.setCate(FileCateEnum.Bg_Bhywpsbg);
        file.setCateName("文件类型");
        file.setCreateBy(11);
        file.setCreateAt(LocalDateTime.now());
        IbcResult<Integer> rst = fileClient.add(file);
        return "TestFile:" + rst.isOk() + "," + rst.getValue();
    }

    @Operation(summary = "文件测试")
    @GetMapping("debug/file/del")
    public String testFileDel() {
        fileClient.del(1);
        return "TestFileDel ok";
    }

    @Operation(summary = "文件测试新增")
    @GetMapping("debug/file/upload")
    public String testFileUpload() throws IOException {
        String path = "G:\\file\\img\\phone\\1.jpg";
        String cnt = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(path)));
        UploadFileDto file = new UploadFileDto();
        file.setProjectId(11);
        file.setName("文件名.png");
        file.setContent(cnt);
        file.setCate(FileCateEnum.Bg_Bhywpsbg);
        file.setRemarks("文件备注");
        file.setAccountId(111);
        FileInfo rst = fileClient.upload(file);
        return "TestFile:" + rst.getId();
    }

    @Autowired
    ITycClient tycClient;

    @Operation(summary = "testTyc")
    @GetMapping("/tyc/gs")
    public String testUrl() {
        GsBaseDto info = tycClient.gs("北京豆网科技有限公司");
        return "CustomerApplication tyc gs " + info;
    }
}
