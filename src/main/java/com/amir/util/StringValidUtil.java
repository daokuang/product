package com.amir.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 工具类-字符串校验
 *
 * @author yinLiang
 * @version 1.0
 * @date 2015年11月17日 下午5:40:24
 * Copyright 杭州融都科技股份有限公司 统一资金接入系统 UFX  All Rights Reserved
 * 官方网站：www.erongdu.com
 * 研发中心：rdc@erongdu.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public class StringValidUtil {

    /**
     * 检验是否为空或空字符串
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return RdStringUtil.isNull(str).equals("");
    }

    /**
     * 检验对象是否为空
     *
     * @param o
     * @return
     */
    public static boolean isBlank(Object o) {
        return RdStringUtil.isNull(o).equals("");
    }

    /**
     * 检验对象是否不为空
     *
     * @param o
     * @return
     */
    public static boolean isNotBlank(Object o) {
        return !RdStringUtil.isNull(o).equals("");
    }

    /**
     * 检验是否非空字符串
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !RdStringUtil.isNull(str).equals("");
    }

    /**
     * 检验手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        phone = RdStringUtil.isNull(phone);
        Pattern regex = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher matcher = regex.matcher(phone);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 校验是否全中文，返回true 表示是 反之为否
     *
     * @param realname
     * @return
     */
    public static boolean isChinese(String realname) {
        realname = RdStringUtil.isNull(realname);
        Pattern regex = Pattern.compile("[\\u4e00-\\u9fa5]{2,25}");
        Matcher matcher = regex.matcher(realname);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 校验邮箱格式
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        email = RdStringUtil.isNull(email);
        Pattern regex = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = regex.matcher(email);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 校验身份证号码
     *
     * @param email
     * @return
     */
    public static boolean isCard(String cardId) {
        cardId = RdStringUtil.isNull(cardId);
        // 身份证正则表达式(15位)
        Pattern isIDCard1 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
        // 身份证正则表达式(18位)
        Pattern isIDCard2 = Pattern
                .compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        Matcher matcher1 = isIDCard1.matcher(cardId);
        Matcher matcher2 = isIDCard2.matcher(cardId);
        boolean isMatched = matcher1.matches() || matcher2.matches();
        return isMatched;
    }

    /**
     * 判断字符串是否为整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (isBlank(str)) {
            return false;
        }
        Pattern regex = Pattern.compile("\\d*");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (isBlank(str)) {
            return false;
        }
        Pattern regex = Pattern.compile("(-)?\\d*(.\\d*)?");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

}
