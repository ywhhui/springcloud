package com.szcgc.project.service;

import com.szcgc.project.dto.RepayTrailDto;
import com.szcgc.project.vo.repay.RepayTrialPlanVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 还款试算
 */
public interface IRepayTrialService {

    /**
     * 还款试算
     *
     * @param repayTrailDto 试算信息
     * @return 试算还款计划
     */
    List<RepayTrialPlanVo> calc(RepayTrailDto repayTrailDto);

    /**
     * 导出还款计划
     * @param titleList 标题集合
     * @param valueList 数据集合
     * @param response 响应对象
     */
    void export(List<String> titleList, List<List<String>> valueList, HttpServletResponse response) throws IOException;
}
