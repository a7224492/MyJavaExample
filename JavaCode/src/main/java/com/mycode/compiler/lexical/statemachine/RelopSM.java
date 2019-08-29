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
		return state == 7;
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
						return 7;
				}
			case 2:
			    return ch == '=' ? 6 : -1;
			case 3:
			case 4:
			case 5:
            case 6:
				return 7;
			default:
				return -1;
		}
	}
}
