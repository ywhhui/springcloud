package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.constant.SupervisorRoleEnum;
import com.szcgc.project.model.ProjectSupervisorInfo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @Author liaohong
 * @create 2020/9/17 17:04
 */
public interface IProjectSupervisorService extends IbcService<ProjectSupervisorInfo, Integer> {

    ProjectSupervisorInfo findByProjectId(int projectId);

//    /**
//     * 已这里面的分配信息去更新数据库的记录
//     *
//     * @param projectId
//     * @param supervisorIds
//     * @return
//     */
//    int update(int projectId, Map<SupervisorRoleEnum, Integer> supervisorIds);
//
//    /**
//     * 插入一条户管信息
//     * //这里面没有去重检验，调用的时候需要注意！
//     * @param projectId
//     * @param accountId
//     * @param role
//     * @return
//     */
//    ProjectSupervisorInfo add(int projectId, int accountId, SupervisorRoleEnum role);
//
//    /**
//     * 查找特定角色的用户
//     * @param projectId
//     * @param role
//     * @return
//     */
//    int findNormal(int projectId, SupervisorRoleEnum role);
//
//    /**
//     * 查看所有角色用户
//     * @param projectId
//     * @return
//     */
//    Map<SupervisorRoleEnum, ProjectSupervisorInfo> findNormal(int projectId);

    /**
     * 我参与的项目
     * @param accountId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<ProjectSupervisorInfo> findByAccountId(int accountId, int pageNo, int pageSize);
}
