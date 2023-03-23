package com.szcgc.account.service.impl;

import com.szcgc.account.model.AccountInfo;
import com.szcgc.account.repository.AccountRepository;
import com.szcgc.account.service.IAccountService;
import com.szcgc.comm.IbcResult;
import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.SundryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountService extends BaseService<AccountRepository, AccountInfo, Integer> implements IAccountService {

    @Override
    public IbcResult<String> resetPwd(int accountId, String newPwd) {
        if (newPwd == null || newPwd.length() < 6) {
            return IbcResult.error("请求参数错误");
        }
        //AccountInfo account = find(accountId).orElseThrow();
        repository.updatePassword(accountId, newPwd);
        clear(accountId);
        return IbcResult.OK();
    }

    @Override
    public AccountInfo update(AccountInfo entity) {
        AccountInfo info = super.update(entity);
        cache(info);
        return info;
    }

    @Override
    public AccountInfo insert(AccountInfo entity) {
        AccountInfo info = super.insert(entity);
        cache(info);
        return info;
    }

    @Override
    public void delete(Integer integer) {
        super.delete(integer);
        clear(integer);
    }

    @Override
    public List<AccountInfo> findAll() {
        return (List<AccountInfo>) repository.findAll();
    }

    @Override
    public IbcResult<String> modifyPwd(int accountId, String oldPwd, String newPwd) {
        if (oldPwd == null || newPwd == null || newPwd.length() < 6) {
            return IbcResult.error("请求参数错误");
        }
        AccountInfo account = repository.findByIdAndPassword(accountId, oldPwd);
        if (account == null) {
            return IbcResult.error("旧密码验证失败");
        }
        repository.updatePassword(accountId, newPwd);
        clear(accountId);
        return IbcResult.OK();
    }

    @Override
    public IbcResult<String> modifyAvatar(int accountId, String newAvatar) {
        if (newAvatar == null || newAvatar.length() < 6) {
            return IbcResult.error("请求参数错误");
        }
        Optional<AccountInfo> optional = repository.findById(accountId);
        if (!optional.isPresent()) {
            return IbcResult.error("未找到对应用户");
        }
        repository.updateAvatar(accountId, newAvatar);
        clear(accountId);
        return IbcResult.OK();
    }

    @Override
    public IbcResult<String> updateStatus(int accountId, int status) {
        if (status != AccountInfo.STATUS_LOCK && status != AccountInfo.STATUS_NORMAL) {
            return IbcResult.error("请求参数错误");
        }
        AccountInfo account = find(accountId).get();
        account.setIbcStatus(status);
        update(account);
        return IbcResult.OK();
    }

    @Override
    public Optional<AccountInfo> find(Integer integer) {
        if (accountMap != null) {
            AccountInfo info = accountMap.get(integer);
            if (info != null) {
                return Optional.of(info);
            }
        }
        Optional<AccountInfo> optional = super.find(integer);
        if (optional.isPresent()) {
            cache(optional.get());
        }
        return optional;
    }

    @Override
    public AccountInfo login(String name, String pwd) {
        return repository.findByNameAndPassword(Objects.requireNonNull(name), Objects.requireNonNull(pwd));
    }

    @Override
    public AccountInfo findByName(String name) {
        return repository.findByName(Objects.requireNonNull(name));
    }

    @Override
    public AccountInfo findByPhone(String phone) {
        return repository.findByPhone(Objects.requireNonNull(phone));
    }

    @Override
    public List<AccountInfo> findByRealName(String realName) {
        return repository.findByRealName(Objects.requireNonNull(realName));
    }

    @Override
    public List<AccountInfo> findByDepartmentId(int departmentId) {
        return repository.findByDepartmentId(SundryUtils.requireId(departmentId));
    }

    @Override
    public String findName(int accountId) {
        if (accountId <= 0) {
            return "Ibc系统";
        }
        Optional<AccountInfo> optional = find(accountId);
        if (optional.isPresent()) {
            return optional.get().getRealName();
        }
        return null;
    }

    @Override
    public int findOaId(int accountId) {
        if (accountId <= 0) {
            return 0;
        }
        Optional<AccountInfo> optional = find(accountId);
        if (optional.isPresent()) {
            return optional.get().getOaId();
        }
        return 0;
    }

    @Override
    public int findDepartmentId(int accountId) {
        if (accountId <= 0) {
            return 0;
        }
        Optional<AccountInfo> optional = find(accountId);
        if (optional.isPresent()) {
            return optional.get().getDepartmentId();
        }
        return 0;
    }

    private Map<Integer, AccountInfo> accountMap;

    @Override
    public void init() {
        List<AccountInfo> infos = repository.findByIbcStatus(AccountInfo.STATUS_NORMAL);
        accountMap = infos.stream().collect(Collectors.toMap(AccountInfo::getId, Function.identity()));
    }

    private void cache(AccountInfo account) {
        if (accountMap !=null) {
            accountMap.put(account.getId(), account);
        }
    }

    private void clear(int accountId) {
        if (accountMap !=null) {
            accountMap.remove(accountId);
        }
    }

}
