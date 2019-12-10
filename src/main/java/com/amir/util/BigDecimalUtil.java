package com.amir.util;

import java.math.BigDecimal;

/**
 * 工具类-BigDecimal算法
 */
public class BigDecimalUtil {

    private static int DEF_DIV_SCALE = 10; // 默认精确的小数位

    private static int DIV_SCALE_TWO = 2; // 默认精确的小数位


    /**
     * 提供精确的加法运算。
     *
     * @param params 参数数组
     * @return 和
     */
    public static double add(double... params) {
        BigDecimal b1 = new BigDecimal(0);
        for (double param : params) {
            BigDecimal b2 = new BigDecimal(Double.toString(param));
            b1 = b1.add(b2);
        }
        return b1.doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * param[0] - param[1] - param[2] ...
     * 当数组长度为1时，即没有可以减的数时，返回param[0]
     *
     * @param params 参数数组
     * @return 多个参数的差
     */
    public static double sub(double... params) {

        int len = params.length;
        if (len < 1) {
            return 0;
        }

        BigDecimal b1 = new BigDecimal(Double.toString(params[0]));
        for (int i = 1; i < len; i++) {
            BigDecimal b2 = new BigDecimal(Double.toString(params[i]));
            b1 = b1.subtract(b2);
        }
        return b1.doubleValue();

    }

    /**
     * 提供精确的乘法运算。
     *
     * @param params 参数数组
     * @return 动态参数的积
     */
    public static double mul(double... params) {
        BigDecimal b1 = new BigDecimal(1);
        for (double param : params) {
            BigDecimal b2 = new BigDecimal(Double.toString(param));
            b1 = b1.multiply(b2);
        }
        return b1.doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DIV_SCALE_TWO);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位(2位)四舍五入处理。
     *
     * @param v 需要四舍五入的数字
     * @return 四舍五入后的结果
     */
    public static double round(double v) {
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字字符串
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位(2位)四舍五入处理。
     *
     * @param v     需要四舍五入的数字字符串
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(String v) {
        if (StringValidUtil.isBlank(v)) {
            return 0;
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位处理，去掉保留位数后的数字
     *
     * @param v     需要处理的数字
     * @param scale 小数点后保留几位
     * @return 去掉保留位数后的结果
     */
    public static double decimal(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 根据字符串获取数据
     *
     * @param str
     * @return
     */
    public static BigDecimal getBigDecimal(String str) {
        if (!StringValidUtil.isNumber(str)) {
            str = "0";
        }
        BigDecimal decimal = new BigDecimal(str);
        return decimal.setScale(4, BigDecimal.ROUND_HALF_EVEN);
    }
}
