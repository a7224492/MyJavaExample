package com.javacode.testclient.constant;

import com.google.protobuf.GeneratedMessage;
import com.javacode.testclient.common.CProtobufMessageHandler;
import com.javacode.testclient.common.RoleService;
import com.kodgames.corgi.core.net.Connection;

/**
 * @author jiangzhen
 * @date 2019/4/18 16:08
 */
public class Constants {
    public static final String INTERFACE_IP = "172.16.2.113";
    public static final int INTERFACE_PORT = 3671;

    public static final boolean RANDOM_USER = true;
    public static final int PLAYER_NUM = 4;

    public static final int MINI_WECHAT_SHARE_INVITER = 5834264;

    public static final CProtobufMessageHandler<GeneratedMessage> DEFAULT_MESSAGE_HANDLER = new CProtobufMessageHandler<GeneratedMessage>() {
        @Override
        protected void handleMsg(Connection connection, RoleService roleService, GeneratedMessage message) {

        }
    };
}
