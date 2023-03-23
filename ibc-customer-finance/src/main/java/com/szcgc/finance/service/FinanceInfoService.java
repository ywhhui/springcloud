package com.szcgc.finance.service;


import com.szcgc.finance.model.CreditRating;
import com.szcgc.finance.model.analysis.FinanceAnalysis;
import com.szcgc.finance.vo.CodeMapAndListVo;
import com.szcgc.finance.vo.CreditRatingVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 财务管理业务接口
 *
 * @author chenjiaming
 * @date 2022-9-23 15:05:49
 */
public interface FinanceInfoService {

    /**
     * 导入数据
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @param file      文件
     * @param accountId 当前人
     * @param title     标题数组
     * @return 错误提示
     */
    String importData(Integer projectId, Integer custId, MultipartFile file, int accountId, List<String> title) throws Exception;

    /**
     * 财务分析
     *
     * @param id1       前年资产负债表数据id
     * @param id2       去年资产负债表数据id
     * @param id3       去年同期资产负债表数据id
     * @param id4       今年资产负债表数据id
     * @param accountId 当前用户
     */
    void analysis(Integer id1, Integer id2, Integer id3, Integer id4, int accountId);

    /**
     * 根据项目id,客户id获取财务详情
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return caiwu
     */
    FinanceAnalysis detail(Integer projectId, Integer custId);

    /**
     * 导出财务分析
     *
     * @param response  响应对象
     * @param projectId 项目id
     * @param custId    客户id
     */
    void export(HttpServletResponse response, Integer projectId, Integer custId) throws Exception;


    /**
     * 导出数据到评审报告
     *
     * @param accountId 当前用户id
     * @param projectId 项目id
     * @param custId    客户id
     * @param path      模板路径
     * @throws Exception
     */
    void exportReport(Integer accountId, Integer projectId, Integer custId, String path) throws Exception;

    /**
     * 财务原始表字段说明
     *
     * @return
     */
    Map<String, CodeMapAndListVo> financetable() throws Exception;

    /**
     * 获取财务分析简表以及定量分析数据
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 财务分析简表以及定量分析数据
     */
    List<CreditRatingVo> financeAnalysis(Integer projectId, Integer custId);

    /**
     * 获取资信评分数据
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @param date      年月
     * @return 资信评分定性评分数据
     */
    CreditRating qualitative(Integer projectId, Integer custId, String date);

    /**
     * 保存资信评分数据
     *
     * @param creditRating 资信评分数据
     */
    void saveQualitative(CreditRating creditRating);

}
