package com.szcgc.finance.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.szcgc.comm.service.BaseService;
import com.szcgc.finance.model.CreditRating;
import com.szcgc.finance.repository.CreditRatingRepository;
import com.szcgc.finance.service.CreditRatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 资信评分业务实现类
 *
 * @author chenjiaming
 * @date 2022-10-20 11:02:06
 */
@Slf4j
@Service
public class CreditRatingServiceImpl extends BaseService<CreditRatingRepository, CreditRating, Integer> implements CreditRatingService {


    @Override
    public CreditRating findByProjectIdAndCustIdAndDate(Integer projectId, Integer custId, String date) {
        return repository.findByProjectIdAndCustomerIdAndDate(projectId, custId, date);
    }

    @Override
    public void saveQualitative(CreditRating creditRating) {
        CreditRating existsObj = findByProjectIdAndCustIdAndDate(creditRating.getProjectId(), creditRating.getCustomerId(), creditRating.getDate());
        if (ObjectUtil.isNotNull(existsObj)) {
            creditRating.setId(existsObj.getId());
        }
        insert(creditRating);
    }

    @Override
    public void deleteByProjectIdAndCustId(Integer projectId, Integer customerId) {
        repository.deleteByProjectIdAndCustomerId(projectId, customerId);
    }
}
