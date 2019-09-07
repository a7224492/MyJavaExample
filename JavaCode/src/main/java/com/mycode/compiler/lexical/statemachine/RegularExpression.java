package com.mycode.compiler.lexical.statemachine;

/**
 * @author jiangzhen
 */
public class RegularExpression
{
	public static String digit = "[0-9]";
	public static String letter = "[A-Za-z]";
	public static String number = "digits(. digits)?(E[+-]?digits)?";
	public static String id = "letter(letter|digit)*";
	public static String _if = "if";
	public static String _else = "else";
	public static String relop = "<|>|<=|>=|=|<>";
}
