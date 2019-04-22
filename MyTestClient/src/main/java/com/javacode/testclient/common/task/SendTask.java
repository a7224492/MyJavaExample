package com.javacode.testclient.common.task;

/**
 * @author jiangzhen
 * @date 2019/4/19 19:31
 */
public abstract class SendTask implements Runnable {
    public boolean hasNext() {
        return false;
    }
}
