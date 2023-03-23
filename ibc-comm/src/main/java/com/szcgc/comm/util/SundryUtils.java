package com.szcgc.comm.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author liaohong
 * @create 2020/8/19 17:18
 */
public class SundryUtils {

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static int requireId(int id) {
        if (id <= 0) {
            throw new NullPointerException();
        } else {
            return id;
        }
    }

    public static int page(Integer page) {
        return (page == null || page.intValue() < 1) ? 0 : page.intValue() - 1;
    }

    public static int size(Integer size) {
        if (size == null) {
            return 10;
        }
        int v = size.intValue();
        if (v < 1) {
            return 10;
        }
        if (v > 50) {
            return 50;
        }
        return v;
    }

    public static LocalDate tryGetLocalDate(String date) {
        return tryGetLocalDate(date, "yyyy-MM-dd", LocalDate.now());
    }

    public static LocalDate tryGetLocalDateOrNull(String date) {
        return tryGetLocalDate(date, "yyyy-MM-dd", null);
    }

    public static LocalDate tryGetLocalDate(String date, String fmt, LocalDate dft) {
        if (date == null) {
            return dft;
        }
        try {
            // DateTimeFormatter formatter =
            // DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fmt);
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            return dft;
        }
    }

    public static LocalTime tryGetLocalTime(String time) {
        return tryGetLocalTime(time, "HH:mm", LocalTime.now());
    }

    public static LocalTime tryGetLocalTime(String time, String fmt, LocalTime dft) {
        if (time == null) {
            return dft;
        }
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fmt);
            return LocalTime.parse(time, formatter);
        } catch (Exception e) {
            return dft;
        }
    }

    public static int tryGetInt(String s, int value) {
        if (s == null || s.length() == 0) {
            return value;
        }
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return value;
        }
    }

    public static int getYMDInt(LocalDate date) {
        if (date == null) {
            return 0;
        }
        return date.getYear() * 10000 + date.getMonthValue() * 100 + date.getDayOfMonth();
    }

    public static byte tryGetByte(boolean value) {
        return (value ? (byte) 1 : (byte) 0);
    }

//    public static String getDateString(int year, int month, int day, String fmt) {
//        LocalDate date = LocalDate.of(year, month, day);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fmt);
//        return date.format(formatter);
//    }
//
//    public static String getYyyyMM(int year, int month) {
//        return getDateString(year, month, 1, "yyyy-MM");
//    }
//
//    public static String toExcelData(String str) {
//        str = str.replace("<", "&lt;");
//        str = str.replace(">", "&gt;");
//        str = str.replace("&", "&amp;");
//        str = str.replace("'", "&apos;");
//        str = str.replace("\"", "&quot;");
//        return str;
//    }

}
