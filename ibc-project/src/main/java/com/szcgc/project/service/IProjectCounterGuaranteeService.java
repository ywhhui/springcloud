package com.szcgc.project.service;

import com.szcgc.project.model.ProjectCounterGuaranteeInfo;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/24 10:25
 */
public interface IProjectCounterGuaranteeService extends IProjectSuperFind<ProjectCounterGuaranteeInfo, Integer> {

    int suggestBak(int projectId);

    int check(int accountId, int projectId, List<Integer> ids, int guaranteeing);

    int select(int accountId, int projectId, List<Integer> ids, int guaranteeing);

    int updateGuaranteeing(int projectId, int guaranteeing);

    boolean existCounterGuarantee(int counterGuaranteeId);

    List<ProjectCounterGuaranteeInfo> findNormal(int customerId);

    List<ProjectCounterGuaranteeInfo> findNormal(List<Integer> cgIds);

    List<ProjectCounterGuaranteeInfo> findByIds(List<Integer> pcgIds);

    //List<ProjectCounterGuaranteeInfo> findByCustomerId(int customerId, int guaranteeing);

}
