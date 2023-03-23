package com.szcgc.project.service;

import com.szcgc.project.model.AgreementApprovalInfo;

/**
 * @Author liaohong
 * @create 2020/11/20 11:10
 */
public interface IAgreementApprovalService extends IProjectSuperFind<AgreementApprovalInfo, Integer> {

    AgreementApprovalInfo insert(int projectId, int accountId, int auditRst, String auditName, String remarks);
}
