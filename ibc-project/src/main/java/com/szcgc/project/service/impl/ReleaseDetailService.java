package com.szcgc.project.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.szcgc.comm.service.BaseService;
import com.szcgc.cougua.feign.ICounterGuaranteeClient;
import com.szcgc.cougua.model.MaterialInfo;
import com.szcgc.project.model.ReleaseDetailInfo;
import com.szcgc.project.repository.ReleaseDetailRepository;
import com.szcgc.project.service.IReleaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2020/9/17 17:58
 */
@Service
public class ReleaseDetailService extends BaseService<ReleaseDetailRepository, ReleaseDetailInfo, Integer> implements IReleaseDetailService {

    @Autowired
    ICounterGuaranteeClient iCounterGuaranteeClient;

    @Override
    public List<ReleaseDetailInfo> findByProjectId(Integer projectId) {
        return repository.findByProjectId(projectId);
    }

    @Override
    public boolean releaseGuarantee(int projectId,String releaseType) {
        ReleaseDetailInfo detailInfo= repository.findFirstByProjectIdAndReleaseTypeOrderByIdDesc(projectId, releaseType);
        if(null != detailInfo){
            return true;
        }
        return false;
    }

    @Override
    public void addReleaseDetail(int projectId) {
        //获取项目的待解除反担保物措施
        List<MaterialInfo> materialInfos = iCounterGuaranteeClient.guaranteeList(projectId);
        if(CollectionUtils.isNotEmpty(materialInfos)){
            ReleaseDetailInfo detailInfo = new ReleaseDetailInfo();
            detailInfo.setProjectId(projectId);
            detailInfo.setReleaseType("1");
            String join = String.join(",", materialInfos.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList()));
            detailInfo.setReleaseIds(join);
            repository.save(detailInfo);
        }
        //获取项目的待解除反担保物措施

    }

}
