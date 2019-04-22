package com.javacode.testclient.common.task;

import com.google.protobuf.GeneratedMessage;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jiangzhen
 * @date 2019/4/19 19:29
 */
public class TestQueue {
    private static final TestQueue instance = new TestQueue();

    public static TestQueue getInstance() {
        return instance;
    }

    private Queue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    public void addTask(Runnable task) {
        taskQueue.offer(task);
    }

    public void start() {
        Runnable task = taskQueue.poll();
        if (task == null) {
            return;
        }

        task.run();
    }

    public void handleMessage(Object message) {
        Runnable task = taskQueue.peek();
        if (!(task instanceof RecvTask)) {
            return;
        }

        RecvTask recvTask = (RecvTask)task;
        if (recvTask.getTestMessageClazz().equals(message.getClass())) {
            recvTask = (RecvTask)taskQueue.poll();
            recvTask.handle((GeneratedMessage) message);
            sendTaskRun();
        }
    }

    private void sendTaskRun() {
        while (!taskQueue.isEmpty()) {
            Runnable task = taskQueue.peek();
            if (!(task instanceof SendTask)) {
                break;
            }

            task = taskQueue.poll();
            SendTask sendTask = (SendTask)task;
            sendTask.run();

            if (!sendTask.hasNext()) {
                break;
            }
        }
    }
}
