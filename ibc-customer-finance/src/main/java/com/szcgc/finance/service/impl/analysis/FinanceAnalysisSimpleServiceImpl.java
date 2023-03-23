package com.szcgc.finance.service.impl.analysis;

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
import com.szcgc.finance.model.analysis.FinanceAnalysisSimple;
import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.model.base.CashFlowStatement;
import com.szcgc.finance.model.base.IncomeStatement;
import com.szcgc.finance.repository.analysis.FinanceAnalysisSimpleRepository;
import com.szcgc.finance.service.analysis.FinanceAnalysisSimpleService;
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
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 财务分析简表业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-27 14:39:54
 */
@Slf4j
@Service
public class FinanceAnalysisSimpleServiceImpl extends BaseService<FinanceAnalysisSimpleRepository, FinanceAnalysisSimple, Integer>
        implements FinanceAnalysisSimpleService {

    /**
     * 编号 公式
     * TA	a2z
     * OF	a0z-a3z
     * NA	a5x-a2i-a2e
     * ALR	a4z/a2z
     * LR	a0z/a3z
     * QR	(a0z-a0t)/a3z
     * LTR	(a5x+a41+a43+a45+a4a)/(a10+a11+a14+a13+a1i)
     * GR	(a31+a33+a35+a3m+a41+a43)/a5x*100
     * CLR	a91/(a2z-a3z-a4j-a5x)*100
     * LRS	1-(a93/a92)
     * AR	b01
     * SIR	b3a/b01*100
     * ART	b01*2/(a0g+上年a0g)
     * TRI	b02*2/（a0t+上年a0t）
     * RNA	b3a*2/（a5x+上年a5x）*100
     * MIG	(b0e+b2a)/b0e
     * NAGR	（a5x-上年a5x）/上年a5x*100
     * GSR	(b01-上年b01)/上年b01绝对值*100
     * PGR	（b3a-上年b3a)/上年b3a绝对值*100
     * PG	b3a-上年b3a
     * CIIL	c04/a3z
     * CITI	c04/a4z
     * CIMI	c04/b01
     * NCIL	c10/a3z
     * NCTI	c10/a4z
     * NCMI	c10/b01
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSimpleTable(List<BalanceSheet> balanceSheets, List<IncomeStatement> incomeStatements, List<CashFlowStatement> cashFlowStatements,
                                  Integer mainId, Integer accountId) {
        List<FinanceAnalysisSimple> list = Lists.newArrayList();
        for (int i = 0, len = balanceSheets.size(); i < len; i++) {
            // 第三列是计算变化(去年-前年)/前年绝对值
            if (i == 2) {
                FinanceAnalysisSimple beforeLast = list.get(0);
                FinanceAnalysisSimple last = list.get(1);
                FinanceAnalysisSimple obj = FinanceAnalysisSimple.builder()
                        .mainId(mainId)
                        .period(i + 1)
                        .build();
                obj.setUpdateBy(accountId);
                obj.setCreateBy(accountId);
                List<FieldDetails> fieldDetailList = ExcelUtil.getExcelFieldList(obj.getClass());
                for (FieldDetails fieldDetails : fieldDetailList) {
                    Field field = fieldDetails.getField();
                    String beforeLastValue = (String) ReflectUtil.getFieldValue(beforeLast, field);
                    String lastValue = (String) ReflectUtil.getFieldValue(last, field);
                    String value;
                    if (Const.Symbol.SLASH.equals(beforeLastValue) || Const.Symbol.SLASH.equals(lastValue)) {
                        value = Const.Symbol.SLASH;
                    } else {
                        beforeLastValue = beforeLastValue.replace(Const.Symbol.PERCENT, "");
                        lastValue = lastValue.replace(Const.Symbol.PERCENT, "");
                        value = CalcUtil.divToPer(NumberUtil.sub(lastValue, beforeLastValue), CalcUtil.abs(beforeLastValue));
                    }
                    ReflectUtil.setFieldValue(obj, field, value);
                }
                list.add(obj);
                continue;
            }
            BalanceSheet ba = Optional.ofNullable(balanceSheets.get(i)).orElse(BalanceSheet.builder().build());
            IncomeStatement in = Optional.ofNullable(incomeStatements.get(i)).orElse(IncomeStatement.builder().build());
            CashFlowStatement ca = Optional.ofNullable(cashFlowStatements.get(i)).orElse(CashFlowStatement.builder().build());

            // 上年资产负债表
            BalanceSheet preBa = i == 0 ? BalanceSheet.builder().build() :
                    Optional.ofNullable(balanceSheets.get(i - 1)).orElse(BalanceSheet.builder().build());
            // 上年损益表
            IncomeStatement preIn = i == 0 ? IncomeStatement.builder().build() :
                    Optional.ofNullable(incomeStatements.get(i - 1)).orElse(IncomeStatement.builder().build());

            list.add(FinanceAnalysisSimple.builder()
                    .mainId(mainId)
                    .date(ba.getDate())
                    .period(i + 1)
                    .ta(CalcUtil.round(ba.getA2z()))
                    .of(CalcUtil.sub(ba.getA0z(), ba.getA3z()))
                    .na(CalcUtil.sub(ba.getA5x(), ba.getA2i(), ba.getA2e()))
                    .alr(CalcUtil.divToPer(ba.getA4z(), ba.getA2z()))
                    .lr(CalcUtil.div(ba.getA0z(), ba.getA3z()))
                    .qr(CalcUtil.div(NumberUtil.sub(ba.getA0z(), ba.getA0t()), ba.getA3z()))
                    .ltr(CalcUtil.div(
                            NumberUtil.add(ba.getA5x(), ba.getA41(), ba.getA43(), ba.getA45(), ba.getA4a()),
                            NumberUtil.add(ba.getA10(), ba.getA11(), ba.getA13(), ba.getA14(), ba.getA1i())
                    ))
                    .gr(CalcUtil.divToPer(
                            NumberUtil.add(ba.getA31(), ba.getA33(), ba.getA35(), ba.getA3m(),
                                    ba.getA41(), ba.getA43()),
                            ba.getA5x()
                    ))
                    .clr(CalcUtil.divToPer(
                            ba.getA91(),
                            NumberUtil.sub(ba.getA2z(), ba.getA3z(), ba.getA4j(), ba.getA5x())
                    ))
                    .lrs(CalcUtil.subToPer(BigDecimal.ONE, CalcUtil.orDiv(ba.getA93(), ba.getA92())))
                    .ar(CalcUtil.round(in.getB01()))
                    .sir(CalcUtil.divToPer(in.getB3a(), in.getB01()))
                    .art(CalcUtil.div(NumberUtil.mul(in.getB01(), CalcUtil.TWO), NumberUtil.add(ba.getA0g(), preBa.getA0g())))
                    .tri(CalcUtil.div(NumberUtil.mul(in.getB02(), CalcUtil.TWO), NumberUtil.add(ba.getA0t(), preBa.getA0t())))
                    .rna(CalcUtil.divToPer(NumberUtil.mul(in.getB3a(), CalcUtil.TWO), NumberUtil.add(ba.getA5x(), preBa.getA5x())))
                    .mig(CalcUtil.div(NumberUtil.add(in.getB0e(), in.getB2a()), in.getB0e()))
                    .nagr(CalcUtil.divToPer(NumberUtil.sub(ba.getA5x(), preBa.getA5x()), preBa.getA5x()))
                    .gsr(CalcUtil.divToPer(NumberUtil.sub(in.getB01(), preIn.getB01()), CalcUtil.abs(preIn.getB01())))
                    .pgr(CalcUtil.divToPer(NumberUtil.sub(in.getB3a(), preIn.getB3a()), CalcUtil.abs(preIn.getB3a())))
                    .pg(CalcUtil.sub(in.getB3a(), preIn.getB3a()))
                    .ciil(CalcUtil.div(ca.getC04(), ba.getA3z()))
                    .citi(CalcUtil.div(ca.getC04(), ba.getA4z()))
                    .cimi(CalcUtil.div(ca.getC04(), in.getB01()))
                    .ncil(CalcUtil.div(ca.getC10(), ba.getA3z()))
                    .ncti(CalcUtil.div(ca.getC10(), ba.getA4z()))
                    .ncmi(CalcUtil.div(ca.getC10(), in.getB01()))
                    .build());
            list.get(i).setCreateBy(accountId);
            list.get(i).setUpdateBy(accountId);
        }
        repository.saveAll(list);
    }

    @Override
    public List<FinanceAnalysisSimple> findByMainId(Integer mainId) {
        return repository.findByMainId(mainId);
    }

    @Override
    public void export(Integer mainId, String path) throws Exception {
        XWPFDocument doc = null;
        File file = null;
        FileOutputStream outputStream = null;
        String key = "$financeAnalysisSimple";
        try {
            doc = new XWPFDocument(new FileInputStream(path));
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.DOCX);
            outputStream = new FileOutputStream(file);

            List<FinanceAnalysisSimple> financeAnalysisSimples = repository.findByMainId(mainId);
            // 转换单位为万
            CalcUtil.tranUnit(financeAnalysisSimples);

            // 在指定key处插入表格
            XWPFTable table = DocUtil.insertTableByKey(doc, key, 10000);
            // 获取表格数据
            List<List<String>> dataList = getTableData(financeAnalysisSimples);

            // 给表格增加数据
            DocUtil.addTableContent(table, dataList);

            // 合并单元格
            DocUtil.mergeCell(table, 1, 10, 0, 0);
            DocUtil.mergeCell(table, 11, 16, 0, 0);
            DocUtil.mergeCell(table, 17, 20, 0, 0);

            // 设置表头背景色
            XWPFTableRow titleRow = DocUtil.getRow(table, 0);
            for (int i = 0, len = dataList.get(0).size(); i < len; i++) {
                DocUtil.getCell(titleRow, i).setColor("CCFFFF");
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
     * @param financeAnalysisSimples 数据集合
     */
    private List<List<String>> getTableData(List<FinanceAnalysisSimple> financeAnalysisSimples) {
        List<Integer> periodList = Lists.newArrayList();
        // 把数据根据期次分别存储
        Map<Integer, FinanceAnalysisSimple> map = Maps.newHashMap();
        for (FinanceAnalysisSimple financeAnalysisSimple : financeAnalysisSimples) {
            Integer period = financeAnalysisSimple.getPeriod();
            if (!map.containsKey(period)) {
                periodList.add(period);
                map.put(period, financeAnalysisSimple);
            }
        }
        periodList.sort(Comparator.comparingInt(Integer::intValue));

        List<List<String>> dataList = Lists.newArrayList();
        List<String> list = Lists.newArrayList();
        list.add("");
        list.add("序号");
        list.add("项目");
        String titleFormat = "%s年%s月";
        // 添加标题
        for (int i = 0, len = periodList.size(); i < len; i++) {
            Integer key = periodList.get(i);
            FinanceAnalysisSimple financeAnalysisSimple = map.get(key);
            if (ObjectUtil.isNull(financeAnalysisSimple) || StrUtil.isBlank(financeAnalysisSimple.getDate())) {
                list.add("");
                continue;
            }
            // 去年同期
            if (DicEnum.SAME_PERIOD_LAST_YEAR.getValue().intValue() == key) {
                list.add("变化% (+ -)");
                continue;
            }
            String[] arr = financeAnalysisSimple.getDate().split(Const.Symbol.SUBTRACT);
            // 今年
            if (DicEnum.THIS_YEAR.getValue().intValue() == key) {
                list.add(String.format(titleFormat, arr[0], arr[1]));
                continue;
            }

            list.add(arr[0].concat("年"));
        }

        dataList.add(list);

        // 添加内容数据
        List<FieldDetails> fieldDetails = ExcelUtil.getExcelFieldList(FinanceAnalysisSimple.class);
        for (int i = 0, len = fieldDetails.size(); i < len; i++) {
            FieldDetails fieldDetail = fieldDetails.get(i);
            List<String> valueList = Lists.newArrayList();
            if (i == 0) {
                valueList.add("偿债\n能力");
            } else if (i == 10) {
                valueList.add("经营\n能力");
            } else if (i == 16) {
                valueList.add("成长\n能力");
            } else {
                valueList.add("");
            }
            valueList.add(String.valueOf(i + 1));
            valueList.add(fieldDetail.getExcelName());
            for (int j = 0, len2 = periodList.size(); j < len2; j++) {
                Integer key = periodList.get(j);

                FinanceAnalysisSimple financeAnalysisSimple = map.get(key);
                if (ObjectUtil.isNull(financeAnalysisSimple)) {
                    valueList.add("");
                    continue;
                }
                valueList.add((String) ReflectUtil.getFieldValue(financeAnalysisSimple, fieldDetail.getField()));
            }

            dataList.add(valueList);

            if ("利润增长额".equals(fieldDetail.getExcelName())) {
                break;
            }
        }
        return dataList;
    }
}
