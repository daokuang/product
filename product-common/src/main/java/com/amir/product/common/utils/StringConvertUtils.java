package com.amir.product.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/8/14 0014.
 */
public class StringConvertUtils {

    public static String convertSpecialSet(String... str) {
        if (str == null || str.length == 0) return null;
        StringBuilder builder = new StringBuilder(",");
        Arrays.stream(str).forEach(e -> {
            if (StringUtils.isNotBlank(e)) {
                builder.append(e).append(",");
            }
        });
        if (builder.length() == 1) return null;
        return builder.substring(1, builder.length() - 1);
    }

}
