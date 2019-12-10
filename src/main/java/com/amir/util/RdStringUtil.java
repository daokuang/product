package com.amir.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class RdStringUtil extends StringUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RdStringUtil.class);

    /**
     * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
     *
     * @param str
     * @return
     */
    public static String isNull(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    /**
     * 将对象转为字符串
     *
     * @param o
     * @return
     */
    public static String isNull(Object o) {
        if (o == null) {
            return "";
        }
        String str = "";
        if (o instanceof String) {
            str = (String) o;
        } else {
            str = o.toString();
        }
        return str.trim();
    }

    /**
     * 首字母大写
     *
     * @param s
     * @return
     */
    public static String firstCharUpperCase(String s) {
        StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
        sb.append(s.substring(1, s.length()));
        return sb.toString();
    }

    /**
     * 首字母小写
     *
     * @param s
     * @return
     */
    public static String firstCharLowerCase(String s) {
        StringBuffer sb = new StringBuffer(s.substring(0, 1).toLowerCase());
        sb.append(s.substring(1, s.length()));
        return sb.toString();
    }


    /**
     * 隐藏头几位
     *
     * @param str
     * @param len
     * @return
     */
    public static String hideFirstChar(String str, int len) {
        if (str == null)
            return null;
        char[] chars = str.toCharArray();
        if (str.length() <= len) {
            for (int i = 0; i < chars.length; i++) {
                chars[i] = '*';
            }
        } else {
            for (int i = 0; i < 1; i++) {
                chars[i] = '*';
            }
        }
        str = new String(chars);
        return str;
    }

    /**
     * 隐藏末几位
     *
     * @param str
     * @param len
     * @return
     */
    public static String hideLastChar(String str, int len) {
        if (str == null)
            return null;
        char[] chars = str.toCharArray();
        if (str.length() <= len) {
            return hideLastChar(str, str.length() - 1);
        } else {
            for (int i = chars.length - 1; i > chars.length - len - 1; i--) {
                chars[i] = '*';
            }
        }
        str = new String(chars);
        return str;
    }

    /**
     * 指定起始位置字符串隐藏
     *
     * @param str
     * @param index1
     * @param index2
     * @return
     */
    public static String hideStr(String str, int index1, int index2) {
        if (str == null)
            return null;
        String str1 = str.substring(index1, index2);
        String str2 = str.substring(index2);
        String str3 = "";
        if (index1 > 0) {
            str1 = str.substring(0, index1);
            str2 = str.substring(index1, index2);
            str3 = str.substring(index2);
        }
        char[] chars = str2.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = '*';
        }
        str2 = new String(chars);
        String str4 = str1 + str2 + str3;
        return str4;
    }

    /**
     * Object数组拼接为字符串
     *
     * @param args
     * @return
     */
    public static String contact(Object[] args) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i < args.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * Long数组拼接为字符串
     *
     * @param args
     * @return
     */
    public static String contact(long[] args) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i < args.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 数字数组拼接为字符串
     *
     * @param arr
     * @return
     */
    public static String array2Str(int[] arr) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            s.append(arr[i]);
            if (i < arr.length - 1) {
                s.append(",");
            }
        }
        return s.toString();
    }

    /**
     * 字符串数组转换为数字数组
     *
     * @param strarr
     * @return
     */
    public static int[] strarr2intarr(String[] strarr) {
        int[] result = new int[strarr.length];
        for (int i = 0; i < strarr.length; i++) {
            result[i] = Integer.parseInt(strarr[i]);
        }
        return result;
    }

    /**
     * 大写字母转成“_”+小写 驼峰命名转换为下划线命名
     *
     * @param str
     * @return
     */
    public static String toUnderline(String str) {
        char[] charArr = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        sb.append(charArr[0]);
        for (int i = 1; i < charArr.length; i++) {
            if (charArr[i] >= 'A' && charArr[i] <= 'Z') {
                sb.append('_').append(charArr[i]);
            } else {
                sb.append(charArr[i]);
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 下划线改成驼峰样子
     *
     * @param str
     * @return
     */
    public static String clearUnderline(String str) {
        char[] charArr = isNull(str).toLowerCase().toCharArray();
        StringBuffer sb = new StringBuffer();
        sb.append(charArr[0]);
        boolean isClear = false;
        for (int i = 1; i < charArr.length; i++) {
            if (charArr[i] == '_') {
                isClear = true;
                continue;
            }
            if (isClear && (charArr[i] >= 'a' && charArr[i] <= 'z')) {
                char c = (char) (charArr[i] - 32);
                sb.append(c);
                isClear = false;
            } else {
                sb.append(charArr[i]);
            }

        }
        return sb.toString();
    }

    /**
     * String to int
     *
     * @param str
     * @return
     */
    public static int toInt(String str) {
        if (StringValidUtil.isBlank(str))
            return 0;
        int ret = 0;
        try {
            ret = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }

    /**
     * String to Byte
     *
     * @param str
     * @return
     */
    public static byte toByte(String str) {
        if (StringValidUtil.isBlank(str))
            return 0;
        byte ret = 0;
        try {
            ret = Byte.parseByte(str);
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }

    /**
     * String to Long
     *
     * @param str
     * @return
     */
    public static long toLong(String str) {
        if (StringValidUtil.isBlank(str))
            return 0L;
        long ret = 0;
        try {
            ret = Long.parseLong(str);
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }

    /**
     * String[] to long[]
     *
     * @param str
     * @return
     */
    public static long[] toLongs(String[] str) {
        if (str == null || str.length < 1)
            return new long[]{0L};
        long[] ret = new long[str.length];
        ret = (long[]) ConvertUtils.convert(str, long.class);
        return ret;
    }

    /**
     * String[] to double[]
     *
     * @param str
     * @return
     */
    public static double[] toDoubles(String[] str) {

        if (str == null || str.length < 1)
            return new double[]{0L};
        double[] ret = new double[str.length];
        for (int i = 0; i < str.length; i++) {
            ret[i] = toDouble(str[i]);
        }
        return ret;
    }

    /**
     * String to Double
     *
     * @param str
     * @return
     */
    public static double toDouble(String str) {
        if (StringValidUtil.isBlank(str))
            return 0;
        try {
            return BigDecimalUtil.round(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 生成指定长度的随机字符串，字母加数字组合
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数  
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字  
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 删除字符串中包含的 html 元素 <>
     *
     * @param str
     * @return
     */
    public static String removeHtml(String str) {
        str = isNull(str);
        Pattern regex = Pattern.compile("<.+?>");
        Matcher matcher = regex.matcher(str);
        return matcher.replaceAll("");
    }

    /**
     * @param @param  jsonString
     * @param @return
     * @throws
     * @Description: 过滤参数json串中的空值，避免数据库存储太多无用数据
     */
    public static String reBuildReqParamsToJson(String jsonString) {
        if (StringValidUtil.isNotBlank(jsonString)) {
            JSONObject jsonObject = JSONObject.parseObject(jsonString);

            Iterator<Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
            Map<String, Object> rtMap = new HashMap<String, Object>();
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (entry.getValue() == null) {
                    continue;
                }
                if (entry.getValue().getClass().equals(String.class)) {
                    if (StringValidUtil.isNotBlank(entry.getValue().toString())) {
                        rtMap.put(entry.getKey(), entry.getValue());
                    }
                } else if (entry.getValue().getClass().isArray()) {
                    Object[] objs = (Object[]) entry.getValue();
                    if (objs.length > 0) {
                        rtMap.put(entry.getKey(), entry.getValue());
                    }
                } else {
                    rtMap.put(entry.getKey(), entry.getValue());
                }
            }
            jsonString = JSONObject.toJSONString(rtMap);
            return jsonString;
        } else {
            return "";
        }
    }

    /**
     * 字符串解码UTF-8
     *
     * @param str
     * @return
     */
    public static String urlDecoderUTF8(String str) {
        try {
            if (StringValidUtil.isNotBlank(str)) {
                return URLDecoder.decode(str, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("URL解码异常：" + str + ":" + e.getMessage());
        }
        return "";
    }

    /**
     * 字符串编码UTF-8
     *
     * @param str
     * @return
     */
    public static String urlEncoderUTF8(String str) {
        try {
            if (StringValidUtil.isNotBlank(str)) {
                return URLEncoder.encode(str, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字符串解码
     *
     * @param str
     * @return
     */
    public static String urlDecoder(String str, String enc) {
        try {
            if (StringValidUtil.isNotBlank(str)) {
                return URLDecoder.decode(str, enc);
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("URL解码异常：" + str + ":" + e.getMessage());
        }
        return "";
    }

    /**
     * 字符串编码
     *
     * @param str
     * @return
     */
    public static String urlEncoder(String str, String enc) {
        try {
            if (StringValidUtil.isNotBlank(str)) {
                return URLEncoder.encode(str, enc);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Unicode解码
     *
     * @param str
     * @return
     */
    public static String unicodeDecoder(String str) {
        char aChar;
        int len = str.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = str.charAt(x++);
            if (aChar == '\\') {
                aChar = str.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = str.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }


    /**
     * 将元转换成分
     *
     * @param money
     * @return
     */
    public static String yuanConvertFen(double money) {
        double fenMoney = BigDecimalUtil.mul(money, 100);
        return String.valueOf((long) fenMoney);
    }

    /**
     * 将元转换成分
     *
     * @param money
     * @return
     */
    public static String yuanConvertFen(String money) {
        double fenMoney = BigDecimalUtil.mul(toDouble(money), 100);
        return String.valueOf((long) fenMoney);
    }

    /**
     * 字符转义，如将数据库查询出来的"转换为&quot;
     *
     * @param strData
     * @param regex
     * @param replacement
     * @return
     */
    public static String replaceString(String strData, String regex, String replacement) {
        if (strData == null) {
            return null;
        }
        int index;
        index = strData.indexOf(regex);
        String strNew = "";
        if (index >= 0) {
            while (index >= 0) {
                strNew += strData.substring(0, index) + replacement;
                strData = strData.substring(index + regex.length());
                index = strData.indexOf(regex);
            }
            strNew += strData;
            return strNew;
        }
        return strData;
    }

    /**
     * 判断纯数字
     *
     * @param str
     * @return
     */
    public static boolean IsNumber(String str) {
        Pattern rule = Pattern.compile("[0-9]*");
        return rule.matcher(str).matches();
    }

}
