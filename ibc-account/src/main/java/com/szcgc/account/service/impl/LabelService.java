package com.szcgc.account.service.impl;

import com.szcgc.account.model.system.LabelInfo;
import com.szcgc.account.repository.LabelRepository;
import com.szcgc.account.service.ILabelService;
import com.szcgc.comm.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author liaohong
 * @create 2020/10/20 16:03
 */
@Service
public class LabelService  extends BaseService<LabelRepository, LabelInfo, Integer> implements ILabelService {

    @Override
    public List<LabelInfo> findByName(String name) {
        return repository.findByName(Objects.requireNonNull(name));
    }

    @Override
    public List<LabelInfo> searchByName(String name) {
        Objects.requireNonNull(name);
        String keys = '%' + name + '%';
        return repository.findByNameLike(keys);
    }
}
