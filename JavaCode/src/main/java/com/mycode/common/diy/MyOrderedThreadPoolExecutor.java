package com.mycode.common.diy;

/**
 * @author jiangzhen
 * @date 2019/5/7 12:42
 */
public class MyOrderedThreadPoolExecutor{
//    private MyPoolBossThread boss;
//
//    public MyOrderedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, String threadGroupName) {
//        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new SynchronousQueue(), threadGroupName);
//        this.boss = new MyPoolBossThread(this);
//        this.boss.start();
//    }
//
//    public MyOrderedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, String threadGroupName, MetricRegistry registry) {
//        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new SynchronousQueue(), threadGroupName);
//        this.boss = new MyPoolBossThread(this, registry);
//        this.boss.start();
//    }
//
//    public void execute(Runnable command) {
//        if (command instanceof Task) {
//            this.boss.addTask((Task)command);
//        } else {
//            throw new RuntimeException("OrderedThreadPoolExecutor only execute " + Task.class.getName());
//        }
//    }
//
//    public int getTaskQueueSize(Object key) {
//        return this.boss.getQueueSize(key);
//    }
//
//    public boolean isQueueEmpty(Object key) {
//        return this.boss.isQueueEmpty(key);
//    }
//
//    public int getCacheTaskNum() {
//        return this.boss.getCacheTaskNum();
//    }
}
