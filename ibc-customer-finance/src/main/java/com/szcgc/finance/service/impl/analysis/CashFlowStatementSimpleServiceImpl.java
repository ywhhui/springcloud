package com.szcgc.finance.service.impl.analysis;

import com.google.common.collect.Lists;
import com.szcgc.comm.service.BaseService;
import com.szcgc.finance.model.analysis.CashFlowStatementSimple;
import com.szcgc.finance.model.base.CashFlowStatement;
import com.szcgc.finance.repository.analysis.CashFlowStatementSimpleRepository;
import com.szcgc.finance.service.analysis.CashFlowStatementSimpleService;
import com.szcgc.finance.util.CalcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 现金流量简表业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-27 14:39:54
 */
@Slf4j
@Service
public class CashFlowStatementSimpleServiceImpl extends BaseService<CashFlowStatementSimpleRepository, CashFlowStatementSimple, Integer>
        implements CashFlowStatementSimpleService {

    /**
     * 流入量	    流出量	    净额
     * 经营活动产生的现金流量	c04         c09	        c10
     * 投资活动产生的现金流量	c15         c19	        c20
     * 融资活动产生的现金流量	c25         c32	        c33
     * 合  计	            c04+c15+c25 c09+c19+c32 c10+c20+c33
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSimpleTable(List<CashFlowStatement> cashFlowStatements, Integer mainId, Integer accountId) {
        List<CashFlowStatementSimple> list = Lists.newArrayList();
        for (int i = 0; i < 2; i++) {
            CashFlowStatement ca = Optional.ofNullable(cashFlowStatements.get(i)).orElse(CashFlowStatement.builder().build());
            list.add(CashFlowStatementSimple.builder()
                    .mainId(mainId)
                    .date(ca.getDate())
                    .manageIn(CalcUtil.round(ca.getC04()))
                    .manageOut(CalcUtil.round(ca.getC09()))
                    .manageNet(CalcUtil.round(ca.getC10()))
                    .investIn(CalcUtil.round(ca.getC15()))
                    .investOut(CalcUtil.round(ca.getC19()))
                    .investNet(CalcUtil.round(ca.getC20()))
                    .financingIn(CalcUtil.round(ca.getC25()))
                    .financingOut(CalcUtil.round(ca.getC32()))
                    .financingNet(CalcUtil.round(ca.getC33()))
                    .totalIn(CalcUtil.add(ca.getC04(), ca.getC15(), ca.getC25()))
                    .totalOut(CalcUtil.add(ca.getC09(), ca.getC19(), ca.getC32()))
                    .totalNet(CalcUtil.add(ca.getC10(), ca.getC20(), ca.getC33()))
                    .build());
            list.get(i).setCreateBy(accountId);
            list.get(i).setUpdateBy(accountId);
        }
        repository.saveAll(list);
    }

    @Override
    public List<CashFlowStatementSimple> findByMainId(Integer mainId) {
        return repository.findByMainId(mainId);
    }
}
