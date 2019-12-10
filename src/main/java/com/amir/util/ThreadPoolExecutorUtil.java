package com.amir.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */
public final class ThreadPoolExecutorUtil {
    private final static Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorUtil.class);

    private static ThreadPoolExecutor threadPool;

    /**
     *
     * 初始化线程池
     *
     * @since p2p_cloud_v1.0
     */

    static {
        int corePoolSize = 10;

        int maximumPoolSize = 20;

        long keepAliveTime = 3;

        int workQueue = 10;

        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(workQueue), new ThreadPoolExecutor.DiscardOldestPolicy());
        logger.info("创建线程池成功");
    }

    private ThreadPoolExecutorUtil() {
    }

    public synchronized static ThreadPoolExecutor getPool() {
        return threadPool;
    }

}
