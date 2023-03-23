package com.szcgc.cougua.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.szcgc.comm.service.BaseService;
import com.szcgc.cougua.model.IndividualInfo;
import com.szcgc.cougua.repository.IndividualRepository;
import com.szcgc.cougua.service.IIndividualService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2022/9/17 16:41
 */
@Service
public class IndividualService extends BaseService<IndividualRepository, IndividualInfo, Integer> implements IIndividualService {

    @Override
    public List<IndividualInfo> findByProjectId(Integer projectId) {
        List<IndividualInfo> result = repository.findByProjectId(projectId);
        if(CollectionUtils.isNotEmpty(result)){
            result = result.stream().sorted(Comparator.comparing(IndividualInfo::getId).reversed()).collect(Collectors.toList());
        }
        return result;
    }

}
