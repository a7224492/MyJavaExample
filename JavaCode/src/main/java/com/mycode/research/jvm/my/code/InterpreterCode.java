package com.mycode.research.jvm.my.code;

/**
 * @author jiangzhen
 */
public class InterpreterCode {
	private Code code;
	private byte[] operand;

	public InterpreterCode(Code code, byte[] operand) {
		this.code = code;
		this.operand = operand;
	}

	public Code getCode()
	{
		return code;
	}

	public void setCode(Code code)
	{
		this.code = code;
	}

	public byte[] getOperand()
	{
		return operand;
	}

	public void setOperand(byte[] operand)
	{
		this.operand = operand;
	}
}
