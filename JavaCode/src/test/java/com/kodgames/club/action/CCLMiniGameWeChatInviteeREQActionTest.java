package com.kodgames.club.action;

import com.google.protobuf.GeneratedMessage;
import com.kodgames.club.action.common.CCLMiniGameWeChatInviteeREQAction;
import com.kodgames.club.service.common.ClubCommonService;
import com.kodgames.message.proto.club.*;
import com.kodgames.message.protocol.PlatformProtocolsConfig;
import org.junit.Test;
import tool.action.ActionTestExpectOut;
import tool.action.ActionTestInput;
import tool.action.ActionTestTool;
import tool.connection.MyTestConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangzhen
 * @date 2019/4/11 16:38
 */
public class CCLMiniGameWeChatInviteeREQActionTest {
    private static final int TEST_ROLE_ID = 1234567;

    private ActionTestTool testTool;
    private CCLMiniGameWeChatInviteeREQAction handler;

    public CCLMiniGameWeChatInviteeREQActionTest() {
        handler = new CCLMiniGameWeChatInviteeREQAction();
        testTool = new ActionTestTool(handler);
    }

    private ActionTestInput buildInput() {
        ActionTestInput input = new ActionTestInput();

        MyTestConnection myTestConnection = MyTestConnection.defaultTestConnection();
        myTestConnection.setRemoteConnectionID(TEST_ROLE_ID);
        input.setConnection(myTestConnection);
        input.setPublicService(new ClubCommonService());

        return input;
    }

    @Test
    public void testNotNewPlayer() {
        ActionTestInput input = buildInput();
        ActionTestExpectOut expectOut = new ActionTestExpectOut();

        ClubProtoBuf.CCLMiniGameWeChatInviteeREQ.Builder req =
                ClubProtoBuf.CCLMiniGameWeChatInviteeREQ.newBuilder();
        req.setInviterRoleId(100000);
        input.setMessage(req.build());
        input.setDbInput(() -> {
            table.Role_clubs.insert(input.getConnection().getRemoteConnectionID());
        });
        input.setClearDbInput(() -> {
            table.Role_clubs.delete(input.getConnection().getRemoteConnectionID());
        });

        List<GeneratedMessage> expectMessageList = new ArrayList<>();
        expectMessageList.add(
                ClubProtoBuf.CLCMiniGameWeChatInviteeRES.newBuilder()
                        .setResult(PlatformProtocolsConfig.CLC_MINI_GAME_WECHAT_INVITEE_FAILS_NOT_NEW_PLAYER)
                        .build()
        );
        expectOut.setExpectMessageList(expectMessageList);

        testTool.addTestTask(input, expectOut);
    }

    /**
     * 不能邀请自己
     */
    @Test
    public void testNotInviteSelf() {
        ActionTestInput input = buildInput();
        ActionTestExpectOut expectOut = new ActionTestExpectOut();

        ClubProtoBuf.CCLMiniGameWeChatInviteeREQ.Builder req =
                ClubProtoBuf.CCLMiniGameWeChatInviteeREQ.newBuilder();
        req.setInviterRoleId(input.getConnection().getRemoteConnectionID());
        input.setMessage(req.build());

        List<GeneratedMessage> expectMessageList = new ArrayList<>();
        expectMessageList.add(
                ClubProtoBuf.CLCMiniGameWeChatInviteeRES.newBuilder()
                        .setResult(PlatformProtocolsConfig.CLC_MINI_GAME_WECHAT_INVITEE_FAILS_INVITER_SAME_INVITEE)
                        .build()
        );
        expectOut.setExpectMessageList(expectMessageList);

        testTool.addTestTask(input, expectOut);
    }

    @Test
    public void testSuccess() {
        ActionTestInput input = buildInput();
        ActionTestExpectOut expectOut = new ActionTestExpectOut();

        ClubProtoBuf.CCLMiniGameWeChatInviteeREQ.Builder req =
                ClubProtoBuf.CCLMiniGameWeChatInviteeREQ.newBuilder();
        req.setInviterRoleId(100000);
        input.setMessage(req.build());

        List<GeneratedMessage> expectMessageList = new ArrayList<>();
        expectMessageList.add(
                ClubProtoBuf.CLCMiniGameWeChatInviteeRES.newBuilder()
                        .setResult(PlatformProtocolsConfig.CLC_MINI_GAME_WECHAT_INVITEE_SUCCESS)
                        .build()
        );
        expectOut.setExpectMessageList(expectMessageList);

        testTool.addTestTask(input, expectOut);
    }
}
