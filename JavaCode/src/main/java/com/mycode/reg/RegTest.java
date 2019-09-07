package com.mycode.reg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jiangzhen
 */
public class RegTest {
	public static void main(String[] args) {
		// 找到匹配的原始字符串
		String target = "steps {\n" + "  roleId: 5088228\n" + "  playType: 1087\n" + "  cards: \"\\001\\001\\001\\002\"\n" + "  sourceRoleId: 5088228\n" + "}\n" + "steps {\n" + "  roleId: 5088228\n"
			+ "  playType: 5304\n" + "  cards: \"\\004\"\n" + "  sourceRoleId: 5088228\n" + "}\n" + "steps {\n" + "  roleId: 5088228\n" + "  playType: 2\n" + "  cards: \"\\005\"\n"
			+ "  sourceRoleId: 5088228\n" + "}\n" + "steps {\n" + "  roleId: 5151542\n" + "  playType: 5304\n" + "  cards: \"\\004\"\n" + "  sourceRoleId: 5151542\n" + "}\n" + "steps {\n"
			+ "  roleId: 5172542\n" + "  playType: 5304\n" + "  cards: \"\\004\"\n" + "  sourceRoleId: 5172542\n" + "}\n" + "steps {\n" + "  roleId: -1\n" + "  pointInGame: 96\n"
			+ "  playType: 2001\n" + "}\n" + "protocolSeq: 2";

		Pattern compile = Pattern.compile("playType: [0-9]+");
		Matcher matcher = compile.matcher(target);

		System.out.println(matcher.find());
	}
}
