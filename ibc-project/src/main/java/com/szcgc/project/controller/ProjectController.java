package com.szcgc.project.controller;

import com.szcgc.comm.IbcPager;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.project.model.ProjectInfo;
import com.szcgc.project.model.ProjectSupervisorInfo;
import com.szcgc.project.service.IProjectService;
import com.szcgc.project.service.IProjectSupervisorService;
import com.szcgc.project.vo.ProjectDetailVo;
import com.szcgc.project.vo.ProjectSupervisorVo;
import com.szcgc.project.vo.ProjectVo;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.szcgc.comm.util.SundryUtils.page;
import static com.szcgc.comm.util.SundryUtils.size;

/**
 * @Author liaohong
 * @create 2022/9/20 15:59
 */
@Api(tags = "项目基本信息")
@RestController
@RequestMapping("projects")
public class ProjectController {

    @Autowired
    IProjectService projectService;

    @Autowired
    IProjectSupervisorService supervisorService;

//    @Operation(summary = "项目搜索")
//    @GetMapping(value = "search")
//    public IbcResponse<IbcPager<ProjectSimpleVo>> search(@RequestParam(name = "projectCode", required = false) String projectCode,
//                                                         @RequestParam(name = "customer", required = false) String customer,
//                                                         @RequestParam(name = "dept", required = false) Integer dept,
//                                                         @RequestParam(name = "manager", required = false) Integer manager,
//                                                         @RequestParam(name = "status", required = false) String status,
//                                                         @RequestParam(name = WebConstants.PGNM, required = false) Integer page,
//                                                         @RequestParam(name = WebConstants.PGSZ, required = false) Integer size) {
//
//
//        if (!StringUtils.isEmpty(projectCode)) {
//            Page<ProjectInfo> byProjectCode = projectService.findByProjectCode(projectCode, page(page), size(size));
//            return IbcResponse.ok(IbcPager.of(byProjectCode.map(projectInfo -> mapSimple(projectInfo))));
//        } else {
//            ProjectStatusEnum projectStatus = null;
//            if (!StringUtils.isEmpty(status)) {
//                projectStatus = ProjectStatusEnum.valueOf(status);
//            }
//            Page<ProjectInfo> byAdvancedCondition = advSearch.findByAdvancedCondition(customer, dept, manager, projectStatus, page(page), size(size));
//            //Page<ProjectInfo> byAdvancedCondition = projectService.findByAdvancedCondition(customer, dept, manager, status, page, size);
//            return IbcResponse.ok(IbcPager.of(byAdvancedCondition.map(projectInfo -> mapSimple(projectInfo))));
//        }
//    }

    @Operation(summary = "项目列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<IbcPager<ProjectSupervisorVo>> index(
            @RequestParam(name = WebConstants.PGNM, required = false) Integer page,
            @RequestParam(name = WebConstants.PGSZ, required = false) Integer size) {
        Page<ProjectInfo> projects = projectService.findAll(page(page), size(size));
        Page<ProjectSupervisorVo> vos = projects.map(project -> mapSimple(project));
        return IbcResponse.ok(IbcPager.of(vos));
    }

    @Operation(summary = "我的项目列表")
    @GetMapping("account")
    public IbcResponse<IbcPager<ProjectSupervisorVo>> account(@IbcId @Parameter(hidden = true) int accountId,
                                                              @RequestParam(name = WebConstants.PGNM, required = false) Integer page,
                                                              @RequestParam(name = WebConstants.PGSZ, required = false) Integer size) {
        Page<ProjectSupervisorInfo> supervisors = supervisorService.findByAccountId(accountId, page(page), size(size));
        Page<ProjectSupervisorVo> vos = supervisors.map(supervisor -> mapSimple(projectService.find(supervisor.getProjectId()).get()));
        return IbcResponse.ok(IbcPager.of(vos));
    }

    @Operation(summary = "项目基本信息")
    @GetMapping("basic")
    public IbcResponse<ProjectVo> basic(@RequestParam("id") int id) {
        Optional<ProjectInfo> optional = projectService.find(id);
        if (!optional.isPresent()) {
            return IbcResponse.error400("未找到对应信息");
        }
        return IbcResponse.ok(mapBasic(optional.get()));
    }

    @Operation(summary = "项目详细信息")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<ProjectDetailVo> detail(@RequestParam("id") int id) {
        Optional<ProjectInfo> optional = projectService.find(id);
        if (!optional.isPresent()) {
            return IbcResponse.error400("未找到对应信息");
        }
        return IbcResponse.ok(mapDetail(optional.get()));
    }

    private void initProject(ProjectInfo project, ProjectVo vo) {
        //BeanUtils.copyProperties(project, vo);
        vo.copyProject(project);
    }

    private void initProjectSupervisor(ProjectSupervisorVo vo) {
        ProjectSupervisorInfo supervisor = supervisorService.findByProjectId(vo.getId());
        if (supervisor != null) {
            //BeanUtils.copyProperties(supervisor, vo);
            vo.copySupervisor(supervisor);
        }
    }

    private ProjectVo mapBasic(ProjectInfo project) {
        ProjectVo vo = new ProjectVo();
        initProject(project, vo);
        return vo;
    }

    private ProjectSupervisorVo mapSimple(ProjectInfo project) {
        ProjectSupervisorVo vo = new ProjectSupervisorVo();
        initProject(project, vo);
        initProjectSupervisor(vo);
        return vo;
    }

    private ProjectDetailVo mapDetail(ProjectInfo project) {
        ProjectDetailVo vo = new ProjectDetailVo();
        initProject(project, vo);
        initProjectSupervisor(vo);
        return vo;
    }
}
