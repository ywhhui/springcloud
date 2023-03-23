package com.szcgc.comm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsccUtil {

    private static final int IDENTITYCODE_NEW = 18; // 新身份证18位
    private static int[] Wi = new int[17];
    private static final String str = "";

    /**
     * 校验18位统一社会信用代码正确性
     *
     * @param uscc 待校验的统一社会信用代码（要求字母已经保持大写）
     * @return boolean类型，false表示验证未通过，true表示验证通过
     */
    public static String checkUscc(String uscc) {
        if (StringUtils.isEmpty(uscc)) {
            return "统一社会信用代码为空";
        }

        if (uscc.length() != 18) {
            return "统一社会信用代码长度错误";
        }
        uscc = uscc.toUpperCase();
        // 用于存放权值
        int weight[] = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
        // 用于计算当前判断的统一社会信用代码位数
        int index = 0;
        // 用于存放当前位的统一社会信用代码
        char testc;
        // 用于存放代码字符和加权因子乘积之和
        int tempSum = 0;
        int tempNum = 0;
        for (index = 0; index <= 16; index++) {
            testc = uscc.charAt(index);

            if (index == 0) {
                if (testc != '1' && testc != '5' && testc != '9' && testc != 'Y') {
                    return "统一社会信用代码中登记管理部门代码错误";
                }
            }

            if (index == 1) {
                if (testc != '1' && testc != '2' && testc != '3' && testc != '9') {
                    return "统一社会信用代码中机构类别代码错误";
                }
            }

            tempNum = charToNum(testc);
            // 验证代码中是否有错误字符
            if (tempNum != -1) {
                tempSum += weight[index] * tempNum;
            } else {
                return "统一社会信用代码中出现错误字符";
            }
        }
        tempNum = 31 - tempSum % 31;
        if (tempNum == 31) {
            tempNum = 0;
        }
        // 按照GB/T 17710标准对统一社会信用代码前17位计算校验码，并与第18位校验位进行比对
        if (charToNum(uscc.charAt(17)) != tempNum) {
            return "统一社会信用代码第18位校验位错误";
        }
        return "";
    }

    /**
     * 按照GB32100-2015标准代码字符集将用于检验的字符变为相应数字
     *
     * @param c 待转换的字符
     * @return 转换完成后对应的数字, 若无法找到字符集中字符，返回-1
     */
    public static int charToNum(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;
            case 'G':
                return 16;
            case 'H':
                return 17;
            case 'J':
                return 18;
            case 'K':
                return 19;
            case 'L':
                return 20;
            case 'M':
                return 21;
            case 'N':
                return 22;
            case 'P':
                return 23;
            case 'Q':
                return 24;
            case 'R':
                return 25;
            case 'T':
                return 26;
            case 'U':
                return 27;
            case 'W':
                return 28;
            case 'X':
                return 29;
            case 'Y':
                return 30;
            default:
                return -1;
        }
    }


    /**
     * 判断身份证号码是否正确。
     *
     * @param code 身份证号码。
     * @return 如果身份证号码正确，则返回true，否则返回false。
     */

    public static String isIdentityCode(String code) {

        if (code == null || str.equals(code.trim())) {
            return "请输入身份证号!";
        }

        code = code.trim();

        // 检验长度
        if (code.length() != IDENTITYCODE_NEW) {
            return "身份证号长度不正确!";
        }

        // 身份证号码必须为数字(18位的新身份证最后一位可以是x)
        Pattern pt = Pattern.compile("\\d{15,17}([\\dxX]{1})?");
        Matcher mt = pt.matcher(code);
        if (!mt.find()) {
            return "身份证号格式不正确";
        }


        // 最后一位校验码验证
        if (code.length() == IDENTITYCODE_NEW) {
            String lastNum = getCheckFlag(code.substring(0,
                    IDENTITYCODE_NEW - 1));
            // check last digit
            if (!("" + code.charAt(IDENTITYCODE_NEW - 1)).toUpperCase().equals(
                    lastNum)) {
                return "身份证号错误";
            }
        }

        return "";
    }

    /**
     * 获取新身份证的最后一位:检验位
     *
     * @param code 18位身份证的前17位
     * @return 新身份证的最后一位
     */
    private static String getCheckFlag(String code) {

        int[] varArray = new int[code.length()];
        String lastNum = "";
        int numSum = 0;
        // 初始化位权值
        setWiBuffer();
        for (int i = 0; i < code.length(); i++) {
            varArray[i] = new Integer("" + code.charAt(i)).intValue();
            varArray[i] = varArray[i] * Wi[i];
            numSum = numSum + varArray[i];
        }
        int checkDigit = 12 - numSum % 11;
        switch (checkDigit) {
            case 10:
                lastNum = "X";
                break;
            case 11:
                lastNum = "0";
                break;
            case 12:
                lastNum = "1";
                break;
            default:
                lastNum = String.valueOf(checkDigit);
        }
        return lastNum;
    }

    /**
     * 初始化位权值
     */
    private static void setWiBuffer() {
        for (int i = 0; i < Wi.length; i++) {
            int k = (int) Math.pow(2, (Wi.length - i));
            Wi[i] = k % 11;
        }
    }


}
