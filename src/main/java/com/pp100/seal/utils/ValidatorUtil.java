package com.pp100.seal.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


public class ValidatorUtil {

    /**
     * 验证企业代码是否正确
     * 
     * @param code
     * @return
     */
    public static final boolean isValidEntpCode(String code) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        int[] ws = { 3, 7, 9, 10, 5, 8, 4, 2 };
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String regex = "^([0-9A-Z]){8}-[0-9|X]$";
        if (!code.matches(regex)) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            sum += str.indexOf(String.valueOf(code.charAt(i))) * ws[i];
        }
        int c9 = 11 - (sum % 11);
        String sc9 = String.valueOf(c9);
        if (11 == c9) {
            sc9 = "0";
        } else if (10 == c9) {
            sc9 = "X";
        }
        return sc9.equals(String.valueOf(code.charAt(9)));
    }

    /**
     * 验证企业统一信用代码
     * 
     * @param creditCode
     * @return
     */
    public static boolean isValidCreditCode(String creditCode) {
        if (StringUtils.isBlank(creditCode) || (creditCode.equals("")) || creditCode.length() != 18) {
            return false;
        }
        String baseCode = "0123456789ABCDEFGHJKLMNPQRTUWXY";
        char[] baseCodeArray = baseCode.toCharArray();
        Map<Character, Integer> codes = new HashMap<Character, Integer>();
        for (int i = 0; i < baseCode.length(); i++) {
            codes.put(baseCodeArray[i], i);
        }
        char[] businessCodeArray = creditCode.toCharArray();
        Character check = businessCodeArray[17];
        if (baseCode.indexOf(check) == -1) {
            return false;
        }
        int[] wi = { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28 };
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            Character key = businessCodeArray[i];
            if (baseCode.indexOf(key) == -1) {
                return false;
            }
            sum += (codes.get(key) * wi[i]);
        }
        int value = 31 - sum % 31;
        if(value == 31){
            value = 0;
        }
        return value == codes.get(check);
    }

    public static void main(String[] args) {
         //System.out.println(isValidEntpCode("16306290-0"));
        // System.out.println(isValidEntpCode("16306296-X"));
        // System.out.println(isValidEntpCode("16306300-6"));
        // System.out.println(isValidEntpCode("16306493-0"));
        // System.out.println(isValidEntpCode(""));
        // System.out.println(isValidEntpCode(null));
        //System.out.println(isValidEntpCode("05555990-1"));
        
        // System.out.println("=====================================");
        // System.out.println(isValidCreditCode("12330109560571805W"));
        // System.out.println( isValid("123301094704550341"));
         System.out.println( isValidCreditCode("911101050555599010"));
         //System.out.println( isValidCreditCode("91370200163562681G"));
        // System.out.println( isValid("52440111MJK9980480"));
        // System.out.println( isValid("12330109560571805W"));
        // System.out.println( isValid("91510000779827684C"));
    }
}