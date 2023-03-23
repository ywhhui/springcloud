package com.szcgc.finance.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.exception.BaseException;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

/**
 * word工具类
 *
 * @author chenjiaming
 * @date 2022-10-8 11:34:39
 */
public class DocUtil {


    /**
     * 根据key值在word文档中插入表格
     *
     * @param doc word对象
     * @param key key值
     * @return 表格对象
     */
    public static XWPFTable insertTableByKey(XWPFDocument doc, String key) {
        return insertTableByKey(doc, key, 9600);
    }

    /**
     * 根据key值在word文档中插入表格
     *
     * @param doc   word对象
     * @param key   key值
     * @param width 指定表格宽度
     * @return 表格对象
     */
    public static XWPFTable insertTableByKey(XWPFDocument doc, String key, Integer width) {
        XmlCursor cursor = getCursor(doc, key);
        if (ObjectUtil.isNull(cursor)) {
            return null;
        }
        return insertTableByCursor(doc, cursor, width);
    }

    /**
     * 在指定光标对象处插入表格
     *
     * @param doc    word对象
     * @param cursor 光标对象
     * @return 表格对象
     */
    public static XWPFTable insertTableByCursor(XWPFDocument doc, XmlCursor cursor) {
        return insertTableByCursor(doc, cursor, 9600);
    }

    /**
     * 在指定光标对象处插入表格
     *
     * @param doc    word对象
     * @param cursor 光标对象
     * @param width  指定表格宽度
     * @return 表格对象
     */
    public static XWPFTable insertTableByCursor(XWPFDocument doc, XmlCursor cursor, Integer width) {
        // 在指定光标位置插入表格
        XWPFTable table = doc.insertNewTbl(cursor);

        CTTblPr tablePr = table.getCTTbl().getTblPr();
        CTTblWidth ctTblWidth = tablePr.addNewTblW();
        ctTblWidth.setW(BigInteger.valueOf(width));

        // 设置表格居中
        CTJc jc = table.getCTTbl().getTblPr().getJc();
        if (jc == null) {
            jc = table.getCTTbl().getTblPr().addNewJc();
        }
        jc.setVal(STJc.CENTER);
        table.getCTTbl().getTblPr().setJc(jc);

        return table;
    }

    /**
     * 根据key值在word文档中插入段落
     *
     * @param doc word对象
     * @param key key值
     * @return 段落对象
     */
    public static XWPFParagraph insertParagraphByKey(XWPFDocument doc, String key) {
        XmlCursor cursor = getCursor(doc, key);
        if (ObjectUtil.isNull(cursor)) {
            return null;
        }
        return doc.insertNewParagraph(cursor);
    }


    /**
     * 根据key值在word文档中插入段落
     *
     * @param paragraph 段落对象
     * @return 段落对象
     */
    public static XWPFParagraph newNextParagraph(XWPFParagraph paragraph) {
        // 获取光标
        XmlCursor cursor = paragraph.getCTP().newCursor();
        // 光标移动到下一段落
        cursor.toNextSibling();
        // 创建新段落
        return paragraph.getDocument().insertNewParagraph(cursor);
    }

    /**
     * 获取指定key光标对象
     *
     * @param doc 文档对象
     * @param key key值
     * @return 光标对象
     */
    public static XmlCursor getCursor(XWPFDocument doc, String key) {
        if (ObjectUtil.isNull(doc)) {
            throw new BaseException("获取光标对象失败,文档对象为空");
        }
        if (StrUtil.isBlank(key)) {
            throw new BaseException("获取光标对象失败,关键字为空");
        }

        Iterator<XWPFParagraph> itPara = doc.getParagraphsIterator();
        if (CollectionUtil.isEmpty(itPara)) {
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

    /**
     * 合并单元格
     *
     * @param table         表格对象
     * @param beginRowIndex 开始行
     * @param endRowIndex   结束行
     * @param beginColIndex 开始列
     * @param endColIndex   结束列
     */
    public static void mergeCell(XWPFTable table, int beginRowIndex, int endRowIndex, int beginColIndex, int endColIndex) {
        if (ObjectUtil.isNull(table) || beginRowIndex > endRowIndex || beginColIndex > endColIndex) {
            return;
        }
        CTVMerge startMerge = CTVMerge.Factory.newInstance();
        startMerge.setVal(STMerge.RESTART);
        CTVMerge endMerge = CTVMerge.Factory.newInstance();
        endMerge.setVal(STMerge.CONTINUE);

        CTHMerge start = CTHMerge.Factory.newInstance();
        start.setVal(STMerge.RESTART);
        CTHMerge end = CTHMerge.Factory.newInstance();
        endMerge.setVal(STMerge.CONTINUE);
        for (int i = beginRowIndex; i <= endRowIndex; i++) {
            for (int j = beginColIndex; j <= endColIndex; j++) {
                XWPFTableCell cell = table.getRow(i).getCell(j);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                cell.getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
                CTTc ctTc = cell.getCTTc();
                CTTcPr ctTcPr = ctTc.addNewTcPr();
                if (i == beginRowIndex && j == beginColIndex) {
                    ctTcPr.setHMerge(start);
                    ctTcPr.setVMerge(startMerge);
                    continue;
                }
                ctTcPr.setHMerge(end);
                ctTcPr.setVMerge(endMerge);
            }
        }
    }

    /**
     * 获取行
     *
     * @param table  表格对象
     * @param rowIdx 行下标
     * @return 行对象
     */
    public static XWPFTableRow getRow(XWPFTable table, int rowIdx) {
        if (ObjectUtil.isNull(table)) {
            throw new BaseException("获取行对象失败,表格对象为空");
        }
        XWPFTableRow row = table.getRow(rowIdx);
        if (ObjectUtil.isNotNull(row)) {
            return row;
        }
        while (ObjectUtil.isNull(row)) {
            table.createRow();
            row = table.getRow(rowIdx);
        }
        return row;
    }

    /**
     * 获取居中格式单元格
     *
     * @param row    行对象
     * @param colIdx 列下标
     * @return 单元格对象
     */
    public static XWPFTableCell getCenterCell(XWPFTableRow row, int colIdx) {
        XWPFTableCell cell = getCell(row, colIdx);
        centerStyle(cell);
        return cell;
    }

    /**
     * 获取单元格
     *
     * @param row    行对象
     * @param colIdx 列下标
     * @return 单元格对象
     */
    public static XWPFTableCell getCell(XWPFTableRow row, int colIdx) {
        if (ObjectUtil.isNull(row)) {
            throw new BaseException("获取单元格失败,行对象为空");
        }
        XWPFTableCell cell = row.getCell(colIdx);
        if (ObjectUtil.isNotNull(cell)) {
            return cell;
        }
        while (ObjectUtil.isNull(cell)) {
            row.createCell();
            cell = row.getCell(colIdx);
        }
        return cell;
    }

    /**
     * 设置单元格样式居中
     *
     * @param cell 单元格
     */
    public static void centerStyle(XWPFTableCell cell) {
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        cell.getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
    }

    /**
     * 设置行高
     *
     * @param row    行对象
     * @param height 高度
     */
    public static void setRowHeight(XWPFTableRow row, int height) {
        if (ObjectUtil.isNull(row)) {
            throw new BaseException("设置行高失败,行对象为空");
        }
        CTTrPr trPr = row.getCtRow().addNewTrPr();
        CTHeight ht = trPr.addNewTrHeight();
        ht.setVal(BigInteger.valueOf(height));
    }

    /**
     * 在指定段落中插入表格
     *
     * @param paragraph 段落对象
     * @return 表格对象
     */
    public static XWPFTable insertTableToParagraph(XWPFParagraph paragraph) {
        return insertTableToParagraph(paragraph, 9600);
    }

    /**
     * 在指定段落中插入表格并指定表格宽度
     *
     * @param paragraph 段落对象
     * @param width     表格宽度
     * @return 表格对象
     */
    public static XWPFTable insertTableToParagraph(XWPFParagraph paragraph, Integer width) {
        if (ObjectUtil.isNull(paragraph)) {
            throw new BaseException("插入表格失败,段落对象不能为空");
        }

        if (ObjectUtil.isNull(width)) {
            throw new BaseException("插入表格失败,表格宽度不能为空");
        }

        XWPFDocument doc = paragraph.getDocument();
        XmlCursor cursor = paragraph.getCTP().newCursor();
        return insertTableByCursor(doc, cursor, width);
    }

    /**
     * 给表格添加数据
     *
     * @param table    表格
     * @param dataList 数据集合
     */
    public static void addTableContent(XWPFTable table, List<List<String>> dataList) {
        if (ObjectUtil.isNull(table)) {
            throw new BaseException("给表格添加数据失败,表格对象为空");
        }
        if (CollectionUtil.isEmpty(dataList)) {
            throw new BaseException("给表格添加数据失败,数据集合为空");
        }

        for (int i = 0, len = dataList.size(); i < len; i++) {
            List<String> list = dataList.get(i);
            XWPFTableRow row = getRow(table, i);
            for (int j = 0, len2 = list.size(); j < len2; j++) {
                XWPFTableCell cell = getCenterCell(row, j);
                XWPFParagraph paragraph = cell.getParagraphs().get(0);
                XWPFRun run = paragraph.createRun();
                if (StrUtil.isBlank(list.get(j)) || CollectionUtil.isEmpty(cell.getParagraphs()) || !list.get(j).contains(Const.Escape.LINE_BREAK)) {
                    run.setText(list.get(j));
                    continue;
                }

                // 如果有\n需要转成换行符
                String[] arr = list.get(j).split(Const.Escape.LINE_BREAK);
                for (int k = 0, len3 = arr.length; k < len3; k++) {
                    run.setText(arr[k], k);
                    if (k != len3 - 1) {
                        run.addBreak();
                    }
                }

            }
        }

    }
}
