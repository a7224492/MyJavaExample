package com.mycode;

/**
 * @author jiangzhen
 * @date 2019/3/29 13:58
 */
public class Test3 implements Runnable {
    private int x;
    private int y;

    public static void main(String[] args) throws InterruptedException {
        Test3 t3 = new Test3();
        Thread t1 = new Thread(t3);
        Thread t2 = new Thread(t3);

        t1.start();
        t2.start();
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < 10000; ++i) {
            ++x;
            ++y;

            System.out.println("x=" + x + ", y=" + y);
        }
    }
}
