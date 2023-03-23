package com.szcgc.project.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.message.IProjectSender;
import com.szcgc.comm.service.BaseService;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.feign.IFileClient;
import com.szcgc.file.model.FileInfo;
import com.szcgc.project.constant.ProjectActEnum;
import com.szcgc.project.model.ReleaseDetailInfo;
import com.szcgc.project.model.ReleaseInfo;
import com.szcgc.project.repository.ReleaseDetailRepository;
import com.szcgc.project.repository.ReleaseRepository;
import com.szcgc.project.service.IReleaseService;
import com.szcgc.project.vo.ReleaseAuditVo;
import com.szcgc.project.vo.ReleaseRegisterVo;
import com.szcgc.project.vo.ReleaseSubmitVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/17 17:58
 */
@Service
public class ReleaseService extends BaseService<ReleaseRepository, ReleaseInfo, Integer> implements IReleaseService{

    @Autowired
    IProjectSender sender;

    @Autowired
    IFileClient fileClient;

    @Autowired
    ReleaseDetailRepository releaseDetailRepository;

    @Override
    public IbcResponse<Integer> registerSubmit(int accountId, ReleaseRegisterVo releaseRegisterVo) {
        //校验是否上传结清证明
        List<FileInfo> byProject = fileClient.findByProject(releaseRegisterVo.getProjectId(), FileCateEnum.Cl_Hkzms);
        if (CollectionUtils.isEmpty(byProject)) {
            return IbcResponse.error400("请上传还款证明书的结清证明后再提交！");
        }
        //初始化解保金额相关
        ReleaseInfo info = new ReleaseInfo();
        BeanUtils.copyProperties(releaseRegisterVo, info);
        info.setCreateAt(LocalDateTime.now());
        info.setCreateBy(accountId);
        repository.save(info);
        //流程的接口
        sender.projectAction(accountId, releaseRegisterVo.getProjectId(), ProjectActEnum.OG_Enroll_Cert.name(), releaseRegisterVo.getTaskId());
        return IbcResponse.ok();
    }

    @Override
    public ReleaseInfo findFirstByProjectIdOrderByIdDesc(int projectId) {
        return repository.findFirstByProjectIdOrderByIdDesc(projectId);
    }

    @Override
    public IbcResponse<Integer> auditSubmit(int accountId, ReleaseAuditVo releaseAuditVo) {
        //根据项目id获取审核的登记详情
        ReleaseInfo info = repository.findFirstByProjectIdOrderByIdDesc(releaseAuditVo.getProjectId());
        //初始化 审核意见相关
        info.setAuditAt(LocalDateTime.now());
        info.setAuditBy(accountId);
        info.setAuditRst(releaseAuditVo.getAuditRst());
        repository.save(info);
        //流程的接口
        sender.projectAction(accountId, releaseAuditVo.getProjectId(), ProjectActEnum.OG_Audit.name(), releaseAuditVo.getTaskId());
        return IbcResponse.ok();
    }

    @Override
    public IbcResponse<Integer> guaranteeSubmit(int accountId, ReleaseSubmitVo submitVo) {
        ReleaseDetailInfo info = releaseDetailRepository.findFirstByProjectIdAndReleaseTypeOrderByIdDesc(submitVo.getProjectId(),submitVo.getReleaseType());
        //解除抵押物的日期和状态修改
        info.setReleaseDate(LocalDateTime.now());
        info.setReleaseStatus(1);
        releaseDetailRepository.save(info);
        if(submitVo.getReleaseType().equals("1")){
            sender.projectAction(accountId, submitVo.getProjectId(), ProjectActEnum.TM_Absolve.name(), submitVo.getTaskId());
        }else if(submitVo.getReleaseType().equals("2")){
            sender.projectAction(accountId, submitVo.getProjectId(), ProjectActEnum.TM_Back.name(), submitVo.getTaskId());
        }
        return IbcResponse.ok();
    }

    @Override
    public boolean releaseTodo(int projectId) {
        //判断项目的放款方式 是一次性 还是分次
        //授信方式是单笔 一次性的
        //分次的 都有台账数据 每笔台账数据都是结清状态
        return false;
    }
}
