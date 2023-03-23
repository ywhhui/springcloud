package com.szcgc.account.service;

import com.szcgc.account.model.AccountInfo;
import com.szcgc.comm.IbcResult;
import com.szcgc.comm.IbcService;

import java.util.List;

public interface IAccountService extends IbcService<AccountInfo, Integer> {

    /**
     * 所有用户
     *
     * @return
     */
    List<AccountInfo> findAll();

    /**
     * 重置密码
     * @param accountId
     * @param newPwd
     * @return
     */
    IbcResult<String> resetPwd(int accountId, String newPwd);

    /**
     * 修改密码
     *
     * @param accountId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    IbcResult<String> modifyPwd(int accountId, String oldPwd, String newPwd);

    /**
     * 修改密码
     *
     * @param accountId
     * @param newAvatar
     * @return
     */
    IbcResult<String> modifyAvatar(int accountId, String newAvatar);

    /**
     * 更新状态
     * @param accountId
     * @param status
     * @return
     */
    IbcResult<String> updateStatus(int accountId, int status);

    /**
     * 登录
     *
     * @param name
     * @param pwd
     * @return
     */
    AccountInfo login(String name, String pwd);

    /**
     * 根据用户名查询
     * @param name
     * @return
     */
    AccountInfo findByName(String name);

    /**
     *
     * @param phone
     * @return
     */
    AccountInfo findByPhone(String phone);

    /**
     *
     * @param realName
     * @return
     */
    List<AccountInfo> findByRealName(String realName);

    /**
     * 根据部门Id查询
     * @param departmentId
     * @return
     */
    List<AccountInfo> findByDepartmentId(int departmentId);

    String findName(int accountId);

    int findOaId(int accountId);

    int findDepartmentId(int accountId);

    void init();

}
