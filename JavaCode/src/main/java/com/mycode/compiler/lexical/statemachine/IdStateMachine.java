package com.mycode.compiler.lexical.statemachine;

/**
 * @author jiangzhen
 */
public class IdStateMachine extends StateMachine
{
	public IdStateMachine() {
		super(Defines.TokenType.ID);
	}

	@Override
	protected int getNextState(int state, byte ch)
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
					finalState = true;
					return -1;
				}
			default:
				return -1;
		}
	}
}
