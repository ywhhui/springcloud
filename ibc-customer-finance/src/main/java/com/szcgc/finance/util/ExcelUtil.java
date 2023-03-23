package com.szcgc.finance.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.exception.BaseException;
import com.szcgc.finance.annotation.Excel;
import com.szcgc.finance.constant.DicEnum;
import com.szcgc.finance.constant.DicTypeEnum;
import com.szcgc.finance.model.FieldDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.compress.utils.Lists;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * excel工具类
 *
 * @author chenjiaming
 * @date 2022-9-21 10:55:57
 */
public class ExcelUtil extends ReflectCacheUtil {

    public static final Integer COL_WIDTH = 16 * 255;
    private static final String FONT_FAMILY = "微软雅黑";
    private static final String COMBOX_ERR_TITLE = "提示";
    private static final String COMBOX_ERR_TXT = "请选择下拉框里的数据";
    private static final String LOSS_HEAD_ROW_ERROR = "缺少标题行,请使用正确模板";
    private static final String LOSS_SHEET_ERROR = "缺少[%s]sheet页,请使用正确模板";
    private static final String TITLE_COLLECTION_ERROR = "标题集合不能为空";
    public static final String BANK_MODEL_ERROR = "导入数据为空,请检查模板";
    private static final String HEAD_ERROR = "第[%s]列表头错误,导入值为[%s],实际值为[%s],请使用正确模板";
    private static final String VERTICAL_HEAD_ERROR = "第[%s]行第[%s]列表头错误,导入值为[%s],实际值为[%s],请使用正确模板";
    private static final String REQUIRED_ERROR = "第[%s]行[%s]列[%s]必填";
    private static final String FORMAT_ERROR = "第[%s]行[%s]列格式错误,正确格式为[%s],实际值为[%s],请使用正确格式";
    private static final String ROW_IDX = "rowIdx";
    private static final String COL_IDX = "colIdx";
    private static final String DOT_ZERO = ".0";
    private static final String PASSWORD = "szcgc123456";
    private static final String FINANCE_MODEL_NAME = "财务数据模板";
    private static final String PLACEHOLDER = "$1";
    private static final String DATE_FORMAT = "日期格式:";
    private static final String YYYY_MM = "yyyy-MM";


    /**
     * 获取示例数据
     *
     * @param cls 类
     * @return 示例数据集合
     */
    private static List<String> getSampleData(Class cls) {
        return getFieldList(cls).stream().filter(obj -> ObjectUtil.isNotEmpty(obj.getExcel())).map(obj -> obj.getExcel().sample()).collect(Collectors.toList());
    }

    /**
     * 获取excel表头数据
     *
     * @param cls 类
     * @return 示例数据集合
     */
    private static List<String> getTitleData(Class cls) {
        return getFieldList(cls).stream().filter(obj -> ObjectUtil.isNotEmpty(obj.getExcel())).map(obj -> {
            // 如果excel注解中没写表头名称,则用swagger注解名称
            if (StrUtil.isBlank(obj.getExcel().name())) {
                if (ObjectUtil.isNotEmpty(obj.getSchema())) {
                    return obj.getSchema().description();
                }
                return null;
            }
            return obj.getExcel().name();
        }).collect(Collectors.toList());
    }

    /**
     * 获取excel注解数据
     *
     * @param cls 类
     * @return excel注解列表
     */
    private static List<Excel> getExcelList(Class cls) {
        return getFieldList(cls).stream().filter(obj -> ObjectUtil.isNotEmpty(obj.getExcel())).map(FieldDetails::getExcel).collect(Collectors.toList());
    }

    /**
     * 空模板导出
     *
     * @param response 响应对象
     */
    public static void exportBankModel(HttpServletResponse response, Class cls) throws Exception {
        exportBankModel(response, getObjName(cls), cls);
    }

    /**
     * 空模板导出
     *
     * @param response  响应对象
     * @param sheetName sheet页名称
     */
    public static void exportBankModel(HttpServletResponse response, String sheetName, Class cls) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        try {
            XSSFSheet sheet = workbook.createSheet(sheetName);

            // 设置下拉校验
            setCombox(workbook, sheet, cls, true);

            // 设置示例数据
            XSSFRow row = sheet.createRow(0);
            List<String> sampleDataList = getSampleData(cls);
            setSample(row, sampleDataList, workbook);

            // 设置表头数据
            row = sheet.createRow(1);
            List<String> headDataList = getTitleData(cls);
            setHead(row, headDataList, workbook);

            // 设置列宽
            for (int i = 0, len = headDataList.size(); i < len; i++) {
                sheet.setColumnWidth(i, COL_WIDTH);
            }

            setResponse(response, sheetName);

            workbook.write(response.getOutputStream());
        } finally {
            IoUtil.close(workbook);
        }
    }

    /**
     * 设置下拉校验
     *
     * @param workbook excel对象
     * @param sheet    需要下拉校验的sheet对象
     * @param cls      类
     * @param sample   是否有示例数据,有则从第三行开始加校验,没有则从第二行开始加
     */
    private static void setCombox(XSSFWorkbook workbook, XSSFSheet sheet, Class cls, boolean sample) {
        List<Excel> excels = getExcelList(cls);

        //设置下拉列表
        XSSFSheet hiddenSheet = workbook.createSheet("Data Validation");
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(hiddenSheet);
        for (int i = 0, len = excels.size(); i < len; i++) {
            if (DicTypeEnum.UNDEFINED.name().equals(excels.get(i).comboxType().name())) {
                continue;
            }

            List<String> names = DicEnum.getNamesByType(excels.get(i).comboxType());
            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(names.toArray(new String[names.size()]));
            // 开始结束行，i表示开始和结束列
            CellRangeAddressList addressList = new CellRangeAddressList(sample ? 2 : 1, 100000, i, i);
            XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
            // 这两行设置单元格只能是列表中的内容，否则报错
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            validation.createErrorBox(COMBOX_ERR_TITLE, COMBOX_ERR_TXT);
            sheet.addValidationData(validation);
        }


        workbook.setSheetHidden(1, true);

    }

    public static void setResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding(CharEncoding.UTF_8);
        fileName = fileName.concat(Const.Symbol.UNDERLINE).concat(String.valueOf(System.currentTimeMillis())).concat(Const.Suffix.XLSX);
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, CharEncoding.UTF_8));
    }

    /**
     * 给合并区域设置边框
     *
     * @param region 合并区域对象
     * @param sheet  sheet页
     */
    public static void setBorderStyle(CellRangeAddress region, XSSFSheet sheet) {
        RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
    }

    /**
     * 获取样式
     *
     * @param workbook excel对象
     * @return 样式对象
     */
    public static XSSFCellStyle getStyle(XSSFWorkbook workbook) {
        return getStyle(false, IndexedColors.WHITE.index, IndexedColors.BLACK.index, workbook);
    }

    /**
     * 获取样式
     *
     * @param center   是否居中
     * @param workbook excel对象
     * @return 样式对象
     */
    public static XSSFCellStyle getStyle(boolean center, XSSFWorkbook workbook) {
        return getStyle(center, IndexedColors.WHITE.index, IndexedColors.BLACK.index, workbook);
    }

    /**
     * 获取样式
     *
     * @param center   是否居中
     * @param bold     是否加粗
     * @param workbook excel对象
     * @return 样式对象
     */
    public static XSSFCellStyle getStyle(boolean center, boolean bold, XSSFWorkbook workbook) {
        return getStyle(center, IndexedColors.WHITE.index, IndexedColors.BLACK.index, bold, workbook);
    }

    /**
     * 获取样式
     *
     * @param center   是否居中
     * @param bold     是否加粗
     * @param fontSize 字体大小
     * @param workbook excel对象
     * @return 样式对象
     */
    public static XSSFCellStyle getStyle(boolean center, boolean bold, int fontSize, XSSFWorkbook workbook) {
        return getStyle(center, IndexedColors.WHITE.index, IndexedColors.BLACK.index, bold, fontSize, workbook);
    }

    /**
     * 获取样式
     *
     * @param center    是否居中
     * @param bold      是否加粗
     * @param backColor 背景色
     * @param color     字体颜色
     * @param workbook  excel对象
     * @return 样式对象
     */
    public static XSSFCellStyle getStyle(boolean center, short backColor, short color, boolean bold, XSSFWorkbook workbook) {
        return getStyle(center, backColor, color, bold, 11, workbook);
    }

    /**
     * 获取样式
     *
     * @param center    是否居中
     * @param bold      是否加粗
     * @param backColor 背景色
     * @param color     字体颜色
     * @param workbook  excel对象
     * @return 样式对象
     */
    public static XSSFCellStyle getStyle(boolean center, int[] backColor, short color, boolean bold, XSSFWorkbook workbook) {
        return getStyle(center, backColor, color, bold, 11, workbook);
    }

    /**
     * 获取样式
     *
     * @param center    是否居中
     * @param bold      是否加粗
     * @param backColor 自定义背景色rgb数组
     * @param workbook  excel对象
     * @return 样式对象
     */
    public static XSSFCellStyle getStyle(boolean center, int[] backColor, boolean bold, XSSFWorkbook workbook) {
        return getStyle(center, backColor, IndexedColors.BLACK.index, bold, 12, workbook);
    }

    /**
     * 根据rgb数组生成颜色对象
     *
     * @param rgb rgb数组
     * @return 颜色对象
     */
    public static XSSFColor getDefinedColor(int[] rgb) {
        return new XSSFColor(new java.awt.Color(rgb[0], rgb[1], rgb[2]), new DefaultIndexedColorMap());
    }


    /**
     * 获取样式
     *
     * @param center    是否居中
     * @param backColor 背景颜色
     * @param backColor 自定义背景色rgb数组
     * @param bold      是否加粗
     * @param fontSize  字体大小
     * @param workbook  excel对象
     * @return 样式对象
     */
    public static XSSFCellStyle getStyle(boolean center, int[] backColor, short color, boolean bold, int fontSize, XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        if (center) {
            // 水平居中
            style.setAlignment(HorizontalAlignment.CENTER);
            // 垂直居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        // 背景颜色
        style.setFillForegroundColor(getDefinedColor(backColor));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //设置边框 上 右 下 左
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        Font font = workbook.createFont();
        font.setFontName(FONT_FAMILY);
        font.setColor(color);
        font.setBold(bold);
        font.setFontHeight((short) (fontSize * 20));
        style.setFont(font);

        style.setLocked(false);

        return style;
    }

    /**
     * 获取样式
     *
     * @param center    是否居中
     * @param backColor 背景颜色
     * @param color     字体颜色
     * @param bold      是否加粗
     * @param fontSize  字体大小
     * @param workbook  excel对象
     * @return 样式对象
     */
    public static XSSFCellStyle getStyle(boolean center, short backColor, short color, boolean bold, int fontSize, XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        if (center) {
            // 水平居中
            style.setAlignment(HorizontalAlignment.CENTER);
            // 垂直居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        // 背景颜色
        style.setFillForegroundColor(backColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //设置边框 上 右 下 左
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        Font font = workbook.createFont();
        font.setFontName(FONT_FAMILY);
        font.setColor(color);
        font.setBold(bold);
        font.setFontHeight((short) (fontSize * 20));
        style.setFont(font);

        style.setLocked(false);

        return style;
    }


    /**
     * 获取样式
     *
     * @param center    是否居中
     * @param backColor 背景颜色
     * @param color     字体颜色
     * @param workbook  excel对象
     * @return 样式对象
     */
    public static XSSFCellStyle getStyle(boolean center, short backColor, short color, XSSFWorkbook workbook) {
        return getStyle(center, backColor, color, false, workbook);
    }

    /**
     * 设置表头
     *
     * @param row        行对象
     * @param sampleList 示例数据
     * @param workbook   excel对象
     */
    private static void setSample(XSSFRow row, List<String> sampleList, XSSFWorkbook workbook) {
        XSSFCellStyle style = getStyle(true, workbook);
        setData(row, sampleList, style);
    }

    /**
     * 设置表头
     *
     * @param row      行对象
     * @param headList 表头数据
     * @param workbook excel对象
     */
    private static void setHead(XSSFRow row, List<String> headList, XSSFWorkbook workbook) {
        XSSFCellStyle style = getStyle(true, HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex(), HSSFColor.HSSFColorPredefined.WHITE.getIndex(), workbook);
        setData(row, headList, style);
    }

    /**
     * 设置数据
     *
     * @param row  行对象
     * @param data 数据对象
     */
    private static void setData(XSSFRow row, List<String> data, XSSFCellStyle style) {
        for (int i = 0, len = data.size(); i < len; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            row.getCell(i).setCellValue(data.get(i));
        }
    }

    /**
     * 导入表格必填校验
     *
     * @param list 读取到的数据
     * @param <T>  泛型
     * @return 错误提示
     */
    public static <T> String verifyTable(List<T> list) {
        return verifyTable(list, false);
    }

    /**
     * 导入表格必填校验
     *
     * @param list   读取到的数据
     * @param <T>    泛型
     * @param sample 是否有示例数据
     * @return 错误提示
     */
    public static <T> String verifyTable(List<T> list, boolean sample) {
        if (CollectionUtil.isEmpty(list)) {
            return BANK_MODEL_ERROR;
        }

        Class cls = list.get(0).getClass();
        // 错误提示
        String errorTips = "";
        // 表头
        List<String> headList = getTitleData(cls);
        // 反射对象集合
        List<FieldDetails> fieldDetails = getFieldList(cls).stream().filter(obj -> ObjectUtil.isNotNull(obj.getExcel())).collect(Collectors.toList());
        // 错误数量
        int errorNum = 0;
        for (int i = 0, len = list.size(); i < len; i++) {
            Object obj = list.get(i);
            if (ObjectUtil.isNull(obj)) {
                continue;
            }

            for (int j = 0, len2 = headList.size(); j < len2; j++) {
                Field field = fieldDetails.get(j).getField();
                Object value = ReflectUtil.getFieldValue(obj, field);
                if (ObjectUtil.isNotEmpty(value)) {
                    continue;
                }

                Schema schema = fieldDetails.get(j).getSchema();
                Excel excel = fieldDetails.get(j).getExcel();
                // swagger有必填属性或者excel中有必填属性
                if (ObjectUtil.isNotEmpty(schema) && schema.required() || excel.required()) {
                    errorNum++;
                    errorTips = errorTips.concat(String.format(REQUIRED_ERROR, i + (sample ? 3 : 2), j + 1, headList.get(j))).concat(Const.Escape.LINE_BREAK);
                }

                if (errorNum >= 50) {
                    return errorTips;
                }

            }

        }
        return errorTips;
    }

    /**
     * 读取excel
     *
     * @param is     输入流
     * @param cls    类
     * @param <T>    泛型
     * @param sample 是否有示例数据
     * @return 对象集合
     */
    public static <T> List<T> getData(InputStream is, Class<T> cls, boolean sample) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        List<T> list = Lists.newArrayList();
        try {
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<FieldDetails> fieldDetails = getFieldList(cls);
            for (int i = sheet.getFirstRowNum() + (sample ? 2 : 1); i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                if (ObjectUtil.isNull(row)) {
                    continue;
                }
                T t = cls.newInstance();
                for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                    XSSFCell cell = row.getCell(j);
                    if (ObjectUtil.isEmpty(cell)) {
                        continue;
                    }
                    Field field = fieldDetails.get(j).getField();
                    // 从excel中获取值
                    Object value = getValue(cell);
                    // 翻译下拉数据
                    value = translateData(fieldDetails.get(j).getExcel(), value);
                    // 转换成实体类类型
                    value = castValue(field.getType(), value);
                    ReflectUtil.setFieldValue(t, field, value);
                }
                list.add(t);
            }

        } finally {
            IoUtil.close(workbook);
        }
        return list;
    }

    /**
     * 翻译下拉数据
     *
     * @param excel excel注解
     * @param value excel值
     * @return 翻译后的值
     */
    private static Object translateData(Excel excel, Object value) {
        if (DicTypeEnum.UNDEFINED == excel.comboxType() || ObjectUtil.isEmpty(value)) {
            return value;
        }
        return DicEnum.getValueByNameAndType(value, excel.comboxType());
    }

    /**
     * 类型转换
     *
     * @param fieldType 字段类型
     * @param value     值
     * @return 最终值
     */
    private static Object castValue(Class<?> fieldType, Object value) {
        if (String.class == fieldType) {
            String s = Convert.toStr(value);
            // 日期会出现2022.0的情况
            if (StrUtil.endWith(s, DOT_ZERO)) {
                return s.replace(DOT_ZERO, "");
            }
            return s;
        } else if (Date.class == fieldType) {
            if (value instanceof String) {
                return DateUtils.parseDate(Convert.toStr(value));
            } else if (value instanceof Double) {
                return DateUtil.getJavaDate((Double) value);
            }
        }

        // 转成数字类型前先将 "-" 转 "0"
        if (value instanceof String) {
            value = ((String) value).trim().replace(Const.Symbol.SUBTRACT, Const.Number.ZERO);
        }

        if (Integer.TYPE == fieldType || Integer.class == fieldType) {
            return Convert.toInt(value);
        } else if (Long.TYPE == fieldType || Long.class == fieldType) {
            return Convert.toLong(value);
        } else if (Double.TYPE == fieldType || Double.class == fieldType) {
            return Convert.toDouble(value);
        } else if (Float.TYPE == fieldType || Float.class == fieldType) {
            return Convert.toFloat(value);
        } else if (BigDecimal.class == fieldType) {
            return Convert.toBigDecimal(value);
        }
        return value;
    }

    /**
     * 获取值
     *
     * @param cell 列对象
     * @return 值
     */
    private static Object getValue(Cell cell) {
        Object val = "";
        if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
            val = cell.getNumericCellValue();
            if (DateUtil.isCellDateFormatted(cell)) {
                // POI Excel 日期格式转换
                val = DateUtil.getJavaDate((Double) val);
            } else {
                if ((Double) val % 1 != 0) {
                    val = new BigDecimal(val.toString());
                } else {
                    val = new DecimalFormat(Const.Number.ZERO).format(val);
                }
            }
        } else if (cell.getCellType() == CellType.STRING) {
            val = cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            val = cell.getBooleanCellValue();
        } else if (cell.getCellType() == CellType.ERROR) {
            val = cell.getErrorCellValue();
        }
        return val;
    }

    /**
     * 读取excel
     *
     * @param is     输入流
     * @param cls    类
     * @param <T>    泛型
     * @param sample 是否有示例数据
     * @return 对象集合
     */
    public static <T> String verifyHead(FileInputStream is, Class<T> cls, boolean sample) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        String errorTips = "";
        try {
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(sample ? 1 : 0);
            if (ObjectUtil.isNull(row)) {
                errorTips = errorTips.concat(LOSS_HEAD_ROW_ERROR).concat(Const.Escape.LINE_BREAK);
            }
            List<String> headList = getTitleData(cls);
            for (int i = 0, len = headList.size(); i < len; i++) {
                XSSFCell cell = row.getCell(i);
                if (ObjectUtil.isNull(cell)) {
                    continue;
                }

                String value = String.valueOf(getValue(cell));
                if (headList.get(i).equals(value)) {
                    continue;
                }

                errorTips = errorTips.concat(String.format(HEAD_ERROR, i + 1, value, headList.get(i))).concat(Const.Escape.LINE_BREAK);

            }
        } finally {
            IoUtil.close(workbook);
        }
        return errorTips;
    }

    /**
     * 导出excel财务模板
     *
     * @param response 响应对象
     * @param title    标题
     */
    public static <T> void exportFinanceBankModel(HttpServletResponse response, List<String> title, Class<T> tClass) throws Exception {
        if (CollectionUtil.isEmpty(title)) {
            throw new BaseException(TITLE_COLLECTION_ERROR);
        }
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook();

            List<FieldDetails> classFields = getFieldList(tClass);
            for (int i = 0, len = classFields.size(); i < len; i++) {
                Class cls = classFields.get(i).getField().getType();
                String sheetName = getObjName(cls);
                XSSFSheet sheet = workbook.createSheet(sheetName);
//                // 设置保护表格
//                sheet.protectSheet(PASSWORD);
                // 设置列宽
                for (int j = 0, len2 = title.size(); j < len2; j++) {
                    sheet.setColumnWidth(j, COL_WIDTH);
                }

                // 第一行表头
                XSSFRow row = sheet.createRow(0);
                for (int j = 0, len2 = title.size(); j < len2; j++) {
                    XSSFCell cell = row.createCell(j);
                    XSSFCellStyle style = getStyle(true, HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex(),
                            HSSFColor.HSSFColorPredefined.BLACK.getIndex(), workbook);
                    cell.setCellStyle(style);
                    cell.setCellValue(title.get(j));
                }

                // 第二行
                row = sheet.createRow(1);
                for (int j = 0, len2 = title.size(); j < len2; j++) {
                    XSSFCell cell = row.createCell(j);
                    cell.setCellStyle(getStyle(false, workbook));

                    if (j == 0) {
                        XSSFCellStyle style = getStyle(true, HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex(),
                                HSSFColor.HSSFColorPredefined.BLACK.getIndex(), workbook);
                        cell.setCellStyle(style);
                        cell.setCellValue(StrUtil.lowerFirst(cls.getSimpleName()));
                        continue;
                    }

                    // 合并单元格
                    if (j == 1) {
                        CellRangeAddress region = new CellRangeAddress(0, 1, 1, 1);
                        sheet.addMergedRegion(region);
                        continue;
                    }
                    // 添加批注
                    addComment(cell, DATE_FORMAT.concat(Const.Escape.LINE_BREAK).concat(YYYY_MM), 1, 2);
                }

                int col2Len = 0;
                // 获取类的字段对象
                List<FieldDetails> fieldDetails = getFieldList(cls).stream().filter(obj -> ObjectUtil.isNotEmpty(obj.getExcel())).collect(Collectors.toList());
                for (int j = 0, len2 = fieldDetails.size(); j < len2; j++) {
                    row = sheet.createRow(j + 2);
                    XSSFCell cell = row.createCell(0);
                    XSSFCellStyle style = getStyle(true, HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex(),
                            HSSFColor.HSSFColorPredefined.BLACK.getIndex(), workbook);
                    cell.setCellStyle(style);
                    cell.setCellValue(fieldDetails.get(j).getField().getName());

                    style = getStyle(false, HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex(),
                            HSSFColor.HSSFColorPredefined.BLACK.getIndex(), workbook);
                    cell = row.createCell(1);
                    cell.setCellStyle(style);
                    String value;
                    FieldDetails field = fieldDetails.get(j);
                    // swagger注解description有值优先用swagger注解值,没有用excel注解值
                    if (ObjectUtil.isEmpty(field.getSchema()) || StrUtil.isBlank(field.getSchema().description())) {
                        value = field.getExcel().name();
                    } else {
                        value = field.getSchema().description();
                    }
                    if (StrUtil.length(value) > col2Len) {
                        col2Len = StrUtil.length(value);
                    }
                    cell.setCellValue(value);

                    for (int k = 2, len3 = title.size(); k < len3; k++) {
                        cell = row.createCell(k);
                        cell.setCellStyle(getStyle(false, workbook));

                        if (StrUtil.isNotBlank(field.getExcel().formula())) {
                            // 解析公式
                            String formula = parseFormula(cell, field.getExcel().formula());
                            cell.setCellFormula(formula);
                            XSSFCellStyle lockStyle = cell.getCellStyle();
                            lockStyle.setLocked(true);
                            lockStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
                        }


                    }
                }

                sheet.setColumnWidth(1, col2Len * 2 * 255);

            }


            setResponse(response, FINANCE_MODEL_NAME);

            workbook.write(response.getOutputStream());

        } finally {
            IoUtil.close(workbook);
        }
    }

    /**
     * 解析公式
     *
     * @param cell    单元格对象
     * @param formula 公式表达式
     * @return 最终公式
     */
    private static String parseFormula(XSSFCell cell, String formula) {
        Map<String, String> map = parseTemplate(formula);
        // 没有表达式需要替换
        if (CollectionUtil.isEmpty(map)) {
            return formula;
        }

        for (Map.Entry<String, String> set : map.entrySet()) {
            String key = set.getKey();
            String value = set.getValue();

            if (StrUtil.isBlank(value)) {
                continue;
            }

            String[] arr;
            value = value.replace(ROW_IDX, String.valueOf(cell.getRowIndex()));
            value = value.replace(COL_IDX, String.valueOf(cell.getColumnIndex()));

            int res;
            if (value.contains(Const.Symbol.ADD)) {
                arr = value.split(Const.Symbol.ADD);
                res = Convert.toInt(arr[0]) + Convert.toInt(arr[1]);
            } else if (value.contains(Const.Symbol.SUBTRACT)) {
                arr = value.split(Const.Symbol.SUBTRACT);
                res = Convert.toInt(arr[0]) - Convert.toInt(arr[1]);
            } else {
                res = Convert.toInt(value);
            }
            value = key.contains(COL_IDX) ? getColumnName(res) : String.valueOf(res + 1);

            formula = formula.replace(key, value);
        }

        return formula;
    }

    /**
     * 模板解析
     *
     * @param template 模板字符串 如 ${rowIdx+1}
     * @return 以模板字符串为key, 模板内容为value   ${rowIdx+1} - rowIdx+1
     */
    public static Map<String, String> parseTemplate(String template) {
        if (StrUtil.isBlank(template)) {
            return MapUtil.newHashMap();
        }
        String reg = "\\$\\{([^\\}]+)\\}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(template);
        Map<String, String> map = MapUtil.newHashMap();
        while (matcher.find()) {
            String key = matcher.group();
            String value = key.replaceAll(reg, PLACEHOLDER);
            map.put(key, value);
        }
        return map;
    }


    /**
     * 根据索引列获取真实的Excel列【A B C ...】
     *
     * @param columnNum 列下标
     * @return excel列名
     */
    public static String getColumnName(int columnNum) {
        columnNum = columnNum + 1;

        int first;
        int last;
        String result = "";
        if (columnNum > 256) {
            columnNum = 256;
        }

        first = columnNum / 27;
        last = columnNum - (first * 26);
        if (first > 0) {
            result = String.valueOf((char) (first + 64));
        }

        if (last > 0) {
            result = result + (char) (last + 64);
        }

        return result;
    }

    /**
     * 给Cell添加批注
     *
     * @param cell  单元格
     * @param value 批注内容
     * @param col   批注框列宽
     * @param row   批注框行高
     */
    private static void addComment(Cell cell, String value, Integer col, Integer row) {
        Sheet sheet = cell.getSheet();
        cell.removeCellComment();
        ClientAnchor anchor = new XSSFClientAnchor();
        // 关键修改
        anchor.setDx1(0);
        anchor.setDx2(0);
        anchor.setDy1(0);
        anchor.setDy2(0);
        anchor.setCol1(cell.getColumnIndex());
        anchor.setRow1(cell.getRowIndex());
        anchor.setCol2(cell.getColumnIndex() + col - 1);
        anchor.setRow2(cell.getRowIndex() + row - 1);
        // 结束
        Drawing drawing = sheet.createDrawingPatriarch();
        Comment comment = drawing.createCellComment(anchor);
        // 输入批注信息
        comment.setString(new XSSFRichTextString(value));
        // 将批注添加到单元格对象中
        cell.setCellComment(comment);
    }

    /**
     * 校验财务excel表头
     *
     * @param fis   文件流
     * @param title 标题集合
     * @return 错误提示
     */
    public static <T> String verifyFinanceHead(FileInputStream fis, List<String> title, Class<T> tClass) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        String errorTips = "";
        try {
            if (CollectionUtil.isEmpty(title)) {
                throw new BaseException(TITLE_COLLECTION_ERROR);
            }
            List<FieldDetails> classFields = getFieldList(tClass);
            for (int i = 0, len = classFields.size(); i < len; i++) {
                Class cls = classFields.get(i).getField().getType();
                String sheetName = getObjName(cls);
                XSSFSheet sheet = workbook.getSheet(sheetName);
                if (ObjectUtil.isNull(sheet)) {
                    errorTips = errorTips.concat(String.format(LOSS_SHEET_ERROR, sheetName)).concat(Const.Escape.LINE_BREAK);
                    continue;
                }

                // 校验第一行表头
                XSSFRow row = sheet.getRow(0);
                if (ObjectUtil.isNull(row)) {
                    errorTips = errorTips.concat(LOSS_HEAD_ROW_ERROR).concat(Const.Escape.LINE_BREAK);
                    continue;
                }
                for (int j = 0, len2 = title.size(); j < len2; j++) {
                    XSSFCell cell = row.getCell(j);
                    if (ObjectUtil.isNull(cell)) {
                        continue;
                    }
                    String value = String.valueOf(getValue(cell));
                    if (title.get(j).equals(value)) {
                        continue;
                    }
                    errorTips = errorTips.concat(String.format(HEAD_ERROR, j + 1, value, title.get(j))).concat(Const.Escape.LINE_BREAK);
                }

                // 校验第二行
                row = sheet.getRow(1);
                if (ObjectUtil.isNull(row)) {
                    errorTips = errorTips.concat(LOSS_HEAD_ROW_ERROR).concat(Const.Escape.LINE_BREAK);
                    continue;
                }
                for (int j = 0, len2 = title.size(); j < len2; j++) {
                    XSSFCell cell = row.getCell(j);
                    if (ObjectUtil.isNull(cell)) {
                        continue;
                    }
                    String value = String.valueOf(getValue(cell));
                    if (j == 0) {
                        if (StrUtil.lowerFirst(cls.getSimpleName()).equals(value)) {
                            continue;
                        }
                        errorTips = errorTips.concat(String.format(VERTICAL_HEAD_ERROR, 1, j + 1, value, title.get(j))).concat(Const.Escape.LINE_BREAK);
                        continue;
                    }

                    if (j > 1) {
                        try {
                            cn.hutool.core.date.DateUtil.parse(value, YYYY_MM);
                        } catch (Exception e) {
                            errorTips = errorTips.concat(String.format(FORMAT_ERROR, 2, j + 1, YYYY_MM, value)).concat(Const.Escape.LINE_BREAK);
                        }
                    }

                }

                List<FieldDetails> fieldDetailsList = getFieldList(cls).stream().filter(obj -> ObjectUtil.isNotNull(obj.getExcel())).collect(Collectors.toList());
                for (int j = 0, len2 = fieldDetailsList.size(); j < len2; j++) {
                    FieldDetails fieldDetails = fieldDetailsList.get(j);
                    row = sheet.getRow(j + 2);
                    if (ObjectUtil.isNull(row)) {
                        continue;
                    }

                    XSSFCell cell = row.getCell(0);
                    String importData = String.valueOf(getValue(cell));
                    String realValue = fieldDetails.getField().getName();
                    if (!realValue.equals(importData)) {
                        errorTips = errorTips.concat(String.format(VERTICAL_HEAD_ERROR, j + 3, 1, importData, realValue)).concat(Const.Escape.LINE_BREAK);
                    }
                    cell = row.getCell(1);
                    importData = String.valueOf(getValue(cell));
                    // swagger注解description有值优先用swagger注解值,没有用excel注解值
                    if (ObjectUtil.isEmpty(fieldDetails.getSchema()) || StrUtil.isBlank(fieldDetails.getSchema().description())) {
                        realValue = fieldDetails.getExcel().name();
                    } else {
                        realValue = fieldDetails.getSchema().description();
                    }
                    if (!realValue.equals(importData)) {
                        errorTips = errorTips.concat(String.format(VERTICAL_HEAD_ERROR, j + 3, 2, importData, realValue)).concat(Const.Escape.LINE_BREAK);
                    }

                }


            }
        } finally {
            IoUtil.close(workbook);
        }
        return errorTips;
    }

    /**
     * 获取财务数据
     *
     * @param fis           文件流
     * @param tClass        类
     * @param dateFieldName 日期字段名称
     * @param <T>
     * @return 财务数据集合
     */
    public static <T> List<T> getFinanceData(FileInputStream fis, Class<T> tClass, String dateFieldName) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        List<T> list = Lists.newArrayList();
        try {
            List<FieldDetails> classFields = getFieldList(tClass);
            for (int i = 0, len = classFields.size(); i < len; i++) {
                Class cls = classFields.get(i).getField().getType();
                String sheetName = getObjName(cls);
                XSSFSheet sheet = workbook.getSheet(sheetName);
                if (ObjectUtil.isNull(sheet)) {
                    throw new BaseException(LOSS_SHEET_ERROR, sheetName);
                }

                List<FieldDetails> fieldDetails = getFieldList(cls);
                // 取日期字段对象
                Field dateField = fieldDetails.stream().filter(obj -> dateFieldName.equals(obj.getField().getName()))
                        .map(FieldDetails::getField).collect(Collectors.toList()).get(0);
                // 只保留有excel注解的反射对象
                fieldDetails = fieldDetails.stream().filter(obj -> ObjectUtil.isNotNull(obj.getExcel())).collect(Collectors.toList());
                int lastCellNum = sheet.getRow(0).getLastCellNum();
                for (int j = 2; j < lastCellNum; j++) {
                    if (list.size() < lastCellNum - 2) {
                        // 添加财务数据对象
                        list.add(tClass.newInstance());
                    }
                    // 获取属性对象
                    Object obj = ReflectUtil.getFieldValue(list.get(j - 2), classFields.get(i).getField());
                    // 属性对象还没初始化初始化一遍
                    if (ObjectUtil.isNull(obj)) {
                        obj = cls.newInstance();
                        ReflectUtil.setFieldValue(list.get(j - 2), classFields.get(i).getField(), obj);
                    }
                    XSSFRow dateRow = sheet.getRow(1);
                    XSSFCell dateCell = dateRow.getCell(j);
                    Object dateValue = getValue(dateCell);
                    // 转换成实体类类型
                    dateValue = castValue(dateField.getType(), dateValue);
                    // 设置日期值
                    ReflectUtil.setFieldValue(obj, dateField, dateValue);

                    for (int k = 0, len2 = fieldDetails.size(); k < len2; k++) {
                        XSSFRow row = sheet.getRow(k + 2);
                        if (ObjectUtil.isNull(row)) {
                            continue;
                        }
                        XSSFCell cell = row.getCell(j);
                        if (ObjectUtil.isNull(cell)) {
                            continue;
                        }

                        Object value = getValue(cell);
                        Field field = fieldDetails.get(k).getField();
                        // 翻译下拉数据
                        value = translateData(fieldDetails.get(k).getExcel(), value);
                        // 转换成实体类类型
                        value = castValue(field.getType(), value);
                        // 设置属性值
                        ReflectUtil.setFieldValue(obj, field, value);
                    }
                }

            }
        } finally {
            IoUtil.close(workbook);
        }
        return list;
    }

    /**
     * 创建行
     *
     * @param sheet sheet对象
     * @param idx   下标
     * @return 行对象
     */
    public static XSSFRow createRow(XSSFSheet sheet, int idx) {
        XSSFRow row = sheet.getRow(idx);
        if (ObjectUtil.isNotNull(row)) {
            return row;
        }
        return sheet.createRow(idx);
    }
}
