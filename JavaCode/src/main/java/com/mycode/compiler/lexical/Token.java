package com.mycode.compiler.lexical;

import com.mycode.compiler.lexical.statemachine.Defines;

/**
 * 词法单元
 *
 * @author jiangzhen
 */
public class Token
{
	/**
	 * 词法单元类型
	 */
	private Defines.TokenType type;

	/**
	 * 词法单元名
	 */
	private String name;

	/**
	 * 词法单元属性值在符号表中的下标
	 */
	private int index;

	public Token(Defines.TokenType type, String name, int index) {
		this.type = type;
		this.name = name;
		this.index = index;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public Defines.TokenType getType()
	{
		return type;
	}

	public void setType(Defines.TokenType type)
	{
		this.type = type;
	}
}
