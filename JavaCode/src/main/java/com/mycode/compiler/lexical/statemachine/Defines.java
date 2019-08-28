package com.mycode.compiler.lexical.statemachine;

/**
 * @author jiangzhen
 */
public class Defines
{
	public enum TokenType
	{
		ID,
		DIGIT,
		RELOP,
		WHITE,
		IF,
		ELSE,
	}

	public static boolean isLetter(byte ch) {
		return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_';
	}

	public static boolean isNumber(byte ch) {
		return (ch >= '0' && ch <= '9');
	}
}
