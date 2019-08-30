package com.mycode.compiler.lexical;

import com.mycode.compiler.Defines;

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
	private StringBuilder name = new StringBuilder();

	/**
	 * 词法单元属性值在符号表中的下标
	 */
	private int index;

	public Token(Defines.TokenType type, StringBuilder name, int index) {
		this.type = type;
		this.name = name;
		this.index = index;
	}

	public Token(Defines.TokenType type) {
		this.type = type;
	}

	public StringBuilder getName()
	{
		return name;
	}

	public String buildName() {
		return name.toString();
	}

	public void setName(StringBuilder name)
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

	public void reset() {
		name = new StringBuilder();
	}
}
