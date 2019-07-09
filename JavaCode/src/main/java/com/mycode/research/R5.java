package com.mycode.research;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试java线程池中的keepAlive
 *
 * Author: jz
 * Date: 2019/4/27 17:56
 */
public class R5 {
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,4,10, TimeUnit.SECONDS, new SynchronousQueue<>());
    public static void main(String[] args) {
        Runnable longTimeTask = () -> {
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable shortTimeTask = () -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        threadPool.execute(longTimeTask);
        threadPool.execute(longTimeTask);
        threadPool.execute(shortTimeTask);
    }
}
