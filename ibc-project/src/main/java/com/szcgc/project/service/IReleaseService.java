package com.szcgc.project.service;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.IbcService;
import com.szcgc.project.model.ReleaseInfo;
import com.szcgc.project.vo.ReleaseAuditVo;
import com.szcgc.project.vo.ReleaseRegisterVo;
import com.szcgc.project.vo.ReleaseSubmitVo;

/**
 * 解保信息
 */
public interface IReleaseService extends IbcService<ReleaseInfo, Integer>{

    IbcResponse<Integer> registerSubmit(int accountId, ReleaseRegisterVo releaseRegisterVo);

    ReleaseInfo findFirstByProjectIdOrderByIdDesc(int projectId);

    IbcResponse<Integer> auditSubmit(int accountId, ReleaseAuditVo releaseAuditVo);

    IbcResponse<Integer> guaranteeSubmit(int accountId, ReleaseSubmitVo submitVo);

    boolean releaseTodo(int projectId);
}
