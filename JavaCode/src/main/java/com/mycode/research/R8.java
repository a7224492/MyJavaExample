package com.mycode.research;

import com.kodgames.core.threadPool.OrderedThreadPoolExecutor;

import java.util.concurrent.*;

import static com.mycode.common.constants.Constants.createTask_2;


/**
 *
 * @author jiangzhen
 * @date 2019/4/28 14:29
 */
public class R8 {
//    private static final ExecutorService threadPool = Executors.newFixedThreadPool(8);  // 720
//    private static final ExecutorService threadPool = Executors.newFixedThreadPool(40);  // 968
//    private static final ExecutorService threadPool = Executors.newFixedThreadPool(70); // 1137
//    private static final ExecutorService threadPool = Executors.newFixedThreadPool(100);    // 1178
//    private static final ExecutorService threadPool = Executors.newFixedThreadPool(140);    // 1189
//    private static final ExecutorService threadPool = Executors.newFixedThreadPool(180);    // 1132
//    private static final ExecutorService threadPool = Executors.newFixedThreadPool(240);    // 1114
//    private static final ExecutorService threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new SynchronousQueue<>());
//    private static final ExecutorService threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
//    private static final ExecutorService threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200));
//    private static final ExecutorService threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(300));
//    private static final ExecutorService threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(400));
//    private static final ExecutorService threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(600));
//    private static final ExecutorService threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(800));
//    private static final ExecutorService threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1600));
//    private static final ExecutorService threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3200));
//    private static final ExecutorService threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(6400));
//    private static final ExecutorService threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000));
    private static final ExecutorService threadPool = new OrderedThreadPoolExecutor(8, 32, 0, TimeUnit.SECONDS, "jiangzhen");
    private static final int TASK_NUM = 10000;

    public static void main(String[] args) throws InterruptedException {
        long before = System.currentTimeMillis();
        for (int i = 0; i < TASK_NUM; ++i) {
//            threadPool.execute(new Task() {
//                private Constants.LongTimeTask task = new Constants.LongTimeTask();
//
//                @Override
//                public void run() {
//                    task.run();
//                }
//
//                @Override
//                public Object getKey() {
//                    return task.getSelfId();
//                }
//
//                @Override
//                public boolean isUrgency() {
//                    return false;
//                }
//            });
            threadPool.execute(createTask_2());
//            Thread.sleep(2);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        long cost = System.currentTimeMillis() - before;
        float thought = 1000 * TASK_NUM / (float)cost;

        System.out.println(thought);
    }
}
