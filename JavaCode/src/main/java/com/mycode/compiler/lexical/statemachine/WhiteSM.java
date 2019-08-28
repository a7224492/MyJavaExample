package com.mycode.compiler.lexical.statemachine;

import com.mycode.compiler.lexical.Token;

/**
 * 空格
 *
 * @author jiangzhen
 */
public class WhiteSM extends StateMachine
{
	public WhiteSM() {
		super(new Token(Defines.TokenType.WHITE));
	}

	@Override
	public boolean accept() {
		return state == 2;
	}

	@Override
	protected int moveToNextState(byte ch)
	{
		switch (state) {
			case 0:
				switch (ch) {
					case ' ':
					case '\t':
					case '\n':
						return 1;
					default:
						return -1;
				}
			case 1:
				switch (ch) {
					case ' ':
					case '\t':
					case '\n':
						return 1;
					default:
						return 2;
				}
			default:
				return -1;
		}
	}
}
