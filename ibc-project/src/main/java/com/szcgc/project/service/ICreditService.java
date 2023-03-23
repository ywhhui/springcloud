package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.constant.BusinessTypeCateEnum;
import com.szcgc.project.constant.BusinessTypeEnum;
import com.szcgc.project.model.CreditInfo;
import com.szcgc.project.model.CreditRecordInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/25 17:28
 */
public interface ICreditService extends IbcService<CreditInfo, Integer> {

    List<CreditInfo> findByCustomerId(int customerId);

    List<CreditInfo> findByCustomerId(int customerId, BusinessTypeCateEnum cate);

    List<CreditRecordInfo> findRecord(int creditId);

    Page<CreditRecordInfo> findRecord(int creditId, int pageNo, int pageSize);

    int hold(int creditId, int projectId, int customerId, long amount, String remarks);

    boolean checkCredit(int customerId, BusinessTypeEnum businessType);
}
