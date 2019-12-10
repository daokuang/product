package com.amir.excel.easyexcel;

import org.springframework.core.NamedThreadLocal;

/**
 * @Auther: liuyuqing
 * @Date: 2019-12-05 17:49
 * @Description:
 * @see org.springframework.aop.framework.AopContext
 */
public class ResponseContent {
    private static final ThreadLocal<ContentModel> response = new NamedThreadLocal("Current response result");

    private ResponseContent() {
    }

    public static ContentModel currentProxy() throws IllegalStateException {
        ContentModel contentModel = response.get();
        return contentModel;
    }


    static Object setCurrentProxy(ContentModel result) {
        Object old = response.get();
        if (result != null) {
            response.set(result);
        } else {
            response.remove();
        }

        return old;
    }

    static void remove() {
        response.remove();
    }
}
