package com.mycode.compiler.lexical.statemachine;

import com.mycode.compiler.lexical.Token;

import static com.mycode.compiler.lexical.statemachine.Defines.isLetter;
import static com.mycode.compiler.lexical.statemachine.Defines.isNumber;

/**
 * identifier
 *
 * @author jiangzhen
 */
public class IdSM extends StateMachine
{
	public IdSM() {
		super(new Token(Defines.TokenType.ID));
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
				if (isLetter(ch)) {
					return 1;
				} else {
					return -1;
				}
			case 1:
				if (isLetter(ch) || isNumber(ch)) {
					return 1;
				} else {
					return 2;
				}
			default:
				return -1;
		}
	}
}
