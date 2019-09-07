package com.mycode.research.jvm.my.code;

/**
 * @author jiangzhen
 */
public class InterpreterCode {
	private Code code;
	private byte[] operands;

	public InterpreterCode(Code code, byte[] operand) {
		this.code = code;
		this.operands = operand;
	}

	public Code getCode()
	{
		return code;
	}

	public void setCode(Code code)
	{
		this.code = code;
	}

	public byte[] getOperands()
	{
		return operands;
	}

	public void setOperands(byte[] operands)
	{
		this.operands = operands;
	}

	public int parseOperand() {
		int result = 0;
		for (int i = 0; i < operands.length; ++i) {
			result = (result << 8) | operands[i];
		}

		return 0;
	}
}
