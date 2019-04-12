package com.mycode.calculate;

import com.kodgames.battlecore.service.battle.common.xbean.PlayerInfo;
import com.kodgames.battleserver.service.battle.core.score.battle.BattleScoreCalculator;
import com.kodgames.message.proto.battle.BattleProtoBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangzhen
 */
public class BattleScoreCalculator_JZ extends BattleScoreCalculator {
    /**
     * 计算所有玩家的牌局最终得分,
     *
     * @param players             所有玩家
     * @param inoutResultBuilders 输出计算结果
     */
    public void calculatePlayerScore(Map<Integer, PlayerInfo> players, HashMap<Integer, BattleProtoBuf.PlayerMatchResultPROTO.Builder> inoutResultBuilders) {
        for (PlayerInfo playerInfo : players.values()) {
            // 计算玩家总分
            calculatePointInGame(playerInfo);

            // 反向构造scoreData

            // scoreData转换为Event，也就是转换为proto消息结构发送给客户端
        }
    }

    /**
     * 计算本局得分
     *
     */
    private void calculatePointInGame(PlayerInfo playerInfo) {
        // 计算公式
    }
}
