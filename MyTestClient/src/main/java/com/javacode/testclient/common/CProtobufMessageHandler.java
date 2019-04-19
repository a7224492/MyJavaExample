package com.javacode.testclient.common;

import com.google.protobuf.GeneratedMessage;
import com.kodgames.corgi.core.net.Connection;
import com.kodgames.corgi.core.net.handler.message.ProtobufMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jiangzhen
 * @date 2019/4/18 17:28
 */
public abstract class CProtobufMessageHandler<T extends GeneratedMessage> extends ProtobufMessageHandler<RoleService, T> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleMessage(Connection connection, RoleService roleService, T message, int callback) {
        logger.info("{} : {} -> {}.", message.getClass().getSimpleName(), connection.getRemotePeerID(), message);
        handleMsg(connection, roleService, message);
    }

    protected abstract void handleMsg(Connection connection, RoleService roleService, T message);
}
