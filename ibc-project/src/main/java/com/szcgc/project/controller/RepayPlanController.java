package com.szcgc.project.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.message.IProjectSender;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.project.constant.ProjectActEnum;
import com.szcgc.project.model.RepayPlan;
import com.szcgc.project.service.IRepayPlanService;
import com.szcgc.project.vo.repay.RepayBaseInfoVo;
import com.szcgc.project.vo.repay.RepayPlanVo;
import com.szcgc.project.vo.repay.RepaySubmitVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 还款计划
 *
 * @author chenjiaming
 * @date 2022-10-24 09:01:17
 */
@Slf4j
@Api(tags = "还款计划")
@RestController
@RequestMapping("repay-plan")
public class RepayPlanController {

    private static final float ONE_HUNDRED = 100;

    @Resource
    private IRepayPlanService repayPlanService;

    @Resource
    private IProjectSender sender;

    @Operation(summary = "获取基本信息")
    @GetMapping("base")
    public IbcResponse<RepayBaseInfoVo> baseInfo(@ApiParam("项目id") Integer projectId, @ApiParam("放款id") Integer loanId) {
        log.info("获取基本信息开始,请求参数为:projectId:{},loanId:{}", projectId, loanId);
        long start = System.currentTimeMillis();
        try {
            RepayBaseInfoVo repayBaseInfoVo = repayPlanService.baseInfo(projectId, loanId);
            log.info("获取基本信息结果为:{}", JsonUtils.toJSONString(repayBaseInfoVo));
            return IbcResponse.ok(repayBaseInfoVo);
        } catch (Exception e) {
            log.error("获取基本信息异常", e);
            return IbcResponse.error500("获取基本信息异常");
        } finally {
            log.info("获取基本信息结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    @Operation(summary = "获取还款计划列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<RepayPlanVo>> list(@ApiParam("项目id") Integer projectId, @ApiParam("放款id") Integer loanId) {
        log.info("获取还款计划列表开始,请求参数为:projectId:{},loanId:{}", projectId, loanId);
        long start = System.currentTimeMillis();
        try {
            List<RepayPlanVo> repayPlanList = repayPlanService.list(projectId, loanId);
            log.info("获取还款计划列表结果集合长度为:{}", repayPlanList.size());
            return IbcResponse.ok(repayPlanList);
        } catch (Exception e) {
            log.error("获取还款计划列表异常", e);
            return IbcResponse.error500("获取还款计划列表异常");
        } finally {
            log.info("获取还款计划列表结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    @Operation(summary = "变更账单")
    @PostMapping("change")
    public IbcResponse change(@ApiParam(hidden = true) @IbcId int accountId, @RequestBody List<RepayPlanVo> repayPlans) {
        log.info("变更账单开始,请求参数为:{}", JsonUtils.toJSONString(repayPlans));
        long start = System.currentTimeMillis();
        try {
            List<RepayPlan> list = tranList(repayPlans, accountId);
            String errorTip = repayPlanService.change(list, accountId);
            if (StrUtil.isNotBlank(errorTip)) {
                log.error(errorTip);
                return IbcResponse.error500(errorTip);
            }
            return IbcResponse.ok();
        } catch (Exception e) {
            log.error("变更账单异常", e);
            return IbcResponse.error500("变更账单计划异常");
        } finally {
            log.info("变更账单结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    @Operation(summary = "删除还款计划")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse delete(@ApiParam(hidden = true) @IbcId int accountId, @RequestParam(name = "id") @ApiParam("id") Integer id) {
        log.info("删除还款计划开始,请求参数为:id={}", id);
        long start = System.currentTimeMillis();
        try {
            String errorTips = repayPlanService.del(id, accountId);
            if (StrUtil.isNotBlank(errorTips)) {
                return IbcResponse.error500(errorTips);
            }
            return IbcResponse.ok();
        } catch (Exception e) {
            log.error("删除还款计划异常", e);
            return IbcResponse.error500("删除还款计划计划异常");
        } finally {
            log.info("删除还款计划结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    @Operation(summary = "还款入账提交")
    @PostMapping("credited")
    public IbcResponse credited(@ApiParam(hidden = true) @IbcId int accountId, @RequestBody RepaySubmitVo repaySubmitVo) {
        log.info("还款入账开始,请求参数为:{}", JsonUtils.toJSONString(repaySubmitVo));
        long start = System.currentTimeMillis();
        try {
            List<RepayPlan> list = tranList(repaySubmitVo.getRepayPlanVoList(), accountId);
            String errorTips = repayPlanService.credited(list);
            if (StrUtil.isNotBlank(errorTips)) {
                log.error(errorTips);
                return IbcResponse.error500(errorTips);
            }
            if (CollectionUtil.isNotEmpty(list) && ObjectUtil.isNotNull(list.get(0))) {
                List<RepayPlanVo> repayPlanVoList = repayPlanService.list(list.get(0).getProjectId(), list.get(0).getLoanId());
                // 查看是否所有还款计划都已经入账,都入账提交流程
                boolean flag = repayPlanVoList.stream().allMatch(RepayPlanVo::getCredited);
                if (flag) {
                    sender.projectAction(accountId, repaySubmitVo.getProjectId(), ProjectActEnum.OG_Enroll.name(), repaySubmitVo.getTaskId());
                }
            }
            return IbcResponse.ok();
        } catch (Exception e) {
            log.error("还款入账异常", e);
            return IbcResponse.error500("还款入账计划异常");
        } finally {
            log.info("还款入账结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    /**
     * 集合转换
     *
     * @param list
     * @param accountId 当前登录用户
     * @return
     */
    private List<RepayPlan> tranList(List<RepayPlanVo> list, int accountId) {
        return list.stream().map(obj -> {
            RepayPlan repayPlan = new RepayPlan();
            BeanUtil.copyProperties(obj, repayPlan);
            repayPlan.setRealRepayAccrual(tranUnit(obj.getRealRepayAccrual()));
            repayPlan.setRealRepayCapital(tranUnit(obj.getRealRepayCapital()));
            repayPlan.setShouldRepayAccrual(tranUnit(obj.getShouldRepayAccrual()));
            repayPlan.setShouldRepayCapital(tranUnit(obj.getShouldRepayCapital()));
            repayPlan.setCreateBy(accountId);
            repayPlan.setUpdateBy(accountId);
            return repayPlan;
        }).collect(Collectors.toList());
    }

    /**
     * 转换单位为分
     *
     * @param num 单位为元的bigDecimal类型数字
     * @return 转换后的对象
     */
    private Long tranUnit(BigDecimal num) {
        if (ObjectUtil.isNull(num)) {
            return 0L;
        }
        BigDecimal val = NumberUtil.mul(num, BigDecimal.valueOf(ONE_HUNDRED));
        return val.longValue();
    }
}
