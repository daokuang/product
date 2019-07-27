package com.amir.product.common.utils;


import com.amir.product.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Ycc on 2015/10/29.
 * 专门拿来抛异常的工具类，德玛西亚！！！
 */
public class CheckUtil {

    private static Logger LOGGER = null;

    public static void checkStatus(boolean status, String message) throws BusinessException {
        if (status)
            throw new BusinessException(message);
    }

    public static <T> void checkListSize(List<T> list, String message) throws BusinessException {
        CheckUtil.checkStatus(null == list || 0 == list.size(), "列表信息不允许为空");
        if (list.size() != 1) {
            throw new BusinessException(message);
        }
    }

    public static <T> Boolean checkListIsNullOrIsEmpty(List<T> list) {
        if (null == list || 0 == list.size())
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    public static void checkStatusAndPrintWarnLog(Class clazz, String message) {
        LOGGER = LoggerFactory.getLogger(clazz);
        LOGGER.warn(message);
    }

    public static void checkStatusAndPrintErrorLog(Class clazz, String message) {
        LOGGER = LoggerFactory.getLogger(clazz);
        LOGGER.error(message);
    }
}
