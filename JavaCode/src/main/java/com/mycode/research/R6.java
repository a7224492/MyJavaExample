package com.mycode.research;

import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue
 *
 * Author: jiangzhen
 * Date: 2019/4/27 20:27
 */
public class R6 {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>();

//        Thread t = new Thread(() -> {
//            System.out.println(queue.poll());
//            System.out.println(queue.poll());
//            System.out.println(queue.poll());
//        });
//        queue.offer("e1");
//        queue.offer("e2");

//        t.start();

        new Thread(() -> {


        });
        queue.put("e1");
        System.out.println("put e1");
        queue.put("e2");
    }
}
