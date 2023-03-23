package com.szcgc.account.controller;

import com.szcgc.account.constant.AccountRoleEnum;
import com.szcgc.account.model.AccountInfo;
import com.szcgc.account.model.BranchInfo;
import com.szcgc.account.model.DepartmentInfo;
import com.szcgc.account.model.OrganizationInfo;
import com.szcgc.account.service.IAccountService;
import com.szcgc.account.service.IBranchService;
import com.szcgc.account.service.IDepartmentService;
import com.szcgc.account.service.IOrganizationService;
import com.szcgc.comm.IbcPager;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.model.IbcTree;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.szcgc.comm.util.SundryUtils.page;
import static com.szcgc.comm.util.SundryUtils.size;

/**
 * @Author liaohong
 * @create 2022/9/30 16:57
 * 这里面有两种，
 * 一种是按照流程权限(流程角色)
 * 一种是按照集团组织(子公司，部门)
 */
@Api(tags = "组织架构")
@RestController
@RequestMapping("organizes")
public class OrganizeController {

    @Autowired
    IOrganizationService organizationService;

    @Autowired
    IBranchService branchService;

    @Autowired
    IDepartmentService departmentService;

    @Autowired
    IAccountService accountService;

    /**********按角色来的*******************************************************/

    @Operation(summary = "评估师列表")
    @GetMapping("assessor")
    public IbcResponse<List<IbcTree>> assessAccountId() {
        List<OrganizationInfo> infos = organizationService.findByRoleId(AccountRoleEnum.AssetAssessor);
        List<IbcTree> tree = infos.stream().map(item -> IbcTree.of(item.getAccountId(), item.getAccountName())).collect(Collectors.toList());
        return IbcResponse.ok(tree);
    }

    /**********按组织来的*******************************************************/

    @Operation(summary = "分支结构列表")
    @GetMapping("branches" + WebConstants.INDEX)
    public IbcResponse<List<IbcTree>> indexBranch() {
        List<BranchInfo> branches = branchService.findAll();
        List<DepartmentInfo> departments = departmentService.findAll();
        List<IbcTree> vos = branches.stream().map(item -> IbcTree.of(
                item.getId(), item.getName(), departments.stream().filter(d -> d.getBranchId() == item.getId()).map(d -> IbcTree.of(d.getId(), d.getName())).collect(Collectors.toList())
        )).collect(Collectors.toList());
        return IbcResponse.ok(vos);
    }

    @Operation(summary = "项目受理-可分配的A角")
    @GetMapping("role-a")
    public IbcResponse<List<IbcTree>> roleAId(@IbcId @Parameter(hidden = true) int accountId) {
        AccountInfo account = accountService.find(accountId).get();
        //DepartmentInfo depart = departmentService.find(account.getDepartmentId()).get();//增信 中小担特殊处理
        List<AccountInfo> infos = accountService.findByDepartmentId(account.getDepartmentId());
        List<IbcTree> vos = infos.stream().map(item -> IbcTree.of(item.getId(), item.getRealName())).collect(Collectors.toList());
        return IbcResponse.ok(vos);
    }

    @Operation(summary = "项目受理-可分配的B角", description = "本质是某部门用户列表")
    @GetMapping("role-b")
    public IbcResponse<List<IbcTree>> depart(@RequestParam("departmentId") int departmentId) {
        List<AccountInfo> infos = accountService.findByDepartmentId(departmentId);
        List<IbcTree> vos = infos.stream().map(item -> IbcTree.of(item.getId(), item.getRealName())).collect(Collectors.toList());
        return IbcResponse.ok(vos);
    }

}
