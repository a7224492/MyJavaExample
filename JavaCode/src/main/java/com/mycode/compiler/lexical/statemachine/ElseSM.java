package com.mycode.compiler.lexical.statemachine;

import com.mycode.compiler.lexical.Token;

/**
 * else
 *
 * @author jiangzhen
 */
public class ElseSM extends StateMachine
{
	public ElseSM() {
		super(new Token(Defines.TokenType.ELSE));
	}

	@Override
	public boolean accept() {
		return state == 5;
	}

	@Override
	protected int moveToNextState(byte ch)
	{
		switch (state) {
			case 0:
				return ch == 'e' ? 1 : -1;
			case 1:
				return ch == 'l' ? 2 : -1;
			case 2:
				return ch == 's' ? 3 : -1;
			case 3:
				return ch == 'e' ? 4 : -1;
			case 4:
				return 5;
			default:
				return -1;
		}
	}
}
