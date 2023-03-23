package com.szcgc.finance.service;

import com.szcgc.comm.IbcService;
import com.szcgc.finance.model.ExamineInfo;
import com.szcgc.finance.vo.PostAnalysisVo;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/11/18 17:22
 */
public interface IExamineService extends IbcService<ExamineInfo, Integer> {

    int enroll(int projectId);

    int audit(int projectId, int accountId);

    List<ExamineInfo> findByProjectId(int projectId);

    List<PostAnalysisVo>  examineFinanceDetail(int projectId,String financialReport);

   void examineFinanceAdd(int projectId, String financialReport, int accountId);
}
