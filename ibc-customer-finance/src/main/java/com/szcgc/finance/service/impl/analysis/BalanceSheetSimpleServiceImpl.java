package com.szcgc.finance.service.impl.analysis;

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
import com.szcgc.finance.model.analysis.BalanceSheetSimple;
import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.repository.analysis.BalanceSheetSimpleRepository;
import com.szcgc.finance.service.analysis.BalanceSheetSimpleService;
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
 * 资产负债简表业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-27 14:39:54
 */
@Slf4j
@Service
public class BalanceSheetSimpleServiceImpl extends BaseService<BalanceSheetSimpleRepository, BalanceSheetSimple, Integer> implements BalanceSheetSimpleService {

    /**
     * 编码 公式
     * CCE	a01
     * STI	a0a+a0c+a0w
     * a0e	a0e
     * a0g	a0g
     * a0i	a0i
     * ONR	a0k+a0m+a0p+a0r
     * FG 	a0t
     * DPE	a0v
     * LTI	a10+a11+a19
     * FA 	a13+a1i+a1k+a1p
     * IA 	a0x+a0y+a12+a1m+a2a+a2c+a2e+a2g+a2i+a2k+a2m+a2p
     * a2z	a2z
     * SLB	a31+a33+a41+a43
     * a35	a35
     * a37	a37
     * a39	a39
     * a3d	a3d
     * OP 	a3b+a3f+a3h+a3j+a3m+a3r+a3t+a3v+a45+a4a+a4c+a4e+a4g
     * PUC	a51
     * CR 	a53
     * SR 	a57+a5b
     * POE	a5j
     * LOE	a5z
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSimpleTable(List<BalanceSheet> balanceSheets, Integer mainId, Integer accountId) {
        List<BalanceSheetSimple> list = Lists.newArrayList();
        for (int i = 0, len = balanceSheets.size(); i < len; i++) {
            BalanceSheet ba = Optional.ofNullable(balanceSheets.get(i)).orElse(BalanceSheet.builder().build());
            list.add(BalanceSheetSimple.builder()
                    .mainId(mainId)
                    .date(ba.getDate())
                    .period(i + 1)
                    .cce(CalcUtil.round(ba.getA01()))
                    .sti(CalcUtil.add(ba.getA0a(), ba.getA0c(), ba.getA0w()))
                    .a0e(CalcUtil.round(ba.getA0e()))
                    .a0g(CalcUtil.round(ba.getA0g()))
                    .a0i(CalcUtil.round(ba.getA0i()))
                    .onr(CalcUtil.add(ba.getA0k(), ba.getA0m(), ba.getA0p(), ba.getA0r()))
                    .fg(CalcUtil.round(ba.getA0t()))
                    .dpe(CalcUtil.round(ba.getA0v()))
                    .lti(CalcUtil.add(ba.getA10(), ba.getA11(), ba.getA19()))
                    .fa(CalcUtil.add(ba.getA13(), ba.getA1i(), ba.getA1k(), ba.getA1p()))
                    .ia(CalcUtil.add(
                            ba.getA0x(), ba.getA0y(), ba.getA12(), ba.getA1m(), ba.getA2a(), ba.getA2c(),
                            ba.getA2e(), ba.getA2g(), ba.getA2i(), ba.getA2k(), ba.getA2m(), ba.getA2p()
                    ))
                    .a2z(CalcUtil.round(ba.getA2z()))
                    .slb(CalcUtil.add(ba.getA31(), ba.getA33(), ba.getA41(), ba.getA43()))
                    .a35(CalcUtil.round(ba.getA35()))
                    .a37(CalcUtil.round(ba.getA37()))
                    .a39(CalcUtil.round(ba.getA39()))
                    .a3d(CalcUtil.round(ba.getA3d()))
                    .op(CalcUtil.add(
                            ba.getA3b(), ba.getA3f(), ba.getA3h(), ba.getA3j(), ba.getA3m(), ba.getA3r(), ba.getA3t(), ba.getA3v(),
                            ba.getA45(), ba.getA4a(), ba.getA4c(), ba.getA4e(), ba.getA4g()
                    ))
                    .puc(CalcUtil.round(ba.getA51()))
                    .cr(CalcUtil.round(ba.getA53()))
                    .sr(CalcUtil.add(ba.getA57(), ba.getA5b()))
                    .poe(CalcUtil.round(ba.getA5j()))
                    .loe(CalcUtil.round(ba.getA5z()))
                    .build());
            list.get(i).setCreateBy(accountId);
            list.get(i).setUpdateBy(accountId);
        }
        repository.saveAll(list);
    }

    @Override
    public List<BalanceSheetSimple> findByMainId(Integer mainId) {
        return repository.findByMainId(mainId);
    }

    @Override
    public void export(Integer mainId, String path) throws Exception {
        XWPFDocument doc = null;
        File file = null;
        FileOutputStream outputStream = null;
        String key = "$balanceSheetSimple";
        try {
            doc = new XWPFDocument(new FileInputStream(path));
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.DOCX);
            outputStream = new FileOutputStream(file);

            List<BalanceSheetSimple> balanceSheetSimples = repository.findByMainId(mainId);
            // 转换单位为万
            CalcUtil.tranUnit(balanceSheetSimples);

            // 在指定key处插入表格
            XWPFTable table = DocUtil.insertTableByKey(doc, key, 11500);
            // 获取表格数据
            List<List<String>> dataList = getTableData(balanceSheetSimples);
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
                    if (i == 0 || j == 0 || j == len2 / 2) {
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
     * 获取表格数据
     *
     * @param balanceSheetSimples 数据集合
     */
    private List<List<String>> getTableData(List<BalanceSheetSimple> balanceSheetSimples) {
        List<Integer> periodList = Lists.newArrayList();
        // 把数据根据期次分别存储
        Map<Integer, BalanceSheetSimple> map = Maps.newHashMap();
        for (BalanceSheetSimple balanceSheetSimple : balanceSheetSimples) {
            Integer period = balanceSheetSimple.getPeriod();
            if (!map.containsKey(period)) {
                periodList.add(period);
                map.put(period, balanceSheetSimple);
            }
        }
        periodList.sort(Comparator.comparingInt(Integer::intValue));
        // 先删除再新增调整顺序
        periodList.remove(DicEnum.SAME_PERIOD_LAST_YEAR.getValue());
        periodList.add(DicEnum.SAME_PERIOD_LAST_YEAR.getValue());

        // 添加标题
        List<List<String>> dataList = Lists.newArrayList();
        List<String> list = Lists.newArrayList();
        String titleFormat = "%s年%s月";
        String titleFormat2 = "%s年同期";
        String titleFormat3 = "%s年";
        for (int i = 0; i < 2; i++) {
            list.add("科目");
            for (int j = 0, len = periodList.size(); j < len; j++) {
                BalanceSheetSimple balanceSheetSimple = map.get(periodList.get(j));
                if (ObjectUtil.isNull(balanceSheetSimple) || StrUtil.isBlank(balanceSheetSimple.getDate())) {
                    list.add("");
                    continue;
                }
                String[] arr = balanceSheetSimple.getDate().split(Const.Symbol.SUBTRACT);
                if (DicEnum.THIS_YEAR.getValue().intValue() == balanceSheetSimple.getPeriod()) {
                    list.add(String.format(titleFormat, arr[0], arr[1]));
                } else if (DicEnum.SAME_PERIOD_LAST_YEAR.getValue().intValue() == balanceSheetSimple.getPeriod()) {
                    list.add(String.format(titleFormat2, arr[0]));
                } else {
                    list.add(String.format(titleFormat3, arr[0]));
                }

            }
        }

        dataList.add(list);

        // 添加内容数据
        List<FieldDetails> fieldDetails = ExcelUtil.getExcelFieldList(BalanceSheetSimple.class);
        for (int i = 0, len = (fieldDetails.size() + 1) / 2; i < len; i++) {
            List<String> valueList = Lists.newArrayList();
            FieldDetails fieldDetails1 = fieldDetails.get(i);
            FieldDetails fieldDetails2 = null;

            if (i < 6) {
                fieldDetails2 = fieldDetails.get(i + (fieldDetails.size() + 1) / 2);
            } else if (i > 6) {
                fieldDetails2 = fieldDetails.get(i + (fieldDetails.size() + 1) / 2 - 1);
            }
            FieldDetails fieldDetail = fieldDetails1;
            for (int k = 0; k < 2; k++) {
                if (k == 1) {
                    fieldDetail = fieldDetails2;
                }
                valueList.add(ObjectUtil.isNull(fieldDetail) ? "" : fieldDetail.getExcelName());
                for (int j = 0, len2 = periodList.size(); j < len2; j++) {
                    Integer key = periodList.get(j);

                    BalanceSheetSimple balanceSheetSimple = map.get(key);
                    if (ObjectUtil.isNull(balanceSheetSimple)) {
                        valueList.add("");
                        continue;
                    }
                    valueList.add(ObjectUtil.isNull(fieldDetail) ? "" : (String) ReflectUtil.getFieldValue(balanceSheetSimple, fieldDetail.getField()));
                }
            }
            dataList.add(valueList);
        }
        return dataList;
    }
}
