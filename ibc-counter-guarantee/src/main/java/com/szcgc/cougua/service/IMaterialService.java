package com.szcgc.cougua.service;

import com.szcgc.comm.IbcService;
import com.szcgc.cougua.model.MaterialInfo;
import com.szcgc.cougua.vo.AssessedReportTemplateVo;
import com.szcgc.file.model.FileInfo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @Author liaohong
 * @create 2020/9/1 16:51
 */
public interface IMaterialService extends IbcService<MaterialInfo, Integer> {

    List<MaterialInfo> findByProjectId(Integer projectId);

    List<MaterialInfo> searchByProjectId(Integer projectId);

    MaterialInfo insert(MaterialInfo materialInfo);

    MaterialInfo update(MaterialInfo materialInfo);

    List<FileInfo> templateReportAdd(int accountId, List<AssessedReportTemplateVo> reportTemplateVos) throws IOException;

    Optional<MaterialInfo> find(int id);

    void exportReportTemplate(int accountId, Integer projectId) throws IOException;

    void createReportTemplate(int accountId, Integer projectId) throws IOException;

    void getReports(Integer projectId);
}
