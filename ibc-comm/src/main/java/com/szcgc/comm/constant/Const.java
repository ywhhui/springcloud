package com.szcgc.comm.constant;

/**
 * 常量
 */
public interface Const {

    /**
     * 表单符号
     */
    interface Symbol {
        String DOT = ",";
        String UNDERLINE = "_";
        String EQUALS = "=";
        String ADD = "+";
        String SUBTRACT = "-";
        String PERCENT = "%";
        String SLASH = "/";
    }

    /**
     * 后缀
     */
    interface Suffix {
        String XLSX = ".xlsx";
        String XLS = ".xls";
        String DOCX = ".docx";
        String DOC = ".doc";
    }

    /**
     * 转义字符
     */
    interface Escape {
        String LINE_BREAK = "\n";
    }

    /**
     * 数字
     */
    interface Number {
        String ZERO = "0";
    }

    /**
     * 正则
     */
    interface Regex {
        // 数字
        String NUMBER = "-?\\d+(\\.\\d+)?";
        // 年月日期格式
        String YYYY_MM_DD_REG = "^2\\d{3}-(0[1-9]|11|12)-(0[1-9]|[12]\\d|31|30)$";
    }

    /**
     * 日期格式
     */
    interface DateFormat {
        String YYYY_MM_DD = "yyyy-MM-dd";
        String YMD = "yyyyMMdd";
    }

}
