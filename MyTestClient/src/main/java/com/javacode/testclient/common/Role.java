package com.javacode.testclient.common;

import com.google.protobuf.GeneratedMessage;
import com.kodgames.corgi.core.net.Connection;
import com.kodgames.corgi.core.net.handler.AbstractMessageInitializer;
import com.kodgames.corgi.core.session.ConnectionManager;
import com.kodgames.message.proto.auth.AuthProtoBuf;
import com.kodgames.message.proto.game.GameProtoBuf;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.Semaphore;

import static com.javacode.testclient.constant.Constants.MINI_WECHAT_SHARE_INVITER;
import static com.javacode.testclient.constant.Constants.RANDOM_USER;

/**
 * Created by liufei on 2017/6/23. 每个Role代表一个角色(玩家)
 */
public class Role {
    private static Logger logger = LoggerFactory.getLogger(Role.class);
    public static final AttributeKey<Role> ROLEINFO = AttributeKey.valueOf("ROLEINFO");
    private static AbstractMessageInitializer msgInitializer = ConnectionManager.getInstance().getMsgInitializer();

    private Connection interfaceConnection = null;

    private int gameServerId;
    private int clubServerId;
    private int battleServerId;
    private int campaignServerId;
    private int replayServerId;
    private int replayNonZdbId;

    private int roleId = 0;
    private String username;
    private String nickName;
    private String headImg;
    private int sex = 0;
    private int areaId = 0;
    private String channel;
    private int channelId;
    private String unionId;
    private String signature;
    private String developerId;

    private int campaignId = 0;
    private int roomId = 0;
    // 请求进入的房间号
    private int requestEnterRoomId = 0;


    private static boolean replayServerFlag = true;

    public Role(Connection conn) {
        interfaceConnection = conn;

        this.username = "jztest" + (RANDOM_USER ? UUID.randomUUID().toString() : "") + String.valueOf(interfaceConnection.getConnectionID());
    }

    public int getRoleId() {
        return roleId;
    }

    public String getUsername() {
        return username;
    }

    static  {
        msgInitializer.setHandler(AuthProtoBuf.ICAccountAuthRES.class, RoleService.class, new CProtobufMessageHandler<AuthProtoBuf.ICAccountAuthRES>() {
            @Override
            protected void handleMsg(Connection connection, RoleService roleService, AuthProtoBuf.ICAccountAuthRES message) {
                Role role = roleService.getRole(connection.getConnectionID());
                role.roleId = message.getAccountId();
                role.username = message.getUsername();
                role.nickName = message.getNickname();
                role.headImg = message.getHeadImageUrl();
                role.sex = message.getSex();
                role.channel = message.getChannel();
                role.unionId = message.getUnionId();
                role.signature = message.getSignature();
                role.developerId = message.getDeveloperId();
                role.gameServerId = message.getGameServerId();
                role.clubServerId = message.getClubServerId();

                role.interfaceConnection.setRemotePeerID(role.roleId);

                role.semaphore.release();
            }
        });

        msgInitializer.setHandler(GameProtoBuf.GCLoginRES.class, RoleService.class, new CProtobufMessageHandler<GameProtoBuf.GCLoginRES>() {
            @Override
            protected void handleMsg(Connection connection, RoleService roleService, GameProtoBuf.GCLoginRES message) {
                Role role = roleService.getRole(connection.getConnectionID());
                role.semaphore.release();
            }
        });
    }

    public int getConnectionId() {
        return interfaceConnection.getConnectionID();
    }

    // 根据不同的消息类型得到destination server id
    @SuppressWarnings("rawtypes")
    public int getDestServerId(Class messageClass) {
        String name = messageClass.getSimpleName();

        if (name.startsWith("CG")) {
            // game server id
            return gameServerId;
        } else if (name.startsWith("CCL")) {
            // club server id
            return clubServerId;
        } else if (name.startsWith("CB")) {
            // battle server
            return battleServerId;
        } else if (name.startsWith("CCA")) {
            // battle server
            return campaignServerId;
        } else if (name.startsWith("CR")) {
            // replay server
            if (replayNonZdbId > 0) {
                // 如果有两个replay server, 每次交换返回一个serverId
                replayServerFlag = !replayServerFlag;
                if (replayServerFlag)
                    return replayServerId;
                else
                    return replayNonZdbId;
            } else
                return replayServerId;
        } else if (name.startsWith("CI")) {
            // interface server
            return 0;
        }

        logger.warn("unknown server type for message class {}", messageClass.getName());
        return 0;
    }

    private void sendMessage(GeneratedMessage message) {
        interfaceConnection.write(RoleService.callbackSeed.getAndIncrement(), message);
    }

    public void sendAuth() {
        semaphore.acquireUninterruptibly();

        AuthProtoBuf.CIAccountAuthREQ.Builder builder = AuthProtoBuf.CIAccountAuthREQ.newBuilder();
        builder.setChannel("test");
        builder.setRefreshToken("");
        builder.setCode("");
        builder.setProVersion("100000");
        builder.setSubChannel(0);
        builder.setUpdateChannel(0);
        builder.setUsername(username);

        sendMessage(builder.build());
    }

    public void login(){
        semaphore.acquireUninterruptibly();

        GameProtoBuf.CGLoginREQ.Builder builder = GameProtoBuf.CGLoginREQ.newBuilder();
        builder.setAccountId(roleId);
        builder.setArea(areaId);
        builder.setChannel(channel);
        builder.setChannelId(channelId);
        builder.setHeadImageUrl(headImg);
        builder.setNickname(nickName);
        builder.setRoleId(roleId);
        builder.setSex(sex);
        builder.setSignature(signature);
        builder.setUnionId(unionId);
        builder.setDeveloperId(developerId);
        builder.setUsername(username);

        sendMessage(builder.build());
    }

    public void miniGameLogin() {
        semaphore.acquireUninterruptibly();

        GameProtoBuf.CGLoginREQ.Builder builder = GameProtoBuf.CGLoginREQ.newBuilder();
        builder.setAccountId(roleId);
        builder.setArea(areaId);
        builder.setChannel(channel);
        builder.setChannelId(channelId);
        builder.setHeadImageUrl(headImg);
        builder.setNickname(nickName);
        builder.setRoleId(roleId);
        builder.setSex(sex);
        builder.setSignature(signature);
        builder.setUnionId(unionId);
        builder.setDeveloperId(developerId);
        builder.setUsername(username);
        builder.setMiniGameInviter(MINI_WECHAT_SHARE_INVITER);

        sendMessage(builder.build());
    }

    public void release() {
        semaphore.release();
    }
}
