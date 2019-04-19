package com.javacode.testclient.common.task;

import com.google.protobuf.GeneratedMessage;
import com.javacode.testclient.common.Role;

/**
 * @author jiangzhen
 * @date 2019/4/19 16:37
 */
public interface SequenceTask {
    void start();

    void handleMessage(GeneratedMessage message);
}
