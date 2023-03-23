package com.szcgc.customer.service.impl.manufacture;

import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.exception.BaseException;
import com.szcgc.comm.service.BaseService;
import com.szcgc.customer.model.manufacture.UsageAmount;
import com.szcgc.customer.repository.manufacture.UsageAmountRepository;
import com.szcgc.customer.service.manufacture.UsageAmountService;
import com.szcgc.customer.util.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


/**
 * 水煤电用量核实业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-22 16:45:57
 */
@Service
public class UsageAmountServiceImpl extends BaseService<UsageAmountRepository, UsageAmount, Integer> implements UsageAmountService {

    @Override
    public List<UsageAmount> list(Integer projectId, Integer custId) {
        return repository.getUsageAmountByProjectIdAndCustomerId(projectId, custId);
    }

    @Override
    public List<UsageAmount> importData(MultipartFile file) throws Exception {
        List<UsageAmount> data = null;
        String errorTips = "";
        File tempFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.XLSX);
        try {
            file.transferTo(tempFile);
            // 校验表头
            errorTips = ExcelUtil.verifyHead(new FileInputStream(tempFile), UsageAmount.class, true);
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }
            // 获取数据
            data = ExcelUtil.getData(new FileInputStream(tempFile), UsageAmount.class, true);
            // 校验数据
            errorTips = ExcelUtil.verifyTable(data, true);
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }

        } finally {
            tempFile.delete();
        }
        return data;
    }

}
