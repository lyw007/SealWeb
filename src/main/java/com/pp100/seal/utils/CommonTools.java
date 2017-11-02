package com.pp100.seal.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonTools {

    public static final String _OPTION_PRE = "##";// 投票使用的变量

    /**
     * 判断输入的字符串是否为null，如果是null则返回"",否则，返回原字符串。
     *  
     * @param str
     * @return
     */
    public static String null2String(String str) {
        if (isNullString(str)) {
            return "";
        } else
            return str;
    }

    public static String null2TrimString(String str) {
        if (isNullString(str)) {
            return "";
        } else
            return str.trim();
    }

    public static String nullObject2String(Object str) {
        if (str == null) {
            return "";
        } else
            return str.toString();
    }

    public static String nullInt2String(String str) {
        if (isNullString(str) || "".equals(str)) {
            return "0";
        } else
            return str.replaceAll(",", "");
    }

    public static String date2String(Date date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
    }

    public static boolean isNullString(String str) {
        if (str == null || str.trim().equals("") || str.trim().equalsIgnoreCase("NULL"))
            return true;
        else
            return false;
    }
}
