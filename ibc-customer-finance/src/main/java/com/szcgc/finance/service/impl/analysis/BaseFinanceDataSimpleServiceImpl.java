package com.szcgc.finance.service.impl.analysis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.service.BaseService;
import com.szcgc.finance.constant.DicEnum;
import com.szcgc.finance.model.FieldDetails;
import com.szcgc.finance.model.analysis.BaseFinanceDataSimple;
import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.model.base.IncomeStatement;
import com.szcgc.finance.repository.analysis.BaseFinanceDataSimpleRepository;
import com.szcgc.finance.service.analysis.BaseFinanceDataSimpleService;
import com.szcgc.finance.util.CalcUtil;
import com.szcgc.finance.util.DocUtil;
import com.szcgc.finance.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 基本财务数据简表业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-27 14:39:54
 */
@Slf4j
@Service
public class BaseFinanceDataSimpleServiceImpl extends BaseService<BaseFinanceDataSimpleRepository, BaseFinanceDataSimple, Integer>
        implements BaseFinanceDataSimpleService {

    /**
     * 编号 公式
     * TA 	a2z
     * NA	a5x-a2i-a2e
     * OI 	b01
     * GP 	b2a
     * NTP	b3a
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSimpleTable(List<BalanceSheet> balanceSheets, List<IncomeStatement> incomeStatements, Integer mainId, Integer accountId) {
        List<BaseFinanceDataSimple> list = Lists.newArrayList();
        for (int i = 0, len = balanceSheets.size(); i < len; i++) {
            BalanceSheet balanceSheet = Optional.ofNullable(balanceSheets.get(i)).orElse(BalanceSheet.builder().build());
            IncomeStatement incomeStatement = Optional.ofNullable(incomeStatements.get(i)).orElse(IncomeStatement.builder().build());
            list.add(BaseFinanceDataSimple.builder()
                    .mainId(mainId)
                    .date(balanceSheet.getDate())
                    .period(i + 1)
                    .ta(CalcUtil.round(balanceSheet.getA2z()))
                    .na(CalcUtil.sub(balanceSheet.getA5x(), balanceSheet.getA2i(), balanceSheet.getA2e()))
                    .oi(CalcUtil.round(incomeStatement.getB01()))
                    .gp(CalcUtil.round(incomeStatement.getB2a()))
                    .ntp(CalcUtil.round(incomeStatement.getB3a()))
                    .build());
            list.get(i).setCreateBy(accountId);
            list.get(i).setUpdateBy(accountId);
        }
        repository.saveAll(list);
    }

    @Override
    public List<BaseFinanceDataSimple> findByMainId(Integer mainId) {
        return repository.findByMainId(mainId);
    }

    @Override
    public void export(Integer mainId, String path) throws Exception {
        XWPFDocument doc = null;
        File file = null;
        FileOutputStream outputStream = null;
        String key = "$baseFinanceDataSimple";
        try {
            doc = new XWPFDocument(new FileInputStream(path));
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.DOCX);
            outputStream = new FileOutputStream(file);
            List<BaseFinanceDataSimple> baseFinanceDataSimples = repository.findByMainId(mainId);
            // 转换单位为万
            CalcUtil.tranUnit(baseFinanceDataSimples);

            if (CollectionUtil.isEmpty(baseFinanceDataSimples)) {
                baseFinanceDataSimples = Lists.newArrayList(BaseFinanceDataSimple.builder().build());
            }

            // 在指定key处插入表格
            XWPFTable table = DocUtil.insertTableByKey(doc, key, 10000);
            // 获取表格数据
            List<List<String>> dataList = getTableData(baseFinanceDataSimples);
            // 给表格增加数据
            DocUtil.addTableContent(table, dataList);

            doc.write(outputStream);

        } finally {
            IoUtil.close(outputStream);
            IoUtil.close(doc);
            try {
                FileUtil.copy(file, new File(path), true);
            } finally {
                FileUtil.del(file);
            }
        }
    }

    /**
     * 插入数据
     *
     * @param baseFinanceDataSimples 数据集合
     */
    private List<List<String>> getTableData(List<BaseFinanceDataSimple> baseFinanceDataSimples) {
        List<Integer> periodList = Lists.newArrayList();
        // 把数据根据期次分别存储
        Map<Integer, BaseFinanceDataSimple> map = Maps.newHashMap();
        for (BaseFinanceDataSimple baseFinanceDataSimple : baseFinanceDataSimples) {
            Integer period = baseFinanceDataSimple.getPeriod();
            if (!map.containsKey(period)) {
                periodList.add(period);
                map.put(period, baseFinanceDataSimple);
            }
        }
        periodList.sort(Comparator.comparingInt(Integer::intValue));
        // 先删除再新增调整顺序
        periodList.remove(DicEnum.SAME_PERIOD_LAST_YEAR.getValue());
        periodList.add(DicEnum.SAME_PERIOD_LAST_YEAR.getValue());

        List<List<String>> dataList = Lists.newArrayList();
        List<String> list = Lists.newArrayList();
        list.add("项目");
        String titleFormat = "%s年%s月";
        String titleFormat2 = "%s年同期";
        // 添加标题
        for (int i = 0, len = periodList.size(); i < len; i++) {
            Integer key = periodList.get(i);
            BaseFinanceDataSimple baseFinanceDataSimple = map.get(key);
            if (ObjectUtil.isNull(baseFinanceDataSimple) || StrUtil.isBlank(baseFinanceDataSimple.getDate())) {
                list.add("");
                continue;
            }
            String[] arr = baseFinanceDataSimple.getDate().split(Const.Symbol.SUBTRACT);
            // 去年同期
            if (DicEnum.SAME_PERIOD_LAST_YEAR.getValue().intValue() == key) {
                list.add(String.format(titleFormat2, arr[0]));
                continue;
            }
            // 今年
            if (DicEnum.THIS_YEAR.getValue().intValue() == key) {
                list.add(String.format(titleFormat, arr[0], arr[1]));
                continue;
            }

            list.add(arr[0].concat("年"));
        }

        dataList.add(list);

        // 添加内容数据
        List<FieldDetails> fieldDetails = ExcelUtil.getExcelFieldList(BaseFinanceDataSimple.class);
        for (FieldDetails fieldDetail : fieldDetails) {
            List<String> valueList = Lists.newArrayList();
            valueList.add(fieldDetail.getExcelName());
            for (int i = 0, len = periodList.size(); i < len; i++) {
                Integer key = periodList.get(i);

                BaseFinanceDataSimple baseFinanceDataSimple = map.get(key);
                if (ObjectUtil.isNull(baseFinanceDataSimple)) {
                    valueList.add("");
                    continue;
                }
                valueList.add((String) ReflectUtil.getFieldValue(baseFinanceDataSimple, fieldDetail.getField()));
            }
            dataList.add(valueList);
        }
        return dataList;

    }

}
