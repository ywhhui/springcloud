package com.szcgc.project.service;

import com.szcgc.project.model.AdditionalInfo;

import java.util.List;

/**
 * @author JINLINGXUAN
 * @create 2021-03-23
 */
public interface IAdditionalService extends IProjectSuperFind<AdditionalInfo, Integer> {

    AdditionalInfo add(int accountId, int projectId, String fileName);

    AdditionalInfo addByEvaluate(int accountId, int projectId, String fileName);

    int update(List<AdditionalInfo> files);

    AdditionalInfo findByProjectIdAndFileId(int projectId, int fileId);

    AdditionalInfo findByProjectIdAndFileName(int projectId, String fileName);

}
