package com.szcgc.file.feign;

import com.szcgc.comm.IbcResult;
import com.szcgc.comm.constant.AppConstant;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.dto.UploadFileDto;
import com.szcgc.file.model.FileInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author liaohong
 * @create 2022/9/26 9:08
 */
@FeignClient(
        value = AppConstant.APPLICATION_FILE_NAME,
        fallback = IFileClientFallback.class
)
public interface IFileClient {

    String API_PREFIX = "/file";
    String PROJECT = API_PREFIX + "/project";
    String PATH = API_PREFIX + "/path";
    String UPLOAD = API_PREFIX + "/upload4dto";
    String BATCH_PATH = API_PREFIX + "/batch-path";

    /**
     * 增加文件
     *
     * @param file
     * @return 新增文件Id
     */
    @PostMapping(API_PREFIX + WebConstants.INSERT)
    IbcResult<Integer> add(@RequestBody FileInfo file);

    /**
     * 修改文件
     *
     * @param file
     * @return 文件Id
     */
    @PostMapping(API_PREFIX + WebConstants.UPDATE)
    IbcResult<Integer> edit(@RequestBody FileInfo file);

    /**
     * 删除文件
     *
     * @param fileId
     */
    @PostMapping(API_PREFIX + WebConstants.DELETE)
    void del(@RequestParam("fileId") int fileId);

    /**
     * 根据文件路径 获取文件信息
     *
     * @param path
     */
    @GetMapping(PATH)
    FileInfo findByPath(@RequestParam("path") String path);

    /**
     * 文件详情
     *
     * @param fileId
     */
    @GetMapping(API_PREFIX + WebConstants.DETAIL)
    FileInfo findById(@RequestParam("fileId") int fileId);

    /**
     * 根据项目获取文件列表
     *
     * @param projectId
     * @param cate
     * @return
     */
    @GetMapping(PROJECT)
    List<FileInfo> findByProject(@RequestParam("projectId") int projectId, @RequestParam(value = "cate", required = false) FileCateEnum cate);

    /**
     * 模块上传文件到文件服务器(如果filInfo的id为0，表示文件上传失败)
     *
     * @param file
     * @return 返回入库后的信息
     */
    @PostMapping(UPLOAD)
    FileInfo upload(@RequestBody UploadFileDto file);

    /**
     * 文件地址
     *
     * @param fileIds 以,分隔的文件Id
     */
    @GetMapping(BATCH_PATH)
    Map<Integer, String> findPathByIds(@RequestParam("fileIds") String fileIds);

}
