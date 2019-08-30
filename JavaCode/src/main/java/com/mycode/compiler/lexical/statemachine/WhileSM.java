package com.mycode.compiler.lexical.statemachine;

import com.mycode.compiler.Defines;
import com.mycode.compiler.lexical.Token;

/**
 * else
 *
 * @author jiangzhen
 */
public class WhileSM extends StateMachine
{
	public WhileSM() {
		super(new Token(Defines.TokenType.ELSE));
	}

	@Override
	public boolean accept() {
		return state == 6;
	}

	@Override
	protected int moveToNextState(byte ch)
	{
		switch (state) {
			case 0:
				return ch == 'w' ? 1 : -1;
			case 1:
				return ch == 'h' ? 2 : -1;
			case 2:
				return ch == 'i' ? 3 : -1;
			case 3:
				return ch == 'l' ? 4 : -1;
			case 4:
				return ch == 'e' ? 5 : -1;
			case 5:
				return 6;
			default:
				return -1;
		}
	}
}
