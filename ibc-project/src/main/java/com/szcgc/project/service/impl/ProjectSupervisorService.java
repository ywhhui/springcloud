package com.szcgc.project.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.SundryUtils;
import com.szcgc.project.constant.ProjectConstants;
import com.szcgc.project.model.ProjectSupervisorInfo;
import com.szcgc.project.repository.ProjectSupervisorRepository;
import com.szcgc.project.service.IProjectSupervisorService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @Author liaohong
 * @create 2020/9/17 17:58
 */
@Service
public class ProjectSupervisorService extends BaseService<ProjectSupervisorRepository, ProjectSupervisorInfo, Integer> implements IProjectSupervisorService {

    @Override
    public ProjectSupervisorInfo findByProjectId(int projectId) {
        return repository.findFirstByProjectIdOrderByIdDesc(projectId);
    }

    //    @Override
//    public int findNormal(int projectId, SupervisorRoleEnum role) {
//        ProjectSupervisorInfo info = repository.findByProjectIdAndRoleAndIbcStatus(SundryUtils.requireId(projectId),
//                role, ProjectConstants.SUPERVISOR_STATUS_NML);
//        if (info == null) {
//            return 0;
//        }
//        return info.getAccountId();
//    }
//
//    @Override
//    public ProjectSupervisorInfo findLastByProjectId(int projectId) {
//        return null;
//    }
//
//    @Override
//    public Optional<ProjectSupervisorInfo> find(Integer integer) {
//        return repository.findById(Objects.requireNonNull(integer));
//    }
//
//    @Override
//    public ProjectSupervisorInfo update(ProjectSupervisorInfo entity) {
//        return repository.save(Objects.requireNonNull(entity));
//    }
//
//    @Override
//    public ProjectSupervisorInfo insert(ProjectSupervisorInfo entity) {
//        return repository.save(Objects.requireNonNull(entity));
//    }
//
//    @Override
//    public void delete(Integer integer) {
//        repository.deleteById(Objects.requireNonNull(integer));
//    }
//
//    @Override
//    public Page<ProjectSupervisorInfo> findAll(int pageNo, int pageSize) {
//        return repository.findAll(dftPage(pageNo, pageSize));
//    }
//
//    @Override
//    public int update(int projectId, Map<SupervisorRoleEnum, Integer> supervisorIds) {
//        Map<SupervisorRoleEnum, ProjectSupervisorInfo> map = findNormal(projectId);
//        int count = 0;
//        for (Map.Entry<SupervisorRoleEnum, Integer> entry : supervisorIds.entrySet()) {
//            ProjectSupervisorInfo supervisor = map.remove(entry.getKey());
//            if (supervisor == null) {
//                add(projectId, entry.getValue(), entry.getKey());
//                count++;
//            } else if (supervisor.getAccountId() != entry.getValue()) {
//                disable(supervisor);
//                add(projectId, entry.getValue(), entry.getKey());
//                count += 2;
//            }
//        }
//        if (map.size() > 0) {
//            for (Map.Entry<SupervisorRoleEnum, ProjectSupervisorInfo> entry : map.entrySet()) {
//                disable(entry.getValue());
//                count++;
//            }
//        }
//        return count;
//    }
//
//    @Override
//    public Map<SupervisorRoleEnum, ProjectSupervisorInfo> findNormal(int projectId) {
//        //按角色顺序排序
//        List<ProjectSupervisorInfo> list = findByProjectId(projectId);
//        LinkedHashMap<SupervisorRoleEnum, ProjectSupervisorInfo> map=new LinkedHashMap<>();
//        list.stream().filter(ProjectSupervisorInfo::isEnable).forEach(info->map.put(info.getRole(),info));
//        return map;
//    }
//
//    @Override
//    public List<ProjectSupervisorInfo> findByProjectId(int projectId) {
//        return repository.findByProjectIdOrderByRole(SundryUtils.requireId(projectId));
//    }
//
    @Override
    public Page<ProjectSupervisorInfo> findByAccountId(int accountId, int pageNo, int pageSize) {
        return repository.findByAccountId(SundryUtils.requireId(accountId),
                dftPage(pageNo, pageSize));
    }
//
//    @Override
//    public ProjectSupervisorInfo add(int projectId, int accountId, SupervisorRoleEnum role) {
//        //这里面没有去重检验，调用的时候需要注意！
//        ProjectSupervisorInfo info = new ProjectSupervisorInfo();
//        info.setAccountId(accountId);
//        info.setRole(role);
//        info.setProjectId(projectId);
//        info.setEnable();
//        insert(info);
//        return info;
//    }
//
//    private void disable(ProjectSupervisorInfo info) {
//        info.setDisable();
//        update(info);
//    }

}
