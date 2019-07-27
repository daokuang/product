package com.amir.product.common.utils;


import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by liuyq on 2019/4/16.
 *  获取spring配置的bean
 */
public final class SpringBeanUtil {
    private static WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

    private SpringBeanUtil()
    {
    }

    /**
     * 根据bean名称获取
     *
     * @param name
     * @return
     */
    public static Object getBean(String name)
    {
        return ctx.getBean(name);
    }

    public static <T> T getBean(Class<T> type)
    {
        Assert.notNull(type);
        return ctx.getBean(type);
    }
}
