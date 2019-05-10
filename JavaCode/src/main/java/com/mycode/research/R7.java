package com.mycode.research;

import io.netty.util.internal.SystemPropertyUtil;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangzhen
 * @date 2019/4/28 10:53
 */
public class R7 {
    private static int value = Math.max(1, SystemPropertyUtil.getInt("cs.corgi.server.corePoolSize", SystemPropertyUtil.getInt("corgi.server.corePoolSize", 8)));
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,8,10, TimeUnit.SECONDS, new SynchronousQueue<>());

    public static void main(String[] args) throws InterruptedException {
        threadPool.execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(4000);
        threadPool.execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}
