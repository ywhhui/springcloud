package com.szcgc.cougua.utils;


import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.szcgc.comm.exception.BaseException;
import com.szcgc.cougua.constant.CounterGuaranteeTypePropEnum;
import com.szcgc.cougua.model.IndividualInfo;
import com.szcgc.cougua.vo.AssessedReportTemplateVo;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 根据模板生成word
 */
public class TemplateToWordUtils
{

    /**
     * 随机生成文件名
     */
    public static String makeName() {
        Random random = new Random(System.currentTimeMillis());
        char f = (char) (random.nextInt(26) + 65); // 小写字母
        char e = (char) (random.nextInt(26) + 97); // 大写字母
        int n = random.nextInt(8999) + 1000; // 1000-9999
        if (n % 2 == 0)
            return String.valueOf(f) + e + n;
        return String.valueOf(e) + f + n;
    }

    /**
     * 初始化房地产类型的统计列的数据
     */
    public static void getSumTable(ArrayList<String[]> addList) {
        if(CollectionUtils.isNotEmpty(addList)){
            double sum6 =addList.stream().mapToDouble(e->Double.valueOf(e[5])).sum();
            double sum7 =addList.stream().mapToDouble(e->Double.valueOf(e[6])).sum();
            double sum8 =addList.stream().mapToDouble(e->Double.valueOf(e[7])).sum();
            String[] fdcStrTol = new String[]{"合计", "", "", "","",new BigDecimal(sum6).setScale(2, RoundingMode.HALF_UP).toString(), new BigDecimal(sum7).setScale(2, RoundingMode.HALF_UP).toString(),
                    new BigDecimal(sum8).setScale(2, RoundingMode.HALF_UP).toString(),"",""};
            addList.add(fdcStrTol);
        }
    }

    /**
     * 初始化土地统计列的数据
     */
    public static void getJsqSumTable(ArrayList<String[]> addList) {
        double sum7 =addList.stream().mapToDouble(e->Double.valueOf(e[6])).sum();
        double sum8 =addList.stream().mapToDouble(e->Double.valueOf(e[7])).sum();
        double sum9 =addList.stream().mapToDouble(e->Double.valueOf(e[8])).sum();
        String[] fdcStrTol = new String[]{"合计", "", "", "","","",new BigDecimal(sum7).setScale(2, RoundingMode.HALF_UP).toString(), new BigDecimal(sum8).setScale(2, RoundingMode.HALF_UP).toString(),
                new BigDecimal(sum9).setScale(2, RoundingMode.HALF_UP).toString(),"",""};
        addList.add(fdcStrTol);
    }

    /**
     * 初始化股票统计列的数据
     */
    public static void getStockSumTable(ArrayList<String[]> addList) {
        double sum6 =addList.stream().mapToDouble(e->Double.valueOf(e[5])).sum();
        double sum7 =addList.stream().mapToDouble(e->Double.valueOf(e[6])).sum();
        double sum8 =addList.stream().mapToDouble(e->Double.valueOf(e[7])).sum();
        String[] stockStrTol = new String[]{"合计", "", "", "","",new BigDecimal(sum6).setScale(2, RoundingMode.HALF_UP).toString(), new BigDecimal(sum7).setScale(2, RoundingMode.HALF_UP).toString(),
                new BigDecimal(sum8).setScale(2, RoundingMode.HALF_UP).toString()};
        addList.add(stockStrTol);
    }

    /**
     * 初始化股票段落的数据
     */
    public static void getStockMap(AssessedReportTemplateVo reportTemplateVo, Map<String, String> insertTextMap) {
        insertTextMap.put("home1", reportTemplateVo.getCustomerName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String localDateTimeNowStr = reportTemplateVo.getAssessedDate().format(formatter);
        insertTextMap.put("home11", localDateTimeNowStr);
        insertTextMap.put("home12", reportTemplateVo.getProps().get(0).get(CounterGuaranteeTypePropEnum.gpDiscountNo));
        insertTextMap.put("home13", reportTemplateVo.getProps().get(0).get(CounterGuaranteeTypePropEnum.warrantyDiscountNo));
        insertTextMap.put("home14",reportTemplateVo.getAssessedAccountName());
        insertTextMap.put("home15",localDateTimeNowStr );
    }

    /**
     * 初始化土地段落的数据
     */
    public static void getTdMap(AssessedReportTemplateVo reportTemplateVo, Map<String, String> insertTextMap) {
        insertTextMap.put("home1", reportTemplateVo.getCustomerName());
        insertTextMap.put("home16",reportTemplateVo.getAssessedAccountName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String localDateTimeNowStr = reportTemplateVo.getAssessedDate().format(formatter);
        insertTextMap.put("home17",localDateTimeNowStr );
    }

    /**
     * 初始化房地产抵押 段落的数据
     */
    public static void getFdcMap(AssessedReportTemplateVo reportTemplateVo, Map<String, String> insertTextMap) {
        insertTextMap.put("home1", reportTemplateVo.getCustomerName());
        insertTextMap.put("home19",reportTemplateVo.getAssessedAccountName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String localDateTimeNowStr = reportTemplateVo.getAssessedDate().format(formatter);
        insertTextMap.put("home20",localDateTimeNowStr );
    }

    /**
     * 初始化个人保证段落的数据
     */
    public static void getIndividualMap(List<IndividualInfo> individualInfos, Map<String, String> insertTextMap) {
        if(CollectionUtils.isNotEmpty(individualInfos)){
            List<String> names = individualInfos.stream().map(IndividualInfo::getName).collect(Collectors.toList());
            List<String> coupleNames = individualInfos.stream().map(e->StringUtils.isEmpty(e.getCoupleName())?"":e.getCoupleName()).collect(Collectors.toList());
            insertTextMap.put("home1", String.join(",",names));
            insertTextMap.put("home2",String.join(",",coupleNames));
        }
    }

    /**
     * @param wordValue ${...} 带${}的变量
     * @param map       存储需要替换的数据
     * @return java.lang.String
     * @Description 有${}的值匹配出替换的数据，没有${}就返回原来的数据
     * @author hacah
     * @Date 2021/6/15 16:02
     */
    public static String matchesValue(String wordValue, Map<String, String> map) {
        if(MapUtils.isNotEmpty(map)){
            for (String s : map.keySet()) {
                String s1 = new StringBuilder("${").append(s).append("}").toString();
                if (s1.equals(wordValue)) {
                    wordValue = map.get(s);
                }
            }
        }
        return wordValue;
    }

    /**
     * @return boolean
     * @Description 测试是否包含需要替换的数据
     * @author hacah
     * @Date 2021/6/15 15:30
     */
    public static boolean isReplacement(String text) {
        boolean check = false;
        if (text.contains("$")) {
            check = true;
        }
        return check;
    }

    /**
     * @Description 处理所有文段数据，除了表格
     * @param xwpfDocument
     * @param insertTextMap
     * @author hacah
     * @Date 2021/6/17 10:04
     */
    public static void handleParagraphs(XWPFDocument xwpfDocument, Map<String, String> insertTextMap) {
        for (XWPFParagraph paragraph : xwpfDocument.getParagraphs()) {
            String text = paragraph.getText();
            if (isReplacement(text)) {
                for (XWPFRun run : paragraph.getRuns()) {
                    // 判断带有${}的run
//                     System.out.println(run.text());
                    run.setText(matchesValue(run.text(), insertTextMap), 0);
//                    System.out.println(run.text());
                }
            }
        }

    }

    /**
     * @Description 通过word模板生成word的主方法
     * @param inputStream
     * @param outputStream
     * @param insertTextMap
     * @author hacah
     * @Date 2021/6/17 10:03
     */
    public static void generateWord(InputStream inputStream, OutputStream outputStream, Map<String, String> insertTextMap,ArrayList<String[]> addList,ArrayList<String[]> addTableList) throws IOException {
        //获取docx解析对象
        XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
        // 处理所有文段数据，除了表格
        handleParagraphs(xwpfDocument, insertTextMap);
        // 处理表格数据
        handleTable(xwpfDocument, insertTextMap,addList,addTableList);
        // 写出数据
        xwpfDocument.write(outputStream);
        outputStream.close();
    }

    /**
     * @Description 处理表格数据方法
     * @param xwpfDocument
     * @param insertTextMap
     * @author hacah
     * @Date 2021/6/17 10:04
     */
    public static void handleTable(XWPFDocument xwpfDocument, Map<String, String> insertTextMap,ArrayList<String[]> addList,ArrayList<String[]> addTableList) {
        List<XWPFTable> tables = xwpfDocument.getTables();
        for (XWPFTable table : tables) {
            List<XWPFTableRow> rows = table.getRows();
            if (rows.size() > 1) {
                if (table.getText().contains("&") && CollectionUtils.isNotEmpty(addList)) {
                    // 插入数据
                    for (int i = 1; i < addList.size(); i++) {
                        handleTableBorders(table);
                        XWPFTableRow row = table.createRow();
                    }
                    List<XWPFTableRow> rowList = table.getRows();
                    for (int i = 1; i < rowList.size(); i++) {
                        XWPFTableRow xwpfTableRow = rowList.get(i);
                        List<XWPFTableCell> tableCells = xwpfTableRow.getTableCells();
                        for (int j = 0; j < tableCells.size(); j++) {
                            XWPFTableCell xwpfTableCell = tableCells.get(j);
                            String text = xwpfTableCell.getText();
                            if(text.contains("&")){
                                xwpfTableCell.removeParagraph(0);
                                xwpfTableCell.setText("");
                            }
                            xwpfTableCell.setText(addList.get(i - 1)[j]);
                        }
                    }
                }
                if (table.getText().contains("$") && CollectionUtils.isNotEmpty(addTableList)) {
                    // 插入数据
                    for (int i = 1; i < addTableList.size(); i++) {
                        handleTableBorders(table);
                        XWPFTableRow row = table.createRow();
                    }
                    List<XWPFTableRow> rowList = table.getRows();
                    for (int i = 1; i < rowList.size(); i++) {
                        XWPFTableRow xwpfTableRow = rowList.get(i);
                        List<XWPFTableCell> tableCells = xwpfTableRow.getTableCells();
                        for (int j = 0; j < tableCells.size(); j++) {
                            XWPFTableCell xwpfTableCell = tableCells.get(j);
                            String text = xwpfTableCell.getText();
                            if(text.contains("$")){
                                xwpfTableCell.removeParagraph(0);
                                xwpfTableCell.setText("");
                            }
                            xwpfTableCell.setText(addTableList.get(i - 1)[j]);
                        }
                    }
                }
//                if (isReplacement(table.getText())) {
//                    // 替换数据
//                    for (XWPFTableRow row : rows) {
//                        List<XWPFTableCell> tableCells = row.getTableCells();
//                        for (XWPFTableCell tableCell : tableCells) {
//                            if (isReplacement(tableCell.getText())) {
//                                // 替换数据
//                                List<XWPFParagraph> paragraphs = tableCell.getParagraphs();
//                                for (XWPFParagraph paragraph : paragraphs) {
//                                    List<XWPFRun> runs = paragraph.getRuns();
//                                    for (XWPFRun run : runs) {
//                                        run.setText(matchesValue(tableCell.getText(), insertTextMap), 0);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }



            }
        }
    }

    /**
     * 表格边框实线
     * @param table
     */
    private static void handleTableBorders(XWPFTable table) {
        CTTblBorders borders = table.getCTTbl().getTblPr().addNewTblBorders();
        CTBorder hBorder = borders.addNewInsideH();
        hBorder.setVal(STBorder.Enum.forString("single"));  // 线条类型
        hBorder.setSz(new BigInteger("1")); // 线条大小
        hBorder.setColor("000000"); // 设置颜色

        CTBorder vBorder = borders.addNewInsideV();
        vBorder.setVal(STBorder.Enum.forString("single"));
        vBorder.setSz(new BigInteger("1"));
        vBorder.setColor("000000");

        CTBorder lBorder = borders.addNewLeft();
        lBorder.setVal(STBorder.Enum.forString("single"));
        lBorder.setSz(new BigInteger("1"));
        lBorder.setColor("000000");

        CTBorder rBorder = borders.addNewRight();
        rBorder.setVal(STBorder.Enum.forString("single"));
        rBorder.setSz(new BigInteger("1"));
        rBorder.setColor("000000");

        CTBorder tBorder = borders.addNewTop();
        tBorder.setVal(STBorder.Enum.forString("single"));
        tBorder.setSz(new BigInteger("1"));
        tBorder.setColor("000000");

        CTBorder bBorder = borders.addNewBottom();
        bBorder.setVal(STBorder.Enum.forString("single"));
        bBorder.setSz(new BigInteger("1"));
        bBorder.setColor("000000");
    }

    /**
     * 根据key值在word文档中插入表格
     *
     * @param key   key值
     * @param width 指定表格宽度
     * @return 表格对象
     */
//    public static void insertTableByKey(InputStream inputStream,OutputStream outputStream,String key,Integer width, ArrayList<String[]> addTableList) throws IOException {
//        //获取docx解析对象
//        XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
//        //创建表格
//        XWPFTable tableByKey = createTableByKey(key, width, xwpfDocument);
//        //初始化表格数据 插入房地产
//        handleTableData(tableByKey,addTableList);
//        // 写出数据
//        xwpfDocument.write(outputStream);
//        outputStream.close();
//    }

    /**
     * 初始化单个表格数据
     * @param table
     * @param addList
     */
    public static void handleTableData(XWPFTable table, ArrayList<String[]> addList) {
        if (CollectionUtils.isNotEmpty(addList)) {
            // 增加行
            for (int i = 0; i < addList.size(); i++) {
                //增加实线边框
                handleTableBorders(table);
                table.createRow();
            }
            List<XWPFTableRow> rowList = table.getRows();
            for (int i = 1; i < rowList.size(); i++) {
                XWPFTableRow xwpfTableRow = rowList.get(i);
                List<XWPFTableCell> tableCells = xwpfTableRow.getTableCells();
                for (int j = 0; j < tableCells.size(); j++) {
                    XWPFTableCell xwpfTableCell = tableCells.get(j);
                    xwpfTableCell.setText(addList.get(i-1)[j]);
                }
            }
        }
    }

    /**
     * 根据不同tab的类型 初始化对应的表头
     * @param tableOne
     */
    public static void initTableRow(XWPFTable tableOne, String key) {
        XWPFTableRow tableRowTitle = tableOne.getRow(0);
        if("$guaranteeFdc".equals(key) || "$guaranteeFdcIndividual".equals(key) ){
            tableRowTitle.getCell(0).setText("房地产证号");
            tableRowTitle.addNewTableCell().setText("权利人");
            tableRowTitle.addNewTableCell().setText("地址");
            tableRowTitle.addNewTableCell().setText("面积(㎡)");
            tableRowTitle.addNewTableCell().setText("建成日期");
            tableRowTitle.addNewTableCell().setText("原值");
            tableRowTitle.addNewTableCell().setText("税前评估值");
            tableRowTitle.addNewTableCell().setText("税前担保额");
            tableRowTitle.addNewTableCell().setText("税前评估价（/㎡）");
            tableRowTitle.addNewTableCell().setText("备注");
        }else if("$guaranteeFdcRate".equals(key) || "$guaranteeFdcRateIndividual".equals(key)){
            tableRowTitle.getCell(0).setText("房地产证号");
            tableRowTitle.addNewTableCell().setText("权利人");
            tableRowTitle.addNewTableCell().setText("地址");
            tableRowTitle.addNewTableCell().setText("面积(㎡)");
            tableRowTitle.addNewTableCell().setText("建成日期");
            tableRowTitle.addNewTableCell().setText("原值");
            tableRowTitle.addNewTableCell().setText("税后评估值");
            tableRowTitle.addNewTableCell().setText("税后担保额");
            tableRowTitle.addNewTableCell().setText("税后评估价（/㎡）");
            tableRowTitle.addNewTableCell().setText("备注");
        }
        for (int i = 0, len = tableRowTitle.getTableCells().size(); i < len; i++) {
            tableRowTitle.getCell(i).setColor("CCFFFF");
        }
    }

    /**
     *  创建表格
     * @param key
     * @param width
     * @param xwpfDocument
     * @return
     */
    public static XWPFTable createTableByKey(String key, Integer width, XWPFDocument xwpfDocument) {
        XmlCursor cursor = getCursor(xwpfDocument, key);
        if (null != cursor) {
            // 在指定光标位置插入表格
            XWPFTable table = xwpfDocument.insertNewTbl(cursor);
            CTTblPr tblPr = table.getCTTbl().getTblPr();
            tblPr.getTblW().setType(STTblWidth.DXA);    // 固定值
            tblPr.getTblW().setW(BigInteger.valueOf(width));// 设置宽度

            // 设置表格居中
            CTJc jc = table.getCTTbl().getTblPr().getJc();
            if (jc == null) {
                jc = table.getCTTbl().getTblPr().addNewJc();
            }
            jc.setVal(STJc.CENTER);
            table.getCTTbl().getTblPr().setJc(jc);
            return table;
        }
        return null;
    }

    /**
     * 获取指定key光标对象
     *
     * @param doc 文档对象
     * @param key key值
     * @return 光标对象
     */
    public static XmlCursor getCursor(XWPFDocument doc, String key) {
        if (null == doc) {
            throw new BaseException("获取光标对象失败,文档对象为空");
        }
        if (StringUtils.isBlank(key)) {
            throw new BaseException("获取光标对象失败,关键字为空");
        }
        Iterator<XWPFParagraph> itPara = doc.getParagraphsIterator();
        if (null == itPara) {
            throw new BaseException("获取光标对象失败,段落集合为空");
        }
        while (itPara.hasNext()) {
            // 获取段落
            XWPFParagraph paragraph = itPara.next();
            // 需要添加表格的地方
            if (key.equals(paragraph.getParagraphText())) {
                for (XWPFRun run : paragraph.getRuns()) {
                    run.setText("", 0);
                }
                return paragraph.getCTP().newCursor();
            }

        }
        throw new BaseException(String.format("获取光标对象失败,未在文档中找到key值:[%s]", key));
    }
}