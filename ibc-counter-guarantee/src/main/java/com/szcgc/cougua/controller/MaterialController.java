package com.szcgc.cougua.controller;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.cougua.model.MaterialInfo;
import com.szcgc.cougua.service.IMaterialService;
import com.szcgc.cougua.vo.AssessedReportTemplateVo;
import com.szcgc.cougua.vo.MaterialAssessedVo;
import com.szcgc.file.model.FileInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *   反担保措施相关
 * @Author liaohong
 * @create 2022/9/14 15:47
 */
@Api(tags = "反担保措施相关")
@RestController
@RequestMapping("material")
public class MaterialController {

    private static final Log logger = LogFactory.getLog(MaterialController.class);

    @Autowired
    IMaterialService materialService;

    /**
     * 根据不同类型 添加不同类型的反担保措施
     * @param materialInfo
     * @return
     */
    @Operation(summary = "新增", description = "新增")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<Integer> insert(@IbcId @Parameter(hidden = true) int accountId, @RequestBody MaterialInfo materialInfo) {
        logger.info("入参 materialInfo param:"+ JsonUtils.toJSONString(materialInfo));
        materialInfo.setCreateAt(LocalDateTime.now());
        materialInfo.setCreateBy(accountId);
        MaterialInfo result = materialService.insert(materialInfo);
        return IbcResponse.ok(result.getId());
    }

    /**
     * 修改反担保措施
     * @param materialInfo
     * @return
     */
    @Operation(summary = "修改", description = "修改反担保措施")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<Integer> update(@IbcId @Parameter(hidden = true) int accountId,@RequestBody MaterialInfo materialInfo) {
        logger.info("update入参 materialInfo param:"+JsonUtils.toJSONString(materialInfo));
        materialInfo.setUpdateAt(LocalDateTime.now());
        materialInfo.setUpdateBy(accountId);
        MaterialInfo result = materialService.update(materialInfo);
        return IbcResponse.ok(result.getId());
    }

    /**
     * 删除反担保措施
     * @param id
     * @return
     */
    @Operation(summary = "删除", description = "删除")
    @PostMapping(WebConstants.DELETE)
    public IbcResponse delete(@RequestParam(name = "id") Integer id) {
        logger.info("delete入参 materialInfo:"+id);
        materialService.delete(id);
        return IbcResponse.ok();
    }

    /**
     *  根据项目id 获取项目的所有 反担保措施 不带分页
     * @param projectId
     * @return
     */
    @Operation(summary = "根据项目id获取项目的所有反担保措施", description = "根据项目id获取项目的所有反担保措施")
    @GetMapping("/project")
    public IbcResponse<List<MaterialInfo>> findByProjectId(@RequestParam("projectId") Integer projectId) {
        logger.info("materialInfo入参 findByProjectId param:"+projectId);
        List<MaterialInfo> result = materialService.findByProjectId(projectId);
        return IbcResponse.ok(result);
    }

    /**
     *  分配评估师页面 根据担保类型(房地产抵押 建设用地使用权 股票)获取评估列表相关信息
     * @return
     */
    @Operation(summary = "根据项目id获取评估列表相关信息", description = "根据项目id获取评估列表相关信息")
    @GetMapping("/assessed/project")
    public IbcResponse<List<MaterialInfo>> searchAssessed(@RequestParam("projectId") Integer projectId) {
        logger.info("materialInfo入参 search param:"+projectId);
        List<MaterialInfo> result = materialService.searchByProjectId(projectId);
        return IbcResponse.ok(result);
    }

    /**
     *  评估页面专用 根据项目id 获取待评估类型(房地产抵押 建设用地使用权 股票)的数据 带评估可担保总和
     * @return
     */
    @Operation(summary = "根据项目id获取评估列表相关信息", description = "根据项目id获取评估列表相关信息")
    @GetMapping("/assessing/project")
    public IbcResponse<MaterialAssessedVo> assessingList(@RequestParam("projectId") Integer projectId) {
        logger.info("materialAssessedVo search param:"+projectId);
        MaterialAssessedVo assessedVo = new MaterialAssessedVo();
        List<MaterialInfo> result = materialService.searchByProjectId(projectId);
        if(CollectionUtils.isNotEmpty(result)){
            assessedVo.setCount(result.size());
            Double sum =result.stream().mapToDouble(e->null==e.getGuaranteeLimit()?Double.valueOf(0):e.getGuaranteeLimit().doubleValue()).sum();
            assessedVo.setGuaranteeSum(sum);
            List<MaterialInfo> resultDone = result.stream().filter(e->1==e.getAssessedStatus()).collect(Collectors.toList());
            assessedVo.setDone(resultDone.size());
            logger.info("materialAssessedVo assessedVo:"+JsonUtils.toJSONString(assessedVo));
            assessedVo.setList(result);
        }
        return IbcResponse.ok(assessedVo);
    }


    /**
     * 实物保证详情
     * @param id
     * @return
     */
    @Operation(summary = "详情", description = "详情")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<MaterialInfo> getById(@RequestParam("id") int id) {
        Optional<MaterialInfo> materialInfo = materialService.find(id);
        if (materialInfo.isPresent()) {
            return IbcResponse.ok(materialInfo.get());
        }
        return IbcResponse.error500("id不存在");
    }

    /**
     * 根据不同类型(房地产 建设用地使用权 股票) 生产对应的评估报告
     * @param reportTemplateVos
     * @return
     */
    @Operation(summary = "根据模板生产对应的评估报告", description = "生产对应的评估报告")
    @PostMapping("/assessed/report/template")
    public IbcResponse<List<FileInfo>> templateReportAdd(@IbcId @Parameter(hidden = true) int accountId, @RequestBody List<AssessedReportTemplateVo> reportTemplateVos) throws IOException {
        logger.info("入参 templateReportAdd param:"+ JsonUtils.toJSONString(reportTemplateVos));
        materialService.templateReportAdd(accountId,reportTemplateVos);
        return IbcResponse.ok();
    }

    /**
     * 根据普通类评审报告模板 插入反担保相关数据和保证相关数据
     * @return
     */
    @Operation(summary = "根据模板生产对应的评审报告", description = "生产对应的评审报告")
    @PostMapping("/export/report/template")
    public IbcResponse<List<FileInfo>> exportReportTemplate(@IbcId @Parameter(hidden = true) int accountId,@RequestParam @ApiParam(name = "项目id", required = true) Integer projectId) throws IOException {
        logger.info("入参 exportReportTemplate param:"+ projectId);
         materialService.exportReportTemplate(accountId,projectId);
        return IbcResponse.ok();
    }

    /**
     * 根据普通类评审报告模板 插入反担保相关数据和保证相关数据(插入形式的)
     * @return
     */
    @Operation(summary = "根据模板生产对应的评审报告(插入形式的)", description = "生产对应的评审报告(插入形式的)")
    @PostMapping("/create/report/template")
    public IbcResponse<List<FileInfo>> createReportTemplate(@IbcId @Parameter(hidden = true) int accountId,@RequestParam("projectId") Integer projectId) throws IOException {
        logger.info("入参 createReportTemplate param:"+ projectId);
        materialService.createReportTemplate(accountId,projectId);
        return IbcResponse.ok();
    }

    /**
     * 根据项目id获取报告是否一致
     * @return
     */
    @Operation(summary = "根据项目id获取报告是否一致", description = "根据项目id获取报告是否一致")
    @GetMapping("/report/project")
    public IbcResponse getReports(@RequestParam("projectId") Integer projectId) {
        materialService.getReports(projectId);
        return IbcResponse.ok();
    }

}
