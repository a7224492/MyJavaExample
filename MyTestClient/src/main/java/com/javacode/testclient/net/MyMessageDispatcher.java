package com.javacode.testclient.net;

import com.google.protobuf.GeneratedMessage;
import com.javacode.testclient.common.Role;
import com.javacode.testclient.common.RoleService;
import com.kodgames.corgi.core.net.Connection;
import com.kodgames.corgi.core.net.message.InternalMessage;
import com.kodgames.corgi.core.service.ServiceContainer;
import com.kodgames.message.proto.club.ClubProtoBuf;
import com.kodgames.message.proto.game.GameProtoBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jiangzhen
 * @date 2019/4/19 18:01
 */
public class MyMessageDispatcher {
    private static Logger logger = LoggerFactory.getLogger(MyMessageDispatcher.class);
    private static final MyMessageDispatcher instance = new MyMessageDispatcher();

    private static final Set<Class> MASK_MESSAGE_SET = new HashSet<>();

    static {
        MASK_MESSAGE_SET.add(GameProtoBuf.GCPushParameterSYN.class);
        MASK_MESSAGE_SET.add(ClubProtoBuf.CLCClubDataSYN.class);
    }

    public static MyMessageDispatcher getInstance() {
        return instance;
    }

    public void dispatchMessage(Connection connection, InternalMessage internalMessage) {
        RoleService roleService = ServiceContainer.getInstance().getPublicService(RoleService.class);
        Role role = roleService.getRole(connection.getConnectionID());
        if (role == null) {
            logger.warn("Unkown message handler role, connectionId={}, message={}", connection.getConnectionID(), internalMessage.getMessage());
            return;
        }

        Object message = internalMessage.getMessage();
        if (!MASK_MESSAGE_SET.contains(message.getClass())) {
            logger.info("{} : {} -> {}.", role.getRoleId(), message.getClass().getSimpleName(), message);
        }

        role.getSequenceTask().handleMessage((GeneratedMessage) internalMessage.getMessage());
    }
}
