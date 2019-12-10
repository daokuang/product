package com.amir.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public MD5Utils() {
    }

    public static String ecodeByMD5(String originstr) {
        String result = null;
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        if (originstr != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] source = originstr.getBytes("utf-8");
                md.update(source);
                byte[] tmp = md.digest();
                char[] str = new char[32];
                int i = 0;

                for (int var8 = 0; i < 16; ++i) {
                    byte b = tmp[i];
                    str[var8++] = hexDigits[b >>> 4 & 15];
                    str[var8++] = hexDigits[b & 15];
                }

                result = new String(str);
            } catch (NoSuchAlgorithmException var10) {
                var10.printStackTrace();
            } catch (UnsupportedEncodingException var11) {
                var11.printStackTrace();
            }
        }

        return result;
    }

    public static String getMD5String(String convertStr) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var4) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(convertStr.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException var3) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }
}