package com.szcgc.project.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.szcgc.account.feign.IAccountClient;
import com.szcgc.account.model.AccountInfo;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.service.BaseService;
import com.szcgc.project.model.InviteLoanInfo;
import com.szcgc.project.repository.InviteLoanRepository;
import com.szcgc.project.service.IInviteLoanService;
import com.szcgc.project.vo.InviteLoanOpinionVo;
import com.szcgc.project.vo.InviteLoanVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/17 17:58
 */
@Service
public class InviteLoanService extends BaseService<InviteLoanRepository, InviteLoanInfo, Integer> implements IInviteLoanService {

    @Autowired
    IAccountClient iAccountClient;

    @Override
    public IbcResponse<Integer> submit(int accountId, InviteLoanVo inviteLoanVo) {
//        //校验是否为空
//        if (StringUtils.isEmpty(inviteLoanVo.getInviteBankAccount()) || StringUtils.isEmpty(inviteLoanVo.getInviteBankName())) {
//            return IbcResponse.error400("收款开户行或者收款账号不能为空！");
//        }
        //初始化请款的放款信息 包括每步的审批意见
        InviteLoanInfo inviteLoanInfo = new InviteLoanInfo();
        BeanUtils.copyProperties(inviteLoanVo, inviteLoanInfo);
        inviteLoanInfo.setCreateAt(LocalDateTime.now());
        inviteLoanInfo.setCreateBy(accountId);
        repository.save(inviteLoanInfo);
        return IbcResponse.ok();
    }

    @Override
    public List<InviteLoanOpinionVo> findByProjectId(Integer projectId) {
        List<InviteLoanInfo> byProjectId = repository.findByProjectId(projectId);
        List<InviteLoanOpinionVo> result = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(byProjectId)){
            for (InviteLoanInfo inviteLoanInfo:byProjectId) {
                InviteLoanOpinionVo inviteLoanOpinionVo = new InviteLoanOpinionVo();
                BeanUtils.copyProperties(inviteLoanInfo,inviteLoanOpinionVo);
                AccountInfo account = iAccountClient.findAccount(inviteLoanOpinionVo.getCreateBy());
                inviteLoanOpinionVo.setCreateByName(null == account?null:account.getName());
                result.add(inviteLoanOpinionVo);
            }
        }
        return result;
    }

    @Override
    public InviteLoanInfo findFirstByProjectIdOrderByIdDesc(Integer projectId) {
        return repository.findFirstByProjectIdOrderByIdDesc(projectId);
    }
}
