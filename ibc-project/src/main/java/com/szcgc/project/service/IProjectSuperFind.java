package com.szcgc.project.service;

import com.szcgc.comm.IbcService;

import java.io.Serializable;
import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/17 17:06
 */
public interface IProjectSuperFind<T, ID extends Serializable> extends IbcService<T, ID> {

    List<T> findByProjectId(int projectId);

    T findLastByProjectId(int projectId);
}
