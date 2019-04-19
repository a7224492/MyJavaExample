package com.javacode.testclient.common;

import com.google.protobuf.GeneratedMessage;
import com.javacode.testclient.common.task.SequenceTask;
import com.javacode.testclient.common.task.SinglePlayerSequenceTask;
import com.kodgames.corgi.core.net.Connection;
import com.kodgames.corgi.core.net.handler.AbstractMessageInitializer;
import com.kodgames.corgi.core.session.ConnectionManager;
import com.kodgames.message.proto.auth.AuthProtoBuf;
import com.kodgames.message.proto.game.GameProtoBuf;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

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

    private SinglePlayerSequenceTask sequenceTask = new SinglePlayerSequenceTask();

    private static boolean replayServerFlag = true;

    public Role(Connection conn) {
        interfaceConnection = conn;

        this.username = "jztest" + (RANDOM_USER ? UUID.randomUUID().toString() : "") + String.valueOf(interfaceConnection.getConnectionID());
    }

    public void init() {
        sequenceTask.addTask(new SequenceTask() {
            @Override
            public void start() {
                sendAuth();
            }

            @Override
            public void handleMessage(GeneratedMessage generatedMessage) {
                AuthProtoBuf.ICAccountAuthRES message = (AuthProtoBuf.ICAccountAuthRES)generatedMessage;
                roleId = message.getAccountId();
                username = message.getUsername();
                nickName = message.getNickname();
                headImg = message.getHeadImageUrl();
                sex = message.getSex();
                channel = message.getChannel();
                unionId = message.getUnionId();
                signature = message.getSignature();
                developerId = message.getDeveloperId();
                gameServerId = message.getGameServerId();
                clubServerId = message.getClubServerId();

                interfaceConnection.setRemotePeerID(roleId);
            }
        });

        sequenceTask.addTask(new SequenceTask() {
            @Override
            public void start() {
                miniGameLogin();
            }

            @Override
            public void handleMessage(GeneratedMessage generatedMessage) {

            }
        });
    }

    public int getRoleId() {
        return roleId;
    }

    public String getUsername() {
        return username;
    }

    public SinglePlayerSequenceTask getSequenceTask() {
        return sequenceTask;
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
}
