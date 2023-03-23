package com.szcgc.finance.service;


import com.szcgc.comm.IbcService;
import com.szcgc.finance.model.CreditRating;

/**
 * 资信评分业务接口
 *
 * @author chenjiaming
 * @date 2022-10-20 11:01:55
 */
public interface CreditRatingService extends IbcService<CreditRating, Integer> {

    /**
     * 根据项目id、客户id、日期获取资信评分数据
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @param date      日期
     * @return 资信评分数据
     */
    CreditRating findByProjectIdAndCustIdAndDate(Integer projectId, Integer custId, String date);

    /**
     * 保存资信评分数据
     *
     * @param creditRating 资信评分数据
     */
    void saveQualitative(CreditRating creditRating);

    /**
     * 根据项目id、客户id删除资信评分数据
     *
     * @param projectId  项目id
     * @param customerId 客户id
     */
    void deleteByProjectIdAndCustId(Integer projectId, Integer customerId);
}
