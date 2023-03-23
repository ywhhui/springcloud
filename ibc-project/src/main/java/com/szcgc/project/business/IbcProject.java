package com.szcgc.project.business;

import com.szcgc.account.feign.IAccountClient;
import com.szcgc.account.model.AccountInfo;
import com.szcgc.account.model.DepartmentInfo;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.IbcResult;
import com.szcgc.comm.web.IbcId;
import com.szcgc.customer.constant.DicEnum;
import com.szcgc.customer.feign.CustomerClient;
import com.szcgc.customer.model.CustomerInfo;
import com.szcgc.flowable.feign.IFlowTaskClient;
import com.szcgc.project.constant.ProjectStatusEnum;
import com.szcgc.project.model.PreliminaryInfo;
import com.szcgc.project.model.ProjectInfo;
import com.szcgc.project.model.ProjectSupervisorInfo;
import com.szcgc.project.service.IPreliminaryService;
import com.szcgc.project.service.IProjectService;
import com.szcgc.project.service.IProjectSupervisorService;
import com.szcgc.project.vo.preliminary.SupervisorInVo;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2022/9/30 9:56
 */
@Component
public class IbcProject {

    @Autowired
    IProjectService projectService;

    @Autowired
    IPreliminaryService preliminaryService;

    @Autowired
    IProjectSupervisorService supervisorService;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    IAccountClient accountClient;

    @Autowired
    IFlowTaskClient flowTaskClient;

    /**
     * 受理
     *
     * @param accountId
     * @param preliminary
     * @return
     */
    public IbcResult<String> begin(int accountId, PreliminaryInfo preliminary) {
        if (preliminary.getCustomerId() <= 0) {
            boolean isCorp = DicEnum.CUST_TYPE_COMPANY.getValue() == preliminary.getCate();
            CustomerInfo customer = customerClient.findCust(isCorp ? preliminary.getCustomerCode() : preliminary.getCertifyCode());
            if (customer == null) {
                customer = new CustomerInfo();
                customer.setName(preliminary.getName());
                customer.setCate(preliminary.getCate());
                customer.setIdNo(isCorp ? preliminary.getCustomerCode() : preliminary.getCertifyCode());
                customer.setLegal(isCorp ? preliminary.getLegalPerson() : preliminary.getName());
                customer.setContacts(preliminary.getContactPerson());
                customer.setPhone(preliminary.getContactPhone());
                customer.setLegalNationality(preliminary.getLegalNation());
                customer = customerClient.addCust(customer);
                preliminary.setCustomerId(customer.getId());
            } else {
                if (!customer.getName().equals(preliminary.getName()))
                    return IbcResult.error("客户信息(客户名和证件号)与数据库已有数据不匹配，请确认");
                preliminary.setCustomerId(customer.getId());
            }
        }
        ProjectInfo project = new ProjectInfo();
        BeanUtils.copyProperties(preliminary, project);
        project.setIbcStatus(ProjectStatusEnum.Init);
        project.setCreateBy(accountId);
        project.setCreateAt(LocalDateTime.now());
        projectService.insert(project);
        preliminary.setId(project.getId());
        preliminary.setCreateBy(accountId);
        preliminary.setCreateAt(LocalDateTime.now());
        preliminaryService.insert(preliminary);
        return IbcResult.ok(String.valueOf(project.getId()));
    }

    /**
     * 分配
     *
     * @param accountId
     * @param projectId
     * @param roleAId
     * @param roleBId
     * @param roleCId
     * @return
     * @throws Exception
     */
    public IbcResult<String> assign(int accountId, int projectId, int roleAId, int roleBId, int roleCId) {
        ProjectSupervisorInfo supervisor = new ProjectSupervisorInfo();
        supervisor.setCreateBy(accountId);
        supervisor.setProjectId(projectId);
        supervisor.setRoleAId(roleAId);
        supervisor.setRoleBId(roleBId);
        supervisor.setRoleCId(roleCId);
        AccountInfo roleA = accountClient.findAccount(supervisor.getRoleAId());
        DepartmentInfo department = accountClient.findDepart(roleA.getDepartmentId());
        supervisor.setRoleA(roleA.getRealName());
        supervisor.setDepartmentId(department.getId());
        supervisor.setDepartmentName(department.getName());
        AccountInfo roleB = accountClient.findAccount(supervisor.getRoleBId());
        supervisor.setRoleB(roleB.getRealName());
        if (supervisor.getRoleCId() > 0) {
            AccountInfo roleC = accountClient.findAccount(supervisor.getRoleCId());
            supervisor.setRoleC(roleC.getRealName());
        }
        supervisor.setDealAId(supervisor.getRoleAId());
        supervisor.setDealA(supervisor.getRoleA());
        supervisor.setDealBId(supervisor.getRoleBId());
        supervisor.setDealB(supervisor.getRoleB());
        supervisor.setCreateAt(LocalDateTime.now());
        supervisorService.insert(supervisor);
        return IbcResult.OK();
    }

    /**
     * 跨部门分配
     *
     * @param accountId
     * @return
     * @throws Exception
     */
    public IbcResult<String> assignClaim(int accountId, String taskId, int departmentId) {
        DepartmentInfo department = accountClient.findDepart(departmentId);
        flowTaskClient.assign(taskId, department.getManagerId());
        return IbcResult.OK();
    }
}
