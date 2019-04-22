package com.javacode.testclient.common.task;

import com.google.protobuf.GeneratedMessage;

public abstract class RecvTask implements Runnable{
    public abstract Class<?> getTestMessageClazz();

    public abstract void handle(GeneratedMessage message);

    public void run() {

    }
}
