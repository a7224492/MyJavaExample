package com.mycode.common.diy;

import com.kodgames.core.threadPool.ModuleThreadFactory;
import com.kodgames.core.threadPool.task.Task;
import com.kodgames.core.threadPool.task.TaskQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangzhen
 * @date 2019/5/7 12:43
 */
public class MyKodThreadPoolExecutor extends ThreadPoolExecutor {
    private Logger logger = LoggerFactory.getLogger(MyKodThreadPoolExecutor.class);

    public MyKodThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, String threadGroupName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new ModuleThreadFactory(threadGroupName));
    }

    boolean executeTask(TaskQueue queue, MyPoolBossThread boss) {
        Task task = queue.poll();
        if (task == null) {
            return true;
        } else {
            try {
                queue.setRunning(true);
                super.execute(task);
            } catch (RejectedExecutionException var5) {
                if (task != null) {
                    queue.addFirst(task);
                }

                queue.setRunning(false);
                return false;
            } catch (Exception var6) {
                this.logger.error("executeTask exception : ", var6);
            } catch (Throwable var7) {
                this.logger.error("executeTask Throwable : ", var7);
            }

            return true;
        }
    }

    protected void afterExecute(Runnable r, Throwable t) {
        Task task = (Task)r;
        TaskQueue queue = task.getParentQueue();
        if (queue == null) {
            this.logger.warn("queue {}  task {} parent queue empty...........run finished", task.getKey(), task);
        } else {
            queue.setRunning(false);
        }
    }
}
