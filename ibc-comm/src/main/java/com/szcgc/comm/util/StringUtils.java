package com.szcgc.comm.util;

import java.util.Map;

public class StringUtils {

    public static boolean isEmpty(final String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 取指定长度的起始字符
     *
     * @param s
     * @param length
     * @return
     */
    public static String first(String s, int length) {
        if (s == null)
            return null;
        if (s.length() <= length)
            return s;
        return s.substring(0, length);
    }

    /**
     * 取指定长度的结尾字符
     *
     * @param s
     * @param length
     * @return
     */
    public static String ending(String s, int length) {
        if (s == null)
            return null;
        if (s.length() <= length)
            return s;
        return s.substring(s.length() - length);
    }

    public static String join(String sp, Object... objs) {
        if (objs == null || objs.length <= 0)
            return null;
        StringBuffer sb = new StringBuffer();
        for (Object obj : objs) {
            sb.append(obj);
            sb.append(sp);
        }
        return sb.toString();
    }

    public static String joinMap(String spEntry, String spKv, Map<String, Object> params) {
        if (params == null || params.size() <= 0)
            return null;
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append(spKv);
            sb.append(entry.getValue());
            sb.append(spEntry);
        }
        return sb.toString();
    }

    public static boolean isLetterOrDigit(char c) {
        int i = c;
        if (i >= 65 && i <= 90)
            return true;
        if (i >= 97 && i <= 122)
            return true;
        if (i >= 48 && i <= 57)
            return true;
        return false;
    }

}
