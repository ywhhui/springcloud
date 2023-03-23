package com.szcgc.customer.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcPager;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.message.IProjectSender;
import com.szcgc.comm.util.UsccUtil;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.customer.constant.DicEnum;
import com.szcgc.customer.model.CustomerInfo;
import com.szcgc.customer.service.ICustomerService;
import com.szcgc.customer.util.ParamVerifyUtil;
import com.szcgc.customer.vo.CompanyVo;
import com.szcgc.customer.vo.CustSubmitVo;
import com.szcgc.customer.vo.CustomerInfoVo;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.feign.IFileClient;
import com.szcgc.file.model.FileInfo;
import com.szcgc.project.constant.ProjectActEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.szcgc.comm.util.SundryUtils.page;
import static com.szcgc.comm.util.SundryUtils.size;

/**
 * 客户
 *
 * @author chenjiaming
 * @date 2022-9-20 14:45:07
 */
@Slf4j
@Api(tags = "客户")
@RestController
@RequestMapping("cust")
public class CustomerController {

    @Resource
    private ICustomerService customerService;

    @Resource
    private IProjectSender sender;

    @Resource
    private IFileClient fileClient;


    @Operation(summary = "获取客户列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<IbcPager<CustomerInfoVo>> list(@RequestParam(name = WebConstants.PGNM, required = false) Integer pgnum,
                                                      @RequestParam(name = WebConstants.PGSZ, required = false) Integer pgsize,
                                                      @ApiParam("客户名称") String custName,
                                                      @ApiParam("管护人") String custodian) {
        IbcPager<CustomerInfoVo> customerInfos = customerService.list(custName, custodian, page(pgnum), size(pgsize));
        return IbcResponse.ok(customerInfos);
    }

    @Operation(summary = "根据id获取客户信息")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<CustomerInfo> getCustById(@RequestParam @Parameter(description = "客户id", required = true) Integer custId) {
        CustomerInfo customerInfo = customerService.find(custId).orElse(CustomerInfo.builder().build());
        return IbcResponse.ok(customerInfo);
    }

    @Operation(summary = "新增客户信息")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse add(@ApiParam(hidden = true) @IbcId int accountId,
                           @RequestBody @Parameter(description = "客户信息", required = true) CustomerInfo customerInfo) throws Exception {
        String errorTips = ParamVerifyUtil.verify(customerInfo);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        if (DicEnum.CUST_TYPE_PERSON.getValue() == customerInfo.getCate()) {
            if (ObjectUtil.isNull(customerInfo.getCertificateType())) {
                errorTips = "证件类型不能为空";
            }
            if (StrUtil.isNotBlank(errorTips)) {
                return IbcResponse.error500(errorTips);
            }

            if (StrUtil.isNotBlank(customerInfo.getIdNo())) {
                errorTips = UsccUtil.isIdentityCode(customerInfo.getIdNo());
            } else {
                errorTips = "证件号不能为空";
            }

            customerInfo.setLegal(customerInfo.getName());
        }
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        customerInfo.setUpdateBy(accountId);
        customerInfo.setCreateBy(accountId);

        CustomerInfo existsCust = customerService.findByIdNo(customerInfo.getIdNo());
        if (ObjectUtil.isNotNull(existsCust)) {
            customerInfo.setId(existsCust.getId());
        }

        customerService.insert(customerInfo);
        return IbcResponse.ok();
    }

    @Operation(summary = "修改客户")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse edit(@ApiParam(hidden = true) @IbcId int accountId,
                            @RequestBody @Parameter(description = "客户信息", required = true) CustomerInfo customerInfo) throws Exception {
        if (ObjectUtil.isNull(customerInfo.getId())) {
            return IbcResponse.error500("客户id不能为空");
        }
        String errorTips = "";
        if (DicEnum.CUST_TYPE_PERSON.getValue() == customerInfo.getCate()) {
            if (ObjectUtil.isNull(customerInfo.getCertificateType())) {
                errorTips = "证件类型不能为空";
            }
            if (StrUtil.isNotBlank(errorTips)) {
                return IbcResponse.error500(errorTips);
            }

            if (StrUtil.isNotBlank(customerInfo.getIdNo())) {
                errorTips = UsccUtil.isIdentityCode(customerInfo.getIdNo());
            } else {
                errorTips = "证件号不能为空";
            }

            customerInfo.setLegal(customerInfo.getName());
        }
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        customerInfo.setUpdateBy(accountId);
        customerService.update(customerInfo);
        return IbcResponse.ok();
    }

    @Operation(summary = "搜索企业")
    @GetMapping("search")
    public IbcResponse<List<CompanyVo>> search(@RequestParam @ApiParam("关键字") String keyword) {
        List<CompanyVo> companyVoList = customerService.search(keyword);
        return IbcResponse.ok(companyVoList);
    }

    @Operation(summary = "获取大数据企业详情")
    @GetMapping("detail-bigdata")
    public IbcResponse<CustomerInfo> detailBigdata(@RequestParam @ApiParam("企业名称") String name) {
        CustomerInfo customerInfo = customerService.detailBigdata(name);
        return IbcResponse.ok(customerInfo);
    }

    @Operation(summary = "导出数据到评审报告")
    @GetMapping("export-report")
    public IbcResponse exportReport(@ApiParam(hidden = true) @IbcId int accountId,
                                    @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                    @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) throws Exception {

        String path = "D:\\Desktop\\普通类评审报告模板_278347 - 副本.docx";

        customerService.export(accountId, projectId, custId, path);
        return IbcResponse.ok();
    }

    @Operation(summary = "提交评审录入")
    @PostMapping("submit")
    public IbcResponse submit(@ApiParam(hidden = true) @IbcId int accountId, @RequestBody CustSubmitVo submitVo) throws Exception {
        List<FileInfo> fileList = fileClient.findByProject(submitVo.getProjectId(), FileCateEnum.Bg_Ptywpsbg);
        if (CollectionUtil.isEmpty(fileList)) {
            return IbcResponse.error500("未生成评审报告不允许提交,请先生成评审报告!");
        }
        sender.projectAction(accountId, submitVo.getProjectId(), ProjectActEnum.EV_Input.name(), submitVo.getTaskId());
        return IbcResponse.ok();
    }


}
