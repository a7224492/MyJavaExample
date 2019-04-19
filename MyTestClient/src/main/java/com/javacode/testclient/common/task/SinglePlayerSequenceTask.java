package com.javacode.testclient.common.task;

import com.google.protobuf.GeneratedMessage;
import com.javacode.testclient.common.Role;
import com.javacode.testclient.common.RoleService;
import com.kodgames.corgi.core.net.Connection;
import com.kodgames.corgi.core.service.ServiceContainer;
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

    public void addTask(SequenceTask task) {
        taskQueue.offer(task);
    }

    public void start() {
        new Thread(() -> {
            while (!taskQueue.isEmpty()) {
                SequenceTask currentTask = taskQueue.poll();
                semaphore.acquireUninterruptibly();
                currentTask.start();
            }
        }).start();
    }

    public boolean handleMessage(Connection connection, GeneratedMessage message) {
        SequenceTask currentTask = taskQueue.poll();
        if (currentTask == null) {
            return true;
        }

        RoleService roleService = ServiceContainer.getInstance().getPublicService(RoleService.class);
        Role role = roleService.getRole(connection.getConnectionID());
        if (role == null) {
            logger.warn("Can't find role when handle message, connectionId={}, message={}", connection.getConnectionID(), message);
            return true;
        }

        currentTask.handleMessage(message);
        semaphore.release();

        return taskQueue.isEmpty();
    }
}
