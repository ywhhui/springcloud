package com.szcgc.project.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.project.constant.ProjectConstants;
import com.szcgc.project.dto.RepayTrailDto;
import com.szcgc.project.service.IRepayTrialService;
import com.szcgc.project.vo.repay.RepayTrialPlanVo;
import com.szcgc.project.vo.repay.RepayTrialVo;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 还款试算
 *
 * @author chenjiaming
 * @date 2022-10-24 09:01:17
 */
@Slf4j
@Api(tags = "还款试算")
@RestController
@RequestMapping("repay-trial")
public class RepayTrialController {

    @Resource
    private IRepayTrialService repayTrialService;

    @Operation(summary = "计算日期")
    @GetMapping("calcDate")
    public IbcResponse<String> calcDate(@RequestParam String startDate, @RequestParam Integer expires, @RequestParam Integer expiresUnit) {
        log.info("计算日期开始,请求参数为:startDate={},expires={},expiresUnit={}", startDate, expires, expiresUnit);
        long start = System.currentTimeMillis();
        try {
            // 期限为空或者为0
            if (ObjectUtil.isNull(expires) || 0 == expires) {
                return IbcResponse.error500("期限不能为空或者为0");
            }
            // 期限单位为空或者不在范围内
            if (ObjectUtil.isNull(expiresUnit) || expiresUnit < ProjectConstants.DURING_UNIT_DAY || expiresUnit > ProjectConstants.DURING_UNIT_YEAR) {
                return IbcResponse.error500("期限单位不能为空或者不在范围内");
            }
            // 开始结束日期为空
            if (StrUtil.isBlank(startDate) || !startDate.matches(Const.Regex.YYYY_MM_DD_REG)) {
                return IbcResponse.error500("开始日期不能为空或者日期格式不正确");
            }
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Const.DateFormat.YYYY_MM_DD);
            LocalDate localDate = LocalDate.parse(startDate, dateTimeFormatter);
            if (ProjectConstants.DURING_UNIT_DAY == expiresUnit) {
                localDate = localDate.plusDays(expires);
            } else if (ProjectConstants.DURING_UNIT_MONTH == expiresUnit) {
                localDate = localDate.plusMonths(expires);
            } else if (ProjectConstants.DURING_UNIT_YEAR == expiresUnit) {
                localDate = localDate.plusYears(expires);
            }
            String res = localDate.format(dateTimeFormatter);
            log.info("计算日期结果为:{}", JsonUtils.toJSONString(res));
            return IbcResponse.ok(res);
        } catch (Exception e) {
            log.error("计算日期异常", e);
            return IbcResponse.error500("计算日期异常");
        } finally {
            log.info("计算日期结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    @Operation(summary = "还款试算")
    @PostMapping("calc")
    public IbcResponse<List<RepayTrialPlanVo>> calc(@RequestBody RepayTrialVo repayTrialVo) {
        log.info("还款试算开始,请求参数为:{}", JsonUtils.toJSONString(repayTrialVo));
        long start = System.currentTimeMillis();
        try {
            // 本金为空或者为0
            if (ObjectUtil.isNull(repayTrialVo.getAmount()) || BigDecimal.ZERO.equals(repayTrialVo.getAmount())) {
                return IbcResponse.error500("本金不能为空或者为0");
            }
            // 利率为空或者为0
            if (ObjectUtil.isNull(repayTrialVo.getRate()) || BigDecimal.ZERO.equals(repayTrialVo.getRate())) {
                return IbcResponse.error500("利率不能为空或者为0");
            }
            // 期限为空或者为0
            if (ObjectUtil.isNull(repayTrialVo.getExpires()) || 0 == repayTrialVo.getExpires()) {
                return IbcResponse.error500("期限不能为空或者为0");
            }
            // 期限单位为空或者为0
            if (ObjectUtil.isNull(repayTrialVo.getExpiresUnit()) || repayTrialVo.getExpiresUnit() < ProjectConstants.DURING_UNIT_DAY
                    || repayTrialVo.getExpiresUnit() > ProjectConstants.DURING_UNIT_YEAR) {
                return IbcResponse.error500("期限单位不能为空或者不在范围内");
            }
            // 开始日期为空
            if (StrUtil.isBlank(repayTrialVo.getStartDate()) || !repayTrialVo.getStartDate().matches(Const.Regex.YYYY_MM_DD_REG)) {
                return IbcResponse.error500("开始日期不能为空或者日期格式不正确");
            }
            // 结束日期为空
            if (StrUtil.isBlank(repayTrialVo.getEndDate()) || !repayTrialVo.getEndDate().matches(Const.Regex.YYYY_MM_DD_REG)) {
                return IbcResponse.error500("结束日期不能为空或者日期格式不正确");
            }
            // 开始大于结束日期
            if (DateUtil.parse(repayTrialVo.getEndDate(), Const.DateFormat.YYYY_MM_DD).getTime() <=
                    DateUtil.parse(repayTrialVo.getStartDate(), Const.DateFormat.YYYY_MM_DD).getTime()) {
                return IbcResponse.error500("开始日期不能大于结束日期");
            }
            // 还本还息方案为空
            if (StrUtil.isBlank(repayTrialVo.getRepayLoan()) || StrUtil.isBlank(repayTrialVo.getRepayInterest())) {
                return IbcResponse.error500("还本还息方案不能为空");
            }

            RepayTrailDto repayTrailDto = new RepayTrailDto();
            BeanUtil.copyProperties(repayTrialVo, repayTrailDto);
            List<RepayTrialPlanVo> repayTrialPlanVos = repayTrialService.calc(repayTrailDto);
            log.info("还款试算结果为:{}", JsonUtils.toJSONString(repayTrialPlanVos));
            return IbcResponse.ok(repayTrialPlanVos);
        } catch (Exception e) {
            log.error("还款试算异常", e);
            return IbcResponse.error500("还款试算异常");
        } finally {
            log.info("还款试算结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    @Operation(summary = "导出还款计划")
    @PostMapping("export")
    public void export(@RequestBody List<RepayTrialPlanVo> list, @ApiIgnore HttpServletResponse response) {
        log.info("导出还款计划开始,请求参数为:{}", JsonUtils.toJSONString(list));
        long start = System.currentTimeMillis();
        try {
            List<String> titleList = Lists.newArrayList(
                    "应还期次", "应还款日", "应还本金（元）", "应还利息（元）", "应还服务费（元）", "应还合计（元）", "剩余本金（元）", "创收金额（元）");

            List<List<String>> valueList = list.stream().map(obj -> Lists.newArrayList(
                    String.valueOf(obj.getPeriod()), obj.getDate(), obj.getShouldRepayCapital().toString(),
                    obj.getShouldRepayAccrual().toString(), obj.getShouldRepayServiceAmount().toString(),
                    obj.getShouldRepayCount().toString(), obj.getResidueCapital().toString(), obj.getCreateIncome().toString()
            )).collect(Collectors.toList());
            repayTrialService.export(titleList, valueList, response);
        } catch (Exception e) {
            log.error("导出还款计划异常", e);
        } finally {
            log.info("导出还款计划结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }
}
