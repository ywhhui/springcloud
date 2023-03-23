package com.szcgc.cougua.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.cougua.model.MaterialSubmitInfo;
import com.szcgc.cougua.repository.MaterialSubmitRepository;
import com.szcgc.cougua.service.IMaterialSubmitService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author liaohong
 * @create 2022/9/17 16:41
 */
@Service
public class MaterialSubmitService extends BaseService<MaterialSubmitRepository, MaterialSubmitInfo, Integer> implements IMaterialSubmitService {

    @Override
    public MaterialSubmitInfo findByProjectId(Integer projectId) {
        MaterialSubmitInfo result = repository.findByProjectId(projectId);
        return result;
    }

    @Override
    public MaterialSubmitInfo insert(MaterialSubmitInfo entity) {
        MaterialSubmitInfo info = repository.save(Objects.requireNonNull(entity));
        return info;
    }
    
}
