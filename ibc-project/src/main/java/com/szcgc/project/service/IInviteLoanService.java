package com.szcgc.project.service;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.IbcService;
import com.szcgc.project.model.InviteLoanInfo;
import com.szcgc.project.vo.InviteLoanOpinionVo;
import com.szcgc.project.vo.InviteLoanVo;

import java.util.List;

/**
 * 请款流程信息
 */
public interface IInviteLoanService extends IbcService<InviteLoanInfo, Integer>{

    IbcResponse<Integer> submit(int accountId, InviteLoanVo inviteLoanVo);

    List<InviteLoanOpinionVo> findByProjectId(Integer projectId);

    InviteLoanInfo findFirstByProjectIdOrderByIdDesc(Integer projectId);
}
