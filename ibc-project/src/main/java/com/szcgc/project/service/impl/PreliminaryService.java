package com.szcgc.project.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.project.model.PreliminaryInfo;
import com.szcgc.project.repository.PreliminaryRepository;
import com.szcgc.project.service.IPreliminaryService;
import org.springframework.stereotype.Service;

/**
 * @author; liaohong
 * @date: 2021/6/18 10:48
 * @description:
 */
@Service
public class PreliminaryService extends BaseService<PreliminaryRepository, PreliminaryInfo, Integer> implements IPreliminaryService {

}
