package com.mycode.compiler.lexical.statemachine;

import com.mycode.compiler.lexical.Token;

/**
 *
 *
 * @author jiangzhen
 */
public class RelopSM extends StateMachine
{
	public RelopSM() {
		super(new Token(Defines.TokenType.RELOP));
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
				switch (ch) {
					case '<':
						return 1;
					case '=':
						return 2;
					case '>':
						return 3;
					default:
						return -1;
				}
			case 1:
				switch (ch) {
					case '=':
						return 4;
					case '>':
						return 5;
					default:
						return 6;
				}
			case 2:
			case 3:
			case 4:
			case 5:
				return 6;
			default:
				return -1;
		}
	}
}
