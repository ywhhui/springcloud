package com.szcgc.cougua.controller;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.message.IProjectSender;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.cougua.model.MaterialInfo;
import com.szcgc.cougua.model.MaterialSubmitInfo;
import com.szcgc.cougua.service.IMaterialService;
import com.szcgc.cougua.service.IMaterialSubmitService;
import com.szcgc.cougua.vo.MaterialDivideSubmitVo;
import com.szcgc.cougua.vo.MaterialSubmitVo;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.feign.IFileClient;
import com.szcgc.file.model.FileInfo;
import com.szcgc.project.constant.ProjectActEnum;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *   提交
 * @Author liaohong
 * @create 2022/9/14 15:47
 */
@Api(tags = "担保页面相关提交")
@RestController
@RequestMapping("material")
public class MaterialSubmitController {

    private static final Log logger = LogFactory.getLog(MaterialSubmitController.class);

    @Autowired
    IMaterialSubmitService materialSubmitService;

    @Autowired
    IProjectSender sender;

    @Autowired
    IMaterialService materialService;

    @Autowired
    IFileClient fileClient;

    /**
     * 添加反担保物后的提交
     * @param
     * @return
     */
    @Operation(summary = "添加反担保物的提交", description = "添加反担保物的提交")
    @PostMapping("/increase/submit")
    public IbcResponse<Integer> insert(@IbcId @Parameter(hidden = true) int accountId,@RequestBody MaterialSubmitVo materialSubmitVo) {
        logger.info("increase入参 materialSubmitVo param:"+ JsonUtils.toJSONString(materialSubmitVo));
        List<MaterialInfo> result = materialService.findByProjectId(materialSubmitVo.getProjectId());
        //登记担保物的校验
        if (CollectionUtils.isEmpty(result)) {
            return IbcResponse.error400("请添加担保物！");
        }
        //流程的接口
        sender.projectAction(accountId,materialSubmitVo.getProjectId(), ProjectActEnum.AE_Counter.name(),materialSubmitVo.getTaskId() );
        return IbcResponse.ok();
    }

    /**
     * 分配评估师的提交
     * @param materialDivideSubmitVo
     * @return
     */
    @Operation(summary = "分配评估师的提交", description = "分配评估师的提交")
    @PostMapping("/divide/submit")
    public IbcResponse<Integer> assessSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody MaterialDivideSubmitVo materialDivideSubmitVo ) {
        logger.info("入参 materialSubmitInfo param:"+ JsonUtils.toJSONString(materialDivideSubmitVo));
        //分配评估师校验
        if (0 == materialDivideSubmitVo.getAssessingAccountId()) {
            return IbcResponse.error400("请选择评估师！");
        }
        MaterialSubmitInfo materialSubmitInfo = new MaterialSubmitInfo();
        materialSubmitInfo.setCreateBy(accountId);
        materialSubmitInfo.setCreateAt(LocalDateTime.now());
        materialSubmitInfo.setProjectId(materialDivideSubmitVo.getProjectId());
        materialSubmitInfo.setAssessingAccountId(materialDivideSubmitVo.getAssessingAccountId());
        materialSubmitService.insert(materialSubmitInfo);
        //流程的接口
        sender.projectAction(accountId,materialDivideSubmitVo.getProjectId(), ProjectActEnum.AE_Distribute.name(),materialDivideSubmitVo.getTaskId() );
        return IbcResponse.ok();
    }

    /**
     * 评估师评估完的提交
     * @param
     * @return
     */
    @Operation(summary = "评估师评估完的提交", description = "评估师评估完的提交")
    @PostMapping("/assessed/submit")
    public IbcResponse<Integer> assessedSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody MaterialSubmitVo materialSubmitVo) {
        logger.info("assessed入参 materialSubmitVo param:"+ JsonUtils.toJSONString(materialSubmitVo));
        List<MaterialInfo> result = materialService.searchByProjectId(materialSubmitVo.getProjectId());
        List<MaterialInfo> assessedResult = result.stream().filter(e->0==e.getAssessedStatus()).collect(Collectors.toList());
        List<FileInfo> byProject = fileClient.findByProject(materialSubmitVo.getProjectId(), FileCateEnum.Bg_Fdbwbg);
        //评估师校验
        if (assessedResult.size() == result.size() ||  CollectionUtils.isEmpty(byProject)) {
            return IbcResponse.error400("至少评估一条担保物,并且上传评估报告,才能提交");
        }
        //流程的接口
        sender.projectAction(accountId,materialSubmitVo.getProjectId(), ProjectActEnum.AE_Scoring.name(),materialSubmitVo.getTaskId() );
        return IbcResponse.ok();
    }

    /**
     * 复评师 评估完的提交
     * @param
     * @return
     */
    @Operation(summary = "复评师师评估完的提交", description = "复评师师评估完的提交")
    @PostMapping("/reassessed/submit")
    public IbcResponse<Integer> reassessedSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody MaterialSubmitVo materialSubmitVo) {
        logger.info("reassessedSubmit入参 materialSubmitVo param:"+ JsonUtils.toJSONString(materialSubmitVo));
        List<FileInfo> byProject = fileClient.findByProject(materialSubmitVo.getProjectId(), FileCateEnum.Bg_Fdbwbg);
        List<Integer> userIds = byProject.stream().map(FileInfo::getCreateBy).collect(Collectors.toList());
        //复评师需要生产至少一条报告校验
        if(!userIds.contains(accountId)){
            return IbcResponse.error400("至少评估一条担保物,并且上传评估报告,才能提交");
        }
        //流程的接口
        sender.projectAction(accountId,materialSubmitVo.getProjectId(), ProjectActEnum.AE_ReScoring.name(),materialSubmitVo.getTaskId() );
        return IbcResponse.ok();
    }

}
