package com.szcgc.project.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.comm.util.SundryUtils;
import com.szcgc.project.constant.BusinessTypeCateTopEnum;
import com.szcgc.project.constant.BusinessTypeEnum;
import com.szcgc.project.constant.EnterprisesTypeEnum;
import com.szcgc.project.constant.ProjectStatusEnum;
import com.szcgc.project.model.ProjectInfo;
import com.szcgc.project.model.ProjectSupervisorInfo;
import com.szcgc.project.repository.ProjectRepository;
import com.szcgc.project.repository.ProjectSupervisorRepository;
import com.szcgc.project.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectService extends BaseService<ProjectRepository, ProjectInfo, Integer> implements IProjectService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ProjectInfo insert(ProjectInfo entity) {
        Objects.requireNonNull(entity);
        if (entity.getCode() == null) {
            ProjectInfo info = repository.findFirstByCustomerIdOrderByIdDesc(entity.getCustomerId());
            int seriesId = 1;
            int year = LocalDate.now().getYear();
            if (info != null && info.getCode() != null && info.getCode().length() == 13) {
                if (year == Integer.parseInt(info.getCode().substring(6, 10))) {
                    seriesId = Integer.parseInt(info.getCode().substring(10)) + 1;
                }
            }
            //entity.setCode(String.format("%06d_%d_%03d", entity.getCustomerId(), year, seriesId));
            entity.setCode(String.format("%06d%d%03d", entity.getCustomerId(), year, seriesId));
        }
        return repository.save(entity);
    }

    @Override
    public Page<ProjectInfo> findCustomer(int customerId, int pageNo, int pageSize) {
        return repository.findByCustomerId(SundryUtils.requireId(customerId), dftPage(pageNo, pageSize));
    }

    private List<ProjectStatusEnum> statues = Arrays.asList(ProjectStatusEnum.Loan, ProjectStatusEnum.Ongoing, ProjectStatusEnum.Finished);

    @Override
    public Page<ProjectInfo> findCustomer(int customerId, BusinessTypeCateTopEnum top, int pageNo, int pageSize) {
        List<BusinessTypeEnum> types = top.getChildren();
        return repository.findByCustomerId(SundryUtils.requireId(customerId), types, statues, dftPage(pageNo, pageSize));
    }

    @Override
    public Page<ProjectInfo> findByProjectCode(String projectCode, int pageNo, int pageSize) {
        return repository.findByCodeLike("%"+Objects.requireNonNull(projectCode)+"%",dftPage(pageNo,pageSize));
    }

    @Override
    public ProjectStatusEnum findStatus(int projectId) {
        return find(projectId).get().getIbcStatus();
    }

    @Override
    public String findCode(int projectId) {
        if (projectId <= 0) {
            return null;
        }
        Optional<ProjectInfo> optional = find(projectId);
        if (optional.isPresent()) {
            return optional.get().getCode();
        }
        return null;
    }

    @Override
    public int findCustomerId(int projectId) {
        if (projectId <= 0) {
            return 0;
        }
        Optional<ProjectInfo> optional = find(projectId);
        if (optional.isPresent()) {
            return optional.get().getCustomerId();
        }
        return 0;
    }

    @Override
    public long sumAmount(int customerId, ProjectStatusEnum status) {
        return repository.sumAmount(SundryUtils.requireId(customerId), status);
    }

    @Override
    public int updateStatus(int projectId, ProjectStatusEnum status) {
        return repository.updateStatus(SundryUtils.requireId(projectId), status);
    }

    @Override
    public int updateEnterprisesType(int projectId, EnterprisesTypeEnum enterprisesTypeEnum) {
        return repository.updateEnterprisesType(SundryUtils.requireId(projectId), enterprisesTypeEnum);
    }

    @Override
    public int updateManual(int projectId, String currManual) {
        return repository.updateManual(SundryUtils.requireId(projectId), currManual);
    }

    @Override
    public int updateBasic(int projectId, long amount, int during, int duringUnit, BusinessTypeEnum businessType) {
        return repository.updateBasic(SundryUtils.requireId(projectId), amount, during, duringUnit, businessType);
    }

    @Override
    public int updateBasic(int projectId, LocalDate endDate) {
        return repository.updateEndDate(SundryUtils.requireId(projectId), endDate);
    }

    @Override
    public int updateContInfo(int projectId, int contYear, String contNo) {
        return repository.updateContInfo(projectId, contYear, contNo);
    }

    @Override
    public List<ProjectInfo> findAll() {
        return (List<ProjectInfo>) repository.findAll();
    }

    @Override
    public Page<ProjectInfo> findByStatusIn(String code, List<ProjectStatusEnum> status, int pageNo,
                                            int pageSize) {
        if (StringUtils.isEmpty(code)) {
            return repository.findByIbcStatusIn(status, dftPage(pageNo, pageSize));
        }
        return repository.findByCodeLikeAndIbcStatusIn('%' + code + '%', status, dftPage(pageNo, pageSize));
    }

    @Override
    public int findLastContNo(int contYear) {
        ProjectInfo projectInfo = repository.findMaxContNoByContYear(contYear);
        if (projectInfo != null) {
            return projectInfo.getContNoDigital();
        }
        return 0;
    }

}
