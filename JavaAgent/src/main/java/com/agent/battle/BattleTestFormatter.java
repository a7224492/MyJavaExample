package com.agent.battle;

import com.kodgames.battlecore.service.battle.common.xbean.Step;
import com.kodgames.battleserver.service.battle.constant.MahjongConstant;
import com.kodgames.battleserver.service.battle.region.guizhou.common.Rules_GuiZhou;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangzhen
 */
public class BattleTestFormatter
{
	private static Map<Integer, String> roomSize2String = new HashMap<>();

	private static Map<Integer, String> rule2Name = new HashMap<>();
	private static Map<Integer, String> playType2Name = new HashMap<>();
	private static List<String> playerNameList = Arrays.asList("FIRST", "SECOND", "THIRD", "FORTH");

	static {
		roomSize2String.put(2, "ER_ID_LIST");
		roomSize2String.put(3, "SAN_ID_LIST");
		roomSize2String.put(4, "SI_ID_LIST");

		for (Field declaredField : Rules_GuiZhou.class.getDeclaredFields())
		{
			try
			{
				declaredField.setAccessible(true);
				Object o = declaredField.get(Rules_GuiZhou.class);
				if (o instanceof Integer) {
					int value = (int) o;
					String name = declaredField.getName();
					rule2Name.put(value, name);
				}
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}

		for (Field declaredField : MahjongConstant.PlayType.class.getDeclaredFields())
		{
			try
			{
				declaredField.setAccessible(true);
				Object o = declaredField.get(MahjongConstant.PlayType.class);
				if (o instanceof Integer) {
					int value = (int) o;
					String name = declaredField.getName();

					playType2Name.put(value, name);
				}
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
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
		sb.append("// 房间规则\n").append("List<Integer> rules = Arrays.asList(\n");

		for (int i = 0; i < rules.size(); ++i) {
			int rule = rules.get(i);
			String name = rule2Name.get(rule);
			if (name == null) {
				name = String.valueOf(rule);
			}

			if (i == rules.size()- 1) {
				sb.append(name).append("\n");
			} else {
				sb.append(name).append(",\n");
			}
		}

		sb.append(");\n");
		return sb.toString();
	}

	public static String formatCreateRoom(int roomSize) {
		return "// 创建房间\n" + "createBattleRoom(rules, " + roomSize2String.get(roomSize) + ");\n";
	}

	public static String formatPlayStep(List<Step> stepList) {
		// "playStep(SECOND, OPERATE_TING, 0);\n"
		StringBuilder sb = new StringBuilder();
		sb.append("// 打牌\n");
		for (Step step : stepList)
		{
			String name = playType2Name.get(step.getPlayType());
			if (name == null) {
				name = String.valueOf(step.getPlayType());
			}

			String playerName = playerNameList.get(step.getRoleId());
			sb.append("playStep(").append(playerName).append(", ").append(name).append(", ");
			if (!step.getCards().isEmpty()) {
				sb.append(step.getCards().get(0));
			}
			sb.append(");\n");
		}

		return sb.toString();
	}

	public static String formatCheckScore(List<Integer> roleIdList, List<Integer> scoreList) {
		StringBuilder sb = new StringBuilder();
		sb.append("// 算分\n");
		for (int i = 0; i < roleIdList.size(); ++i) {
			sb.append("checkScore(").append(playerNameList.get(roleIdList.get(i))).append(", ").append(scoreList.get(i)).append(");\n");
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(FORMATTER);
	}
}
