package com.mycode.common.diy;

/**
 * @author jiangzhen
 * @date 2019/5/7 12:43
 */
public class MyPoolBossThread extends Thread{
//    private MyKodThreadPoolExecutor executor;
//    private TaskQueue taskQueue = new TaskQueue((Object)null);
//    private Map<Object, TaskQueue> tasks = new ConcurrentHashMap(1000);
//    private TaskQueue curQueue = null;
//    private Logger logger = LoggerFactory.getLogger(MyPoolBossThread.class);
//
//    public MyPoolBossThread(MyKodThreadPoolExecutor executor) {
//        this.executor = executor;
//    }
//
//    public MyPoolBossThread(MyKodThreadPoolExecutor executor, MetricRegistry registry) {
//        this.executor = executor;
//    }
//
//    public void run() {
//        while(true) {
//            if (!this.taskQueue.isRunning()) {
//                this.executor.executeTask(this.taskQueue, this);
//            }
//
//            Iterator iter = this.tasks.entrySet().iterator();
//
//            while(iter.hasNext()) {
//                Map.Entry<Object, TaskQueue> entry = (Map.Entry)iter.next();
//                if (this.curQueue == null || ((TaskQueue)entry.getValue()).equals(this.curQueue)) {
//                    TaskQueue queue = (TaskQueue)entry.getValue();
//                    if (!queue.isRunning()) {
//                        if (!this.executor.executeTask(queue, this)) {
//                            if (!queue.isEmpty()) {
//                                this.curQueue = queue;
//                            }
//                            break;
//                        }
//
//                        this.curQueue = null;
//                    }
//                }
//            }
//
//            Map var8 = this.tasks;
//            synchronized(this.tasks) {
//                iter = this.tasks.entrySet().iterator();
//
//                while(iter.hasNext()) {
//                    Map.Entry<Object, TaskQueue> entry = (Map.Entry)iter.next();
//                    TaskQueue queue = (TaskQueue)entry.getValue();
//                    if (queue.isEmpty() && !queue.isRunning()) {
//                        iter.remove();
//                    }
//                }
//            }
//
//            try {
//                Thread.sleep(1L);
//            } catch (InterruptedException var6) {
//                ;
//            }
//        }
//    }
//
//    void addTask(Task task) {
//        if (task != null) {
//            if (task.getKey() == null) {
//                if (task.isUrgency()) {
//                    this.taskQueue.addFirst(task);
//                } else {
//                    this.taskQueue.offer(task);
//                }
//
//                this.logger.trace("after add null key queue size {}", this.taskQueue.getSize());
//            } else {
//                Map var3 = this.tasks;
//                TaskQueue queue;
//                synchronized(this.tasks) {
//                    queue = (TaskQueue)this.tasks.get(task.getKey());
//                    if (queue == null) {
//                        queue = new TaskQueue(task.getKey());
//                        this.tasks.put(task.getKey(), queue);
//                    }
//
//                    if (task.isUrgency()) {
//                        queue.addFirst(task);
//                    } else {
//                        queue.offer(task);
//                    }
//                }
//
//                this.logger.trace("after add key {} queue size {}", task.getKey(), queue.getSize());
//            }
//
//        }
//    }
//
//    boolean isBusy() {
//        return false;
//    }
//
//    public int getCacheTaskNum() {
//        int result = this.taskQueue.getSize();
//
//        for(Iterator iter = this.tasks.entrySet().iterator(); iter.hasNext(); result += ((TaskQueue)((Map.Entry)iter.next()).getValue()).getSize()) {
//            ;
//        }
//
//        return result;
//    }
//
//    public void waitFinished() {
//        while(true) {
//            while(true) {
//                try {
//                    if (this.taskQueue.isEmpty()) {
//                        Map var1 = this.tasks;
//                        synchronized(this.tasks) {
//                            if (this.tasks.isEmpty()) {
//                                return;
//                            }
//
//                            Iterator iter = this.tasks.entrySet().iterator();
//
//                            while(iter.hasNext()) {
//                                Map.Entry<Object, TaskQueue> entry = (Map.Entry)iter.next();
//                                TaskQueue queue = (TaskQueue)entry.getValue();
//                                if (queue.isEmpty()) {
//                                    continue;
//                                }
//                            }
//                        }
//
//                        Thread.sleep(100L);
//                    } else {
//                        Thread.sleep(100L);
//                    }
//                } catch (InterruptedException var7) {
//                    var7.printStackTrace();
//                }
//            }
//        }
//    }
//
//    int getQueueSize(Object key) {
//        if (key == null) {
//            return this.taskQueue.getSize();
//        } else {
//            Map var2 = this.tasks;
//            synchronized(this.tasks) {
//                TaskQueue queue = (TaskQueue)this.tasks.get(key);
//                return queue != null ? queue.getSize() : 0;
//            }
//        }
//    }
//
//    boolean isQueueEmpty(Object key) {
//        if (key == null) {
//            return this.taskQueue.isEmpty();
//        } else {
//            Map var2 = this.tasks;
//            synchronized(this.tasks) {
//                TaskQueue queue = (TaskQueue)this.tasks.get(key);
//                return queue != null ? queue.isEmpty() : true;
//            }
//        }
//    }
}
