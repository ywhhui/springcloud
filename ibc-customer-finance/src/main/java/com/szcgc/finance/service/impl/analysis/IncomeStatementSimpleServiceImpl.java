package com.szcgc.finance.service.impl.analysis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.service.BaseService;
import com.szcgc.finance.constant.DicEnum;
import com.szcgc.finance.model.FieldDetails;
import com.szcgc.finance.model.analysis.IncomeStatementSimple;
import com.szcgc.finance.model.base.IncomeStatement;
import com.szcgc.finance.repository.analysis.IncomeStatementSimpleRepository;
import com.szcgc.finance.service.analysis.IncomeStatementSimpleService;
import com.szcgc.finance.util.CalcUtil;
import com.szcgc.finance.util.DocUtil;
import com.szcgc.finance.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
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
 * 损益简表业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-27 14:39:54
 */
@Slf4j
@Service
public class IncomeStatementSimpleServiceImpl extends BaseService<IncomeStatementSimpleRepository, IncomeStatementSimple, Integer>
        implements IncomeStatementSimpleService {

    /**
     * 编码 公式
     * b01	b01
     * C&T	b02+b03
     * PC 	b0a+b0c+b0e+b0g
     * OOP	b1e
     * NOI	b1c-b1i
     * II 	b0i+b0k
     * IT 	b2c
     * NeP	b3a
     * GPR	(b01-b02)/b01
     * NIR	b3a/b01
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSimpleTable(List<IncomeStatement> incomeStatements, Integer mainId, Integer accountId) {
        List<IncomeStatementSimple> list = Lists.newArrayList();
        for (int i = 0, len = incomeStatements.size(); i < len; i++) {
            IncomeStatement in = Optional.ofNullable(incomeStatements.get(i)).orElse(IncomeStatement.builder().build());
            list.add(IncomeStatementSimple.builder()
                    .mainId(mainId)
                    .date(in.getDate())
                    .period(i + 1)
                    .b01(CalcUtil.round(in.getB01()))
                    .ct(CalcUtil.add(in.getB02(), in.getB03()))
                    .pc(CalcUtil.add(in.getB0a(), in.getB0c(), in.getB0e(), in.getB0g()))
                    .oop(CalcUtil.round(in.getB1e()))
                    .noi(CalcUtil.sub(in.getB1c(), in.getB1i()))
                    .ii(CalcUtil.add(in.getB0i(), in.getB0k()))
                    .it(CalcUtil.round(in.getB2c()))
                    .nep(CalcUtil.round(in.getB3a()))
                    .gpr(CalcUtil.divToPer(NumberUtil.sub(in.getB01(), in.getB02()), in.getB01()))
                    .nir(CalcUtil.divToPer(in.getB3a(), in.getB01()))
                    .build());
            list.get(i).setCreateBy(accountId);
            list.get(i).setUpdateBy(accountId);
        }
        repository.saveAll(list);
    }

    @Override
    public List<IncomeStatementSimple> findByMainId(Integer mainId) {
        return repository.findByMainId(mainId);
    }

    @Override
    public void export(Integer mainId, String path) throws Exception {
        XWPFDocument doc = null;
        File file = null;
        FileOutputStream outputStream = null;
        String key = "$incomeStatementSimple";
        try {
            doc = new XWPFDocument(new FileInputStream(path));
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.DOCX);
            outputStream = new FileOutputStream(file);

            List<IncomeStatementSimple> incomeStatementSimples = repository.findByMainId(mainId);
            if (CollectionUtil.isEmpty(incomeStatementSimples)) {
                incomeStatementSimples = Lists.newArrayList(IncomeStatementSimple.builder().build());
            }
            // 转换单位为万
            CalcUtil.tranUnit(incomeStatementSimples);

            // 在指定key处插入表格
            XWPFTable table = DocUtil.insertTableByKey(doc, key, 10000);
            // 获取表格数据
            List<List<String>> dataList = getTableData(incomeStatementSimples);
            // 给表格增加数据
            DocUtil.addTableContent(table, dataList);

            // 设置表头背景色
            XWPFTableRow titleRow = DocUtil.getRow(table, 0);
            for (int i = 0, len = dataList.get(0).size(); i < len; i++) {
                DocUtil.getCell(titleRow, i).setColor("CCFFFF");
            }

            // 设置字体加粗
            for (int i = 0, len = dataList.size(); i < len; i++) {
                XWPFTableRow row = DocUtil.getRow(table, i);
                List<String> valueList = dataList.get(i);

                for (int j = 0, len2 = valueList.size(); j < len2; j++) {
                    XWPFTableCell cell = DocUtil.getCell(row, j);
                    XWPFRun run = cell.getParagraphs().get(0).getRuns().get(0);
                    if (i == 0 || j == 0) {
                        run.setBold(true);
                    }
                }
            }

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
     * @param incomeStatementSimples 数据集合
     */
    private List<List<String>> getTableData(List<IncomeStatementSimple> incomeStatementSimples) {
        List<Integer> periodList = Lists.newArrayList();
        // 把数据根据期次分别存储
        Map<Integer, IncomeStatementSimple> map = Maps.newHashMap();
        for (IncomeStatementSimple incomeStatementSimple : incomeStatementSimples) {
            Integer period = incomeStatementSimple.getPeriod();
            if (!map.containsKey(period)) {
                periodList.add(period);
                map.put(period, incomeStatementSimple);
            }
        }
        periodList.sort(Comparator.comparingInt(Integer::intValue));
        // 先删除再新增调整顺序
        periodList.remove(DicEnum.SAME_PERIOD_LAST_YEAR.getValue());
        periodList.add(DicEnum.SAME_PERIOD_LAST_YEAR.getValue());

        List<List<String>> dataList = Lists.newArrayList();
        List<String> list = Lists.newArrayList();
        list.add("科目");
        String titleFormat = "%s年%s月";
        String titleFormat2 = "%s年同期";
        String titleFormat3 = "%s年";
        // 添加标题
        for (int i = 0, len = periodList.size(); i < len; i++) {
            Integer key = periodList.get(i);
            IncomeStatementSimple incomeStatementSimple = map.get(key);
            if (ObjectUtil.isNull(incomeStatementSimple) || StrUtil.isBlank(incomeStatementSimple.getDate())) {
                list.add("");
                continue;
            }
            String[] arr = incomeStatementSimple.getDate().split(Const.Symbol.SUBTRACT);
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

            list.add(String.format(titleFormat3, arr[0]));
        }

        dataList.add(list);

        // 添加内容数据
        List<FieldDetails> fieldDetails = ExcelUtil.getExcelFieldList(IncomeStatementSimple.class);
        for (FieldDetails fieldDetail : fieldDetails) {
            List<String> valueList = Lists.newArrayList();
            valueList.add(fieldDetail.getExcelName());
            for (int i = 0, len = periodList.size(); i < len; i++) {
                Integer key = periodList.get(i);

                IncomeStatementSimple incomeStatementSimple = map.get(key);
                if (ObjectUtil.isNull(incomeStatementSimple)) {
                    valueList.add("");
                    continue;
                }
                valueList.add((String) ReflectUtil.getFieldValue(incomeStatementSimple, fieldDetail.getField()));
            }
            dataList.add(valueList);
        }
        return dataList;

    }
}
