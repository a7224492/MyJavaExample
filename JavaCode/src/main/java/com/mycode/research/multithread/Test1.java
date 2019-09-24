package com.mycode.research.multithread;

/**
 * @author jiangzhen
 * @date 2019/9/17 16:06
 */
public class Test1 extends Thread{
    boolean keepRunning = true;
    public static void main(String[] args) throws InterruptedException {
        Test1 t = new Test1();
        t.start();
        Thread.sleep(1000);
        t.keepRunning = false;
        System.out.println(System.currentTimeMillis() + ": keepRunning is false");
    }
    public void run() {
        for (long i = 0; i < 10000000000L; ++i) {
            int x = (int) Math.sqrt(231.2f);
        }

        System.out.println("start");

        while (keepRunning)
        {}
    }
}
