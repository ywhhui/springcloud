package com.szcgc.customer.service.impl.manufacture;

import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.exception.BaseException;
import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.UsccUtil;
import com.szcgc.customer.model.manufacture.MarketingPattern;
import com.szcgc.customer.repository.manufacture.MarketingPatternRepository;
import com.szcgc.customer.service.manufacture.MarketingPatternService;
import com.szcgc.customer.util.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


/**
 * 营销模式业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-22 16:03:21
 */
@Service
public class MarketingPatternServiceImpl extends BaseService<MarketingPatternRepository, MarketingPattern, Integer> implements MarketingPatternService {

    @Override
    public List<MarketingPattern> list(Integer projectId, Integer custId) {
        return repository.getMarketingPatternByProjectIdAndCustomerId(projectId, custId);
    }

    @Override
    public List<MarketingPattern> importData(MultipartFile file) throws Exception {
        List<MarketingPattern> data = null;
        String errorTips = "";
        File tempFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.XLSX);
        try {
            file.transferTo(tempFile);
            // 校验表头
            errorTips = ExcelUtil.verifyHead(new FileInputStream(tempFile), MarketingPattern.class, true);
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }
            // 获取数据
            data = ExcelUtil.getData(new FileInputStream(tempFile), MarketingPattern.class, true);
            // 校验数据
            errorTips = ExcelUtil.verifyTable(data, true);
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }

            for (int i = 0, len = data.size(); i < len; i++) {
                MarketingPattern obj = data.get(i);
                String err = UsccUtil.checkUscc(obj.getUniqueNo());
                if (StrUtil.isNotBlank(err)) {
                    errorTips = errorTips.concat(String.format("第[%s]行%s\n", i + 3, err));
                }
            }
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }

        } finally {
            tempFile.delete();
        }
        return data;
    }
}
