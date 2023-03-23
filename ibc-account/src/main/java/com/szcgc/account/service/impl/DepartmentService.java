package com.szcgc.account.service.impl;

import com.szcgc.account.model.DepartmentInfo;
import com.szcgc.account.repository.DepartmentRepository;
import com.szcgc.account.service.IDepartmentService;
import com.szcgc.comm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2020/8/17 19:36
 */
@Service
public class DepartmentService  extends BaseService<DepartmentRepository, DepartmentInfo, Integer> implements IDepartmentService {

    @Autowired
    DepartmentRepository repository;

    @Override
    public Optional<DepartmentInfo> find(Integer integer) {
        if (departmentMap == null) {
            init();
        }
        DepartmentInfo info = departmentMap.get(integer);
        if (info != null) {
            return Optional.of(info);
        }
        return repository.findById(Objects.requireNonNull(integer));
    }

    @Override
    public List<DepartmentInfo> findAll() {
        if (departmentList == null) {
            init();
        }
        return departmentList;
    }

    @Override
    public String findName(int departId) {
        if (departId <= 0) {
            return null;
        }
        Optional<DepartmentInfo> optional = find(departId);
        if (optional.isPresent()) {
            return optional.get().getName();
        }
        return null;
    }

    private Map<Integer, DepartmentInfo> departmentMap;
    private List<DepartmentInfo> departmentList;

    @Override
    public void init() {
        departmentList = (List<DepartmentInfo>) repository.findAll();
        departmentMap = departmentList.stream().collect(Collectors.toMap(DepartmentInfo::getId, Function.identity()));
    }
}
