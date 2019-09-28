package com.agent.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangzhen
 */
public class BattleTestFormatter
{
	private static Map<Integer, String> ruleValue2Name = new HashMap<>();

	static {
		Rules_GuiZhou
	}

	public static final String FORMATTER = "" +
		"List<Integer> rules = Arrays.asList(\n" +
		"GAME_TYPE_AN_LONG,\n" +
		"GAME_PLAY_3REN,\n" +
		"GAME_PLAY_YAOBAI_JI,\n" +
		"GAME_PLAY_BEN_JI,\n" +
		"GAME_PLAY_WUGU_JI,\n" +
		"GAME_PLAY_XINGQI_JI,\n" +
		"GAME_PLAY_MEN_HU,\n" +
		"GAME_PLAY_XIAO_HU_BI_MEN,\n" +
		"GAME_PLAY_LIAN_HU,\n" +
		"GAME_PLAY_LIANZHUANG,\n" +
		"GAME_PLAY_FENG_DING_60,\n" +
		"COMMON_VOICE_CLOSE,\n" +
		"COMMON_TING_TIPS_OPEN,\n" +
		"COMMON_TRUSTEESHIP_CLOSE,\n" +
		"GAME_PLAY_NO_TING_CARD\n" + ");\n" +
		"\n" +
		"// 创建房间\n" +
		"createBattleRoom(rules, SAN_ID_LIST);\n" +
		"\n" +
		"// 打牌\n" +
		"playStep(SECOND, OPERATE_TING, 0);\n" +
		"playStep(FIRST, OPERATE_PLAY_A_CARD, 29);\n" +
		"playStep(SECOND, OPERATE_MEN, 29);\n" +
		"playStep(SECOND, OPERATE_PLAY_A_CARD, 19);\n" +
		"playStep(FIRST, OPERATE_MEN, 19);\n" +
		"playStep(THIRD, OPERATE_PLAY_A_CARD, 27);\n" +
		"playStep(SECOND, OPERATE_MEN, 27);\n" +
		"playStep(FIRST, OPERATE_MEN, 18);\n" +
		"playStep(SECOND, OPERATE_PLAY_A_CARD, 17);\n" +
		"playStep(FIRST, OPERATE_MEN, 17);\n" +
		"playStep(THIRD, OPERATE_PLAY_A_CARD, 21);\n" +
		"playStep(SECOND, OPERATE_MEN, 21);\n" +
		"playStep(FIRST, OPERATE_PLAY_A_CARD, 28);\n" +
		"playStep(SECOND, OPERATE_MEN, 28);\n" +
		"playStep(SECOND, OPERATE_PLAY_A_CARD, 16);\n" +
		"playStep(FIRST, OPERATE_MEN, 16);\n" +
		"playStep(THIRD, OPERATE_PLAY_A_CARD, 22);\n" +
		"playStep(SECOND, OPERATE_MEN, 22);\n" + "playStep(FIRST, OPERATE_MEN, 18);\n" +
		"playStep(SECOND, OPERATE_PLAY_A_CARD, 14);\n" +
		"playStep(FIRST, OPERATE_MEN, 14);\n" +
		"playStep(THIRD, OPERATE_PLAY_A_CARD, 14);\n" +
		"playStep(FIRST, OPERATE_HU, 14);\n" +
		"\n" +
		"// 算分\n" +
		"checkScore(FIRST, 119);\n" +
		"checkScore(SECOND, -60);\n" +
		"checkScore(THIRD, -59);";

	public static String formatRoomRules(List<Integer> rules) {
		StringBuilder sb = new StringBuilder();
		sb.append("List<Integer> rules = Arrays.asList(\n");
		for (Integer rule : rules) {

		}
		 +
			"GAME_TYPE_AN_LONG,\n" +
			"GAME_PLAY_3REN,\n" +
			"GAME_PLAY_YAOBAI_JI,\n" +
			"GAME_PLAY_BEN_JI,\n" +
			"GAME_PLAY_WUGU_JI,\n" +
			"GAME_PLAY_XINGQI_JI,\n" +
			"GAME_PLAY_MEN_HU,\n" +
			"GAME_PLAY_XIAO_HU_BI_MEN,\n" +
			"GAME_PLAY_LIAN_HU,\n" +
			"GAME_PLAY_LIANZHUANG,\n" +
			"GAME_PLAY_FENG_DING_60,\n" +
			"COMMON_VOICE_CLOSE,\n" +
			"COMMON_TING_TIPS_OPEN,\n" +
			"COMMON_TRUSTEESHIP_CLOSE,\n" +
			"GAME_PLAY_NO_TING_CARD\n" + ");\n"
	}

	public static void main(String[] args) {
		System.out.println(FORMATTER);
	}
}
