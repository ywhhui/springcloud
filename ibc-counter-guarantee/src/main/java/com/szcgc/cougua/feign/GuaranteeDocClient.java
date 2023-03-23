package com.szcgc.cougua.feign;

import com.szcgc.cougua.vo.GuaranteeDocVo;
import com.szcgc.cougua.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 解析doc文件工具类
 *
 */
@FeignClient(value = "guarantee-doc",url = "10.9.8.227:8000")
public interface GuaranteeDocClient {


    @PostMapping(value = "/report-tables", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultVo<GuaranteeDocVo> guaranteeDoc(@RequestPart("report_file") MultipartFile file);

}
