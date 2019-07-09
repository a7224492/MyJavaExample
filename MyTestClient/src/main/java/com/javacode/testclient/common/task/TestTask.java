package com.javacode.testclient.common.task;

import com.google.protobuf.GeneratedMessage;
import com.javacode.testclient.common.Role;
import com.kodgames.message.proto.auth.AuthProtoBuf;
import com.kodgames.message.proto.club.ClubProtoBuf;
import com.kodgames.message.proto.game.GameProtoBuf;
import com.kodgames.message.protocol.PlatformProtocolsConfig;

import static com.javacode.testclient.common.util.TestUtil.assertTrue;

/**
 * @author jiangzhen
 */
public class TestTask {
    private TestQueue testQueue = TestQueue.getInstance();
    private Role inviterRole;
    private int clubId;

    public void startTestTask() {
        testQueue.start();
    }

    public void setTestTask(Role role, int playerNumber) {
        switch (playerNumber) {
            case 1:
                testQueue.addTask(new SendTask() {
                    @Override
                    public void run() {
                        role.sendAuth();
                    }
                });
                testQueue.addTask(new RecvTask() {
                    @Override
                    public Class<?> getTestMessageClazz() {
                        return AuthProtoBuf.ICAccountAuthRES.class;
                    }

                    @Override
                    public void handle(GeneratedMessage message) {
                        role.onAuth(message);
                        inviterRole = role;
                    }
                });
                testQueue.addTask(new SendTask() {
                    @Override
                    public void run() {
                        role.miniGameLogin(inviterRole.getRoleId());
                    }

                    @Override
                    public boolean hasNext() {
                        return true;
                    }
                });
                testQueue.addTask(new RecvTask() {
                    @Override
                    public Class<?> getTestMessageClazz() {
                        return GameProtoBuf.GCLoginRES.class;
                    }

                    @Override
                    public void handle(GeneratedMessage message) {

                    }
                });

                break;
            case 2:
                testQueue.addTask(new SendTask() {
                    @Override
                    public void run() {
                        role.sendAuth();
                    }
                });
                testQueue.addTask(new RecvTask() {
                    @Override
                    public Class<?> getTestMessageClazz() {
                        return AuthProtoBuf.ICAccountAuthRES.class;
                    }

                    @Override
                    public void handle(GeneratedMessage message) {
                        role.onAuth(message);
                    }
                });
                testQueue.addTask(new SendTask() {
                    @Override
                    public void run() {
                        role.miniGameLogin(inviterRole.getRoleId());
                    }

                    @Override
                    public boolean hasNext() {
                        return true;
                    }
                });
                testQueue.addTask(new RecvTask() {
                    @Override
                    public Class<?> getTestMessageClazz() {
                        return GameProtoBuf.GCLoginRES.class;
                    }

                    @Override
                    public void handle(GeneratedMessage message) {

                    }
                });
                break;
            case 3:
                testQueue.addTask(new SendTask() {
                    @Override
                    public void run() {
                        role.sendAuth();
                    }
                });
                testQueue.addTask(new RecvTask() {
                    @Override
                    public Class<?> getTestMessageClazz() {
                        return AuthProtoBuf.ICAccountAuthRES.class;
                    }

                    @Override
                    public void handle(GeneratedMessage message) {
                        role.onAuth(message);
                    }
                });
                testQueue.addTask(new SendTask() {
                    @Override
                    public void run() {
                        role.miniGameLogin(inviterRole.getRoleId());
                    }

                    @Override
                    public boolean hasNext() {
                        return true;
                    }
                });
                testQueue.addTask(new RecvTask() {
                    @Override
                    public Class<?> getTestMessageClazz() {
                        return GameProtoBuf.GCLoginRES.class;
                    }

                    @Override
                    public void handle(GeneratedMessage message) {

                    }
                });
                testQueue.addTask(new SendTask() {
                    @Override
                    public void run() {
                        inviterRole.miniCreateClub();
                    }
                });
                testQueue.addTask(new RecvTask() {
                    @Override
                    public Class<?> getTestMessageClazz() {
                        return ClubProtoBuf.CLCCreateClubRES.class;
                    }

                    @Override
                    public void handle(GeneratedMessage message) {
                        ClubProtoBuf.CLCCreateClubRES res = (ClubProtoBuf.CLCCreateClubRES)message;
                        assertTrue(res.getResult() == PlatformProtocolsConfig.CLC_CREATE_CLUB_SUCCESS);
                        assertTrue(res.getClubId() != 0);

                        clubId = res.getClubId();
                    }
                });
                testQueue.addTask(new SendTask() {
                    @Override
                    public void run() {
                        inviterRole.sendCCLClubMembersREQ(clubId);
                    }
                });
                testQueue.addTask(new RecvTask() {
                    @Override
                    public Class<?> getTestMessageClazz() {
                        return ClubProtoBuf.CLCClubMembersRES.class;
                    }

                    @Override
                    public void handle(GeneratedMessage message) {

                    }
                });
                testQueue.addTask(new SendTask() {
                    @Override
                    public void run() {
                        inviterRole.miniCreateClub();
                    }
                });
                testQueue.addTask(new RecvTask() {
                    @Override
                    public Class<?> getTestMessageClazz() {
                        return ClubProtoBuf.CLCCreateClubRES.class;
                    }

                    @Override
                    public void handle(GeneratedMessage message) {
                        ClubProtoBuf.CLCCreateClubRES res = (ClubProtoBuf.CLCCreateClubRES)message;
                        assertTrue(res.getResult() == PlatformProtocolsConfig.CLC_CREATE_CLUB_SUCCESS);
                        assertTrue(res.getClubId() != 0);
                    }
                });
                break;
        }
    }
}
