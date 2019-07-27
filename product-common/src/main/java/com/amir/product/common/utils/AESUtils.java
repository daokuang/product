package com.amir.product.common.utils;


import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by gfl on 2018/6/14 10:45
 * Email:gfl@cheok.com.
 */
public class AESUtils {



    //编码方式
//    public static final String bm = "GBK";
    public static final String bm = "UTF-8";


    /**
     * 解密
     *
     * @param encrypted
     * @return
     */
    public static String decrypt(String encrypted, String iv, String sessionKey) {
        try {
            byte[] byteMi = new BASE64Decoder().decodeBuffer(encrypted);
            byte[] byteiv = new BASE64Decoder().decodeBuffer(iv);
            byte[] byteKey = new BASE64Decoder().decodeBuffer(sessionKey);
            IvParameterSpec zeroIv = new IvParameterSpec(byteiv);
            SecretKeySpec key = new SecretKeySpec(
                    byteKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //与加密时不同MODE:Cipher.DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte[] decryptedData = cipher.doFinal(byteMi);
            return new String(decryptedData, bm);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 测试
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        String originalText = "hello java";
//        String cipherText = encrypt(originalText, "123456789","1vZ1JrP15hr2fA1L76Obmw==");
        String decodeText = decrypt("RxSyMhxhZHKXENRyT4TwQkSR4xXzO0x4o27/RAdQ2XXb6FgndLiO8BpqB8wzqlVkxKd3VFwX/gNDmqQSneWEuwbRC8FkYxsu8qR0YsyIO8/LmYEbrlDMMvdDbNyooPVDpAMcVkLcqxQsRxyMNv99b8VXHFZj6D4hvWyCh7YjCdaribk/wNvRmRwcrumEGETg5OUTtoJD84liHW0wRCzih1xFHiwxNEzCFtTphG/HXb6kKvAOkKlP+5nVJN4sZSKMY808DGSRdlcvqAZdOf88s2FaVvW53oOvGk1j56kiG/fBI++w6S0DOptOcXDF/8ZuZ1G71P+4V8hk8M8riY/qQiuvWNxOJB7rx5Q049LPGCc+hb5gm4YIHpBmDqaGQi9GLpakbVEag5LC+OFjN38y9diKi1KSn6EJjXThHsLL+VvdUpUXOdEL++IUDRDBB7MAMCetk6ZOtbAc4gz8OQ3aF8tWEoZOwQLlWOax9Dsw9uiH5xNxXm60Jd2JTF0RDNS02xbpEMfuzhR/tH7uqZpx/A==",
                "x9S5Rfjh1D/M+uY3u4ddpg==",
                "aJZVdLfbh9YGdUf7MyQ3Dw==");
//        System.out.println("原文：" + originalText);
//        System.out.println("密文：" + cipherText);
        System.out.println("解密：" + decodeText);
    }
}
