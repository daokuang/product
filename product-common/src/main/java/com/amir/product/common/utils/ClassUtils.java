package com.amir.product.common.utils;

/**
 * Created by liuyq on 2019/4/23.
 */
public abstract class ClassUtils {

    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader c = null;

        try {
            c = Thread.currentThread().getContextClassLoader();
        }catch (Exception e){}
        if(null == c){
            c = ClassUtils.class.getClassLoader();
            if(null == c){
                try {
                    c =  ClassLoader.getSystemClassLoader();
                }catch (Exception e){}
            }
        }
        return  c;
    }
}
