package com.mycode.research.multithread;

import org.apache.kafka.common.metrics.stats.Count;

import java.util.concurrent.CountDownLatch;

/**
 * @author jiangzhen
 * @date 2019/9/17 16:06
 */
public class Test2 extends Thread{
    private static final ThreadLocal<Counter> threadLocal = new ThreadLocal<>();
    private static Counter COUNTER = new Counter();

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        threadLocal.set(COUNTER);
        new Thread(() -> {
            threadLocal.set(COUNTER);
            try {
                latch.await();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Counter counter = threadLocal.get();
            System.out.println(Thread.currentThread().getName() + ": " +counter.getCount());
        }).start();

        Counter counter = threadLocal.get();
        System.out.println(Thread.currentThread().getName() + ": " + COUNTER.getCount());
        latch.countDown();
    }
}

class Counter {
    private int count = 0;
    public void add() {
        ++count;
    }

    public int getCount() {
        return count;
    }
}
