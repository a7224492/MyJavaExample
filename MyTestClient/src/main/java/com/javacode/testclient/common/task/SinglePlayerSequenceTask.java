package com.javacode.testclient.common.task;

import com.google.protobuf.GeneratedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * @author jiangzhen
 * @date 2019/4/19 16:13
 */
public class SinglePlayerSequenceTask {
    private static final Logger logger = LoggerFactory.getLogger(SinglePlayerSequenceTask.class);

    private Semaphore semaphore = new Semaphore(1, true);
    private Queue<SequenceTask> taskQueue = new LinkedBlockingQueue<>();
    private SequenceTask currentTask;

    public void addTask(SequenceTask task) {
        taskQueue.offer(task);
    }

    public void start() {
        new Thread(() -> {
            while (!taskQueue.isEmpty()) {
                semaphore.acquireUninterruptibly();
                currentTask = taskQueue.poll();
                currentTask.start();
            }
        }).start();
    }

    public boolean handleMessage(GeneratedMessage message) {
        if (currentTask == null) {
            return true;
        }

        currentTask.handleMessage(message);
        semaphore.release();

        return taskQueue.isEmpty();
    }
}
