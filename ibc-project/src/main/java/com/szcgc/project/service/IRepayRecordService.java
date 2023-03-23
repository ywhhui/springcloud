package com.szcgc.project.service;

import com.szcgc.project.constant.SubjectEnum;
import com.szcgc.project.model.RepayRecordInfo;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/10/21 11:07
 */
public interface IRepayRecordService extends IProjectSuperFind<RepayRecordInfo, Integer> {

    List<RepayRecordInfo> findByProjectId(int projectId, SubjectEnum subject);

    long sumAmount(int projectId, SubjectEnum subject);

    long sumCustomerAmount(int customerId, SubjectEnum subject);

    //List<Map<String, Object>> findByProjectIdAndMonth(int projectId, String month, SubjectEnum subject);
}
