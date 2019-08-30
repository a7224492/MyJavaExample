package com.mycode.compiler.lexical.statemachine;

import com.mycode.compiler.Defines;
import com.mycode.compiler.lexical.Token;

/**
 * if
 *
 * @author jiangzhen
 */
public class IfSM extends StateMachine
{
	public IfSM() {
		super(new Token(Defines.TokenType.IF));
	}

	@Override
	public boolean accept() {
		return state == 3;
	}

	@Override
	protected int moveToNextState(byte ch)
	{
		switch (state) {
			case 0:
				return ch == 'i' ? 1 : -1;
			case 1:
				if (ch == 'f') {
					return 2;
				} else {
					return -1;
				}
            case 2:
                return 3;
			default:
				return -1;
		}
	}
}
