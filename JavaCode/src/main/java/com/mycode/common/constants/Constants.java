package com.mycode.common.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class Constants {
    private static final Logger logger = LoggerFactory.getLogger(Constants.class);

    public static final int _1KB = 1024;
    public static final int _1M = 1024 * _1KB;
    public static final int _1G = 1024 * _1M;

    public static class LongTimeTask implements Runnable {
        private static AtomicInteger id = new AtomicInteger(0);

        private int longCpuTaskNum;
        private long sleepTime;

        public LongTimeTask(int longCpuTaskNum, long sleepTime) {
            this.longCpuTaskNum = longCpuTaskNum;
            this.sleepTime = sleepTime;
        }

        public LongTimeTask() {
            this.longCpuTaskNum = 100;
            this.sleepTime = 5;
        }

        @Override
        public void run() {
            for (int i = 0; i < longCpuTaskNum; ++i) {
                new LongCpuTask().run();
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.debug("id = {} finish", id.getAndIncrement());
        }
    }

    public static class LongCpuTask implements Runnable {
        private int i = 10000;
        @Override
        public void run() {
            cpu();
        }

        private int cpu() {
            if (i > 0) {
                --i;
                return cpu();
            } else {
                return i;
            }
        }
    }

    public static LongTimeTask createTask_2() {
        return new LongTimeTask(2, 1);
    }

    public static LongTimeTask createTask_10() {
        return new LongTimeTask(40, 5);
    }

    public static void main(String[] args) {
        long before = System.currentTimeMillis();
        for (int i = 0; i < 100; ++i) {
            createTask_2().run();
        }
        System.out.println((System.currentTimeMillis() - before) / 100);
    }
}
