package com.szcgc.account.service.impl;

import com.szcgc.account.model.system.SettingInfo;
import com.szcgc.account.repository.SettingRepository;
import com.szcgc.account.service.ISettingService;
import com.szcgc.comm.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2021/3/22 16:21
 */
@Service
public class SettingService extends BaseService<SettingRepository, SettingInfo, Integer> implements ISettingService {

    List<SettingInfo> settings;

    @Override
    public void init() {
        settings = (List<SettingInfo>) repository.findAll();
    }

    @Override
    public List<SettingInfo> findAll() {
        if (settings == null) {
            init();
        }
        return settings;
    }

    @Override
    public List<SettingInfo> findByCate(String cate) {
        if (settings == null) {
            init();
        }
        if (settings != null) {
            return settings.stream().filter(item -> item.getCate().equals(cate)).collect(Collectors.toList());
        }
        return repository.findByCate(Objects.requireNonNull(cate));
    }

}
