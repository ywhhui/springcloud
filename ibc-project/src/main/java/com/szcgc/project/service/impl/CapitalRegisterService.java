package com.szcgc.project.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.account.feign.IAccountClient;
import com.szcgc.comm.message.IProjectSender;
import com.szcgc.comm.service.BaseService;
import com.szcgc.customer.feign.CustomerClient;
import com.szcgc.customer.model.CustomerInfo;
import com.szcgc.project.constant.ProjectActEnum;
import com.szcgc.project.model.CapitalRegister;
import com.szcgc.project.model.ProjectInfo;
import com.szcgc.project.model.ProjectSupervisorInfo;
import com.szcgc.project.repository.CapitalRegisterRepository;
import com.szcgc.project.repository.ProjectRepository;
import com.szcgc.project.service.ICapitalRegisterService;
import com.szcgc.project.vo.loan.ContractVo;
import com.szcgc.project.vo.loan.LoanApplyInfoVo;
import com.szcgc.project.vo.loan.LoanInfoVo;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CapitalRegisterService extends BaseService<CapitalRegisterRepository, CapitalRegister, Integer> implements ICapitalRegisterService {

    @Resource
    private CustomerClient customerClient;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProjectSupervisorService projectSupervisorService;

    @Resource
    private IAccountClient accountClient;

    @Resource
    private IProjectSender sender;

    @Override
    public LoanApplyInfoVo loanApplyInfo(Integer projectId, Integer loanId) {
        // 项目信息
        ProjectInfo projectInfo = projectRepository.findById(projectId).orElse(new ProjectInfo());
        // 客户信息
        CustomerInfo customerInfo = customerClient.findCustById(projectInfo.getCustomerId()).orElse(new CustomerInfo());
        // 项目经理信息
        ProjectSupervisorInfo supervisorInfo = projectSupervisorService.findByProjectId(projectId);
        // 剩下评审主体、融资金额、期限、期限单位、年利率、业务标签、操作主体、放款方式、还款方案、还息方案未获取,等待相关接口开发完获取

        LoanApplyInfoVo loanApplyInfoVo = LoanApplyInfoVo.builder()
                .projectId(projectInfo.getId())
                .projectCode(projectInfo.getCode())
                .custId(customerInfo.getId())
                .custName(customerInfo.getName())
                .businessType(projectInfo.getBusinessType())
                .roleA(StrUtil.nullToDefault(supervisorInfo.getRoleA(), accountClient.findAccount(supervisorInfo.getRoleAId()).getRealName()))
                .roleARatio(supervisorInfo.getPercentA())
                .roleB(StrUtil.nullToDefault(supervisorInfo.getRoleB(), accountClient.findAccount(supervisorInfo.getRoleBId()).getRealName()))
                .roleBRatio(supervisorInfo.getPercentB())
                .build();

        return loanApplyInfoVo;
    }

    @Override
    public List<LoanInfoVo> loanInfo(Integer loanId) {
        // 等待放款相关接口写完获取数据

        return Lists.newArrayList();
    }

    @Override
    public List<ContractVo> contract(Integer projectId) {
        // 等待合同相关接口写完获取数据

        return Lists.newArrayList();
    }

    @Override
    public CapitalRegister info(Integer projectId) {
        return repository.findTop1ByProjectIdOrderByUpdateAtDesc(projectId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submit(int accountId, int projectId, String taskId, String remark, CapitalRegister capitalRegister) {
        CapitalRegister existsObj = repository.findTop1ByProjectIdOrderByUpdateAtDesc(projectId);
        if (ObjectUtil.isNotNull(existsObj)) {
            capitalRegister.setId(existsObj.getId());
        }
        capitalRegister.setCreateBy(accountId);
        capitalRegister.setUpdateBy(accountId);
        repository.save(capitalRegister);
        sender.projectAction(accountId, projectId, ProjectActEnum.LN_Cjcbdj.name(), "", remark, taskId, "");
    }
}
