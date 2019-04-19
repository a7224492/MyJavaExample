package com.javacode.testclient.common.task;

import com.google.protobuf.GeneratedMessage;
import com.kodgames.corgi.core.net.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jiangzhen
 * @date 2019/4/19 16:14
 */
public class MultiPlayerSequenceTask {
    private static final Logger logger = LoggerFactory.getLogger(MultiPlayerSequenceTask.class);
    private Queue<SequenceTask> taskQueue = new LinkedBlockingQueue<>();

    public void addRole(SequenceTask task) {
        taskQueue.offer(task);
    }

    public void handleMessage(Connection connection, GeneratedMessage message) {
        SequenceTask currentTask = taskQueue.peek();
        if (currentTask == null) {
            logger.info("No task need handle");
            return;
        }
    }
}
