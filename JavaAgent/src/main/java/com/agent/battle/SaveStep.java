package com.agent.battle;

import com.kodgames.battlecore.service.battle.common.xbean.BattleBean;
import com.kodgames.battlecore.service.battle.common.xbean.BattleRoom;
import com.kodgames.battlecore.service.battle.common.xbean.PlayerInfo;
import com.kodgames.battlecore.service.battle.common.xbean.Step;
import com.kodgames.battlecore.service.battle.core.MahjongHelper;
import com.kodgames.battleserver.service.battle.core.BattleHelper;
import com.kodgames.message.proto.battle.BattleProtoBuf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiangzhen
 * @date 2019/9/26 10:40
 */
public class SaveStep {
    /**
     * <roomId,<真实id, 测试id>>
     */
    private static Map<Integer, TestRoom> roomMap = new ConcurrentHashMap<>();

    public static void startGame(BattleRoom roomInfo) {
        System.out.println("Aspect : startGame, roomId = {}" + roomInfo.getRoomId());

        int i = 0;
        TestRoom testRoom = new TestRoom();
        for (PlayerInfo player : roomInfo.getPlayers())
        {
            testRoom.real2TestIdMap.put(player.getRoleId(), i++);
        }

        testRoom.rules.addAll(roomInfo.getGameplays());
        roomMap.put(roomInfo.getRoomId(), testRoom);
    }

    public static void roundFinish(int roomId, int zhuang, int nextZhuang, BattleProtoBuf.BCMatchResultSYN bcMatchResultSYN)
    {
        System.out.println("Aspect : roundFinish, roomId = {}" + roomId);
        BattleBean context = BattleHelper.getInstance().getBattleBean();
        TestRoom testRoom = roomMap.get(roomId);

        StringBuilder sb = new StringBuilder();
        sb.append("@Test\n").append("\tpublic void test111() throws Exception {\n");

        // 房间规则
        sb.append(BattleTestFormatter.formatRoomRules(context.getGameRules()));

        // 创建房间
        sb.append(BattleTestFormatter.formatCreateRoom(context.getPlayerSize()));

        // 打牌步骤
        sb.append(BattleTestFormatter.formatPlayStep(testRoom.stepList));

        List<Integer> roleIdList = new ArrayList<>();
        List<Integer> scoreList = new ArrayList<>();
        for (BattleProtoBuf.PlayerMatchResultPROTO proto : bcMatchResultSYN.getMatchResultsList())
        {
            roleIdList.add(testRoom.real2TestIdMap.get(proto.getRoleId()));
            scoreList.add(proto.getPointInGame());
        }

        // 算分
        sb.append(BattleTestFormatter.formatCheckScore(roleIdList, scoreList));
        sb.append("\t}");

        System.out.println(sb.toString());
    }

    public static void processStep(int roleId, int playType, byte[] cards, List<Integer> datas) {
        System.out.println("Aspect : processStep, roleId = " + roleId);

        int roomId = BattleHelper.getInstance().getRoomInfo().getRoomId();
        TestRoom testRoom = roomMap.get(roomId);
        int id = testRoom.real2TestIdMap.get(roleId);

        testRoom.stepList.add(new Step(id, playType, MahjongHelper.convert2ByteList(cards)));
    }
}

class TestRoom {
    /**
     * <真实id, 测试id>
     */
    Map<Integer, Integer> real2TestIdMap = new HashMap<>();
    List<Integer> rules = new ArrayList<>();
    List<Step> stepList = new ArrayList<>();
}
