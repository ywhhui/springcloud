package com.szcgc.customer.service.impl.manufacture;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.exception.BaseException;
import com.szcgc.comm.service.BaseService;
import com.szcgc.customer.model.manufacture.ReturnedMoney;
import com.szcgc.customer.repository.manufacture.ReturnedMoneyRepository;
import com.szcgc.customer.service.manufacture.ReturnedMoneyService;
import com.szcgc.customer.util.CalcUtil;
import com.szcgc.customer.util.DocUtil;
import com.szcgc.customer.util.ExcelUtil;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * 回款核实业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-22 16:45:57
 */
@Service
public class ReturnedMoneyServiceImpl extends BaseService<ReturnedMoneyRepository, ReturnedMoney, Integer> implements ReturnedMoneyService {

    @Override
    public List<ReturnedMoney> list(Integer projectId, Integer custId) {
        return repository.getReturnedMoneyByProjectIdAndCustomerId(projectId, custId);
    }

    @Override
    public List<ReturnedMoney> importData(MultipartFile file) throws Exception {
        List<ReturnedMoney> data = null;
        String errorTips = "";
        File tempFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.XLSX);
        try {
            file.transferTo(tempFile);
            // 校验表头
            errorTips = ExcelUtil.verifyHead(new FileInputStream(tempFile), ReturnedMoney.class, true);
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }
            // 获取数据
            data = ExcelUtil.getData(new FileInputStream(tempFile), ReturnedMoney.class, true);
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

    @Override
    public void export(Integer projectId, Integer custId, String path) throws Exception {
        XWPFDocument doc = null;
        File file = null;
        FileOutputStream outputStream = null;
        try {
            doc = new XWPFDocument(new FileInputStream(path));
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.DOCX);
            outputStream = new FileOutputStream(file);
            List<ReturnedMoney> returnedMoneys = repository.getReturnedMoneyByProjectIdAndCustomerId(projectId, custId);

            // 插入数据
            inserInfo(doc, returnedMoneys);

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
     * 把信息插入表格
     *
     * @param doc            文档对象
     * @param returnedMoneys 数据集合
     */
    private void inserInfo(XWPFDocument doc, List<ReturnedMoney> returnedMoneys) {
        String docKey = "$returnedMoney";
        // 在指定key处插入表格
        XWPFParagraph paragraph = DocUtil.insertParagraphByKey(doc, docKey);

        List<String> yearList = Lists.newArrayList();
        // 把数据根据年份分别存储
        Map<String, List<ReturnedMoney>> map = Maps.newHashMap();
        for (ReturnedMoney returnedMoney : returnedMoneys) {
            String year = returnedMoney.getYear();
            if (!map.containsKey(year)) {
                yearList.add(year);
                map.put(year, Lists.newArrayList());
            }
            map.get(year).add(returnedMoney);
        }
        // 年份排序
        yearList.sort(Comparator.comparingInt(Integer::parseInt));

        List<String> oneLevelTitle = Lists.newArrayList("户名", "结算行", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月",
                "10月", "11月", "12月");

        String titleFormat = "(%s)%s年回款情况";
        // 填充数据
        for (int i = 0, len = yearList.size(); i < len; i++) {
            String key = yearList.get(i);
            List<ReturnedMoney> returnedMoneyList = map.get(key);

            // 大标题
            XWPFRun run = paragraph.createRun();
            run.setText(String.format(titleFormat, i + 1, returnedMoneyList.get(0).getYear()));
            run.setBold(true);
            run.setFontSize(12);

            // 在段落下新建一个段落
            XWPFParagraph twoParagraph = DocUtil.newNextParagraph(paragraph);
            XWPFRun twoRun = twoParagraph.createRun();
            twoRun.setText("单位：万元");
            twoRun.setFontSize(9);
            // 居右
            twoParagraph.setAlignment(ParagraphAlignment.RIGHT);

            // 在段落下新建一个段落
            XWPFParagraph threeParagraph = DocUtil.newNextParagraph(twoParagraph);
            XWPFTable table = DocUtil.insertTableToParagraph(threeParagraph, 11500);
            XWPFTableRow oneRow = DocUtil.getRow(table, 0);

            // 从第几行开始
            int startIdx = 1;
            // 设置表格标题
            for (int j = 0, len2 = oneLevelTitle.size(); j < len2; j++) {
                XWPFTableCell oneCell = DocUtil.getCenterCell(oneRow, j);
                oneCell.setText(oneLevelTitle.get(j));
            }

            // 添加表格内容并获取统计对象
            ReturnedMoney count = addContent(returnedMoneyList, startIdx, table);

            // 票据行
            startIdx = startIdx + returnedMoneyList.size();
            XWPFTableRow row = DocUtil.getRow(table, startIdx);
            for (int j = 0, len2 = oneLevelTitle.size(); j < len2; j++) {
                XWPFTableCell cell = DocUtil.getCenterCell(row, j);
                if (j == 0) {
                    cell.setText("票据");
                }
            }
            // 合并票据行
            DocUtil.mergeCell(table, startIdx, startIdx, 0, 1);

            // 合计行
            XWPFTableRow countRow = DocUtil.getRow(table, startIdx + 1);
            DocUtil.getCenterCell(countRow, 0).setText("");
            DocUtil.getCenterCell(countRow, 1).setText("合计");
            DocUtil.getCenterCell(countRow, 2).setText(CalcUtil.round(count.getOne(), 2));
            DocUtil.getCenterCell(countRow, 3).setText(CalcUtil.round(count.getTwo(), 2));
            DocUtil.getCenterCell(countRow, 4).setText(CalcUtil.round(count.getThree(), 2));
            DocUtil.getCenterCell(countRow, 5).setText(CalcUtil.round(count.getFour(), 2));
            DocUtil.getCenterCell(countRow, 6).setText(CalcUtil.round(count.getFive(), 2));
            DocUtil.getCenterCell(countRow, 7).setText(CalcUtil.round(count.getSix(), 2));
            DocUtil.getCenterCell(countRow, 8).setText(CalcUtil.round(count.getSeven(), 2));
            DocUtil.getCenterCell(countRow, 9).setText(CalcUtil.round(count.getEight(), 2));
            DocUtil.getCenterCell(countRow, 10).setText(CalcUtil.round(count.getNight(), 2));
            DocUtil.getCenterCell(countRow, 11).setText(CalcUtil.round(count.getTen(), 2));
            DocUtil.getCenterCell(countRow, 12).setText(CalcUtil.round(count.getEleven(), 2));
            DocUtil.getCenterCell(countRow, 13).setText(CalcUtil.round(count.getTwelve(), 2));

            // 创建新的段落对象
            paragraph = DocUtil.newNextParagraph(threeParagraph);
        }

    }

    /**
     * 添加表格内容并统计
     *
     * @param returnedMoneyList 数据对象
     * @param startIdx          开始行
     * @param table             表格对象
     * @return 统计对象
     */
    public ReturnedMoney addContent(List<ReturnedMoney> returnedMoneyList, Integer startIdx, XWPFTable table) {
        // 统计对象
        ReturnedMoney count = ReturnedMoney.builder()
                .one(BigDecimal.ZERO).two(BigDecimal.ZERO).three(BigDecimal.ZERO).four(BigDecimal.ZERO).five(BigDecimal.ZERO).six(BigDecimal.ZERO)
                .seven(BigDecimal.ZERO).eight(BigDecimal.ZERO).night(BigDecimal.ZERO).ten(BigDecimal.ZERO).eleven(BigDecimal.ZERO).twelve(BigDecimal.ZERO)
                .build();
        // 填充具体数据
        for (int j = 0, len2 = returnedMoneyList.size(); j < len2; j++) {
            ReturnedMoney returnedMoney = returnedMoneyList.get(j);
            XWPFTableRow row = DocUtil.getRow(table, j + startIdx);
            DocUtil.getCenterCell(row, 0).setText(returnedMoney.getAccountName());
            DocUtil.getCenterCell(row, 1).setText(returnedMoney.getSettlementBank());
            DocUtil.getCenterCell(row, 2).setText(CalcUtil.round(returnedMoney.getOne(), 2));
            DocUtil.getCenterCell(row, 3).setText(CalcUtil.round(returnedMoney.getTwo(), 2));
            DocUtil.getCenterCell(row, 4).setText(CalcUtil.round(returnedMoney.getThree(), 2));
            DocUtil.getCenterCell(row, 5).setText(CalcUtil.round(returnedMoney.getFour(), 2));
            DocUtil.getCenterCell(row, 6).setText(CalcUtil.round(returnedMoney.getFive(), 2));
            DocUtil.getCenterCell(row, 7).setText(CalcUtil.round(returnedMoney.getSix(), 2));
            DocUtil.getCenterCell(row, 8).setText(CalcUtil.round(returnedMoney.getSeven(), 2));
            DocUtil.getCenterCell(row, 9).setText(CalcUtil.round(returnedMoney.getEight(), 2));
            DocUtil.getCenterCell(row, 10).setText(CalcUtil.round(returnedMoney.getNight(), 2));
            DocUtil.getCenterCell(row, 11).setText(CalcUtil.round(returnedMoney.getTen(), 2));
            DocUtil.getCenterCell(row, 12).setText(CalcUtil.round(returnedMoney.getEleven(), 2));
            DocUtil.getCenterCell(row, 13).setText(CalcUtil.round(returnedMoney.getTwelve(), 2));

            // 统计
            count.setOne(NumberUtil.add(count.getOne(), returnedMoney.getOne()));
            count.setTwo(NumberUtil.add(count.getTwo(), returnedMoney.getTwo()));
            count.setThree(NumberUtil.add(count.getThree(), returnedMoney.getThree()));
            count.setFour(NumberUtil.add(count.getFour(), returnedMoney.getFour()));
            count.setFive(NumberUtil.add(count.getFive(), returnedMoney.getFive()));
            count.setSix(NumberUtil.add(count.getSix(), returnedMoney.getSix()));
            count.setSeven(NumberUtil.add(count.getSeven(), returnedMoney.getSeven()));
            count.setEight(NumberUtil.add(count.getEight(), returnedMoney.getEight()));
            count.setNight(NumberUtil.add(count.getNight(), returnedMoney.getNight()));
            count.setTen(NumberUtil.add(count.getTen(), returnedMoney.getTen()));
            count.setEleven(NumberUtil.add(count.getEleven(), returnedMoney.getEleven()));
            count.setTwelve(NumberUtil.add(count.getTwelve(), returnedMoney.getTwelve()));
        }
        return count;
    }

}
