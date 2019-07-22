package com.mycode.compiler.lexical.statemachine;

/**
 * @author jiangzhen
 */
public class ElseStateMachine extends StateMachine
{
	public ElseStateMachine() {
		super(Defines.TokenType.ELSE);
	}

	@Override
	protected int getNextState(int state, byte ch)
	{
		switch (state) {
			case 0:
				return ch == 'e' ? 1 : -1;
			case 1:
				return ch == 'l' ? 2 : -1;
			case 2:
				return ch == 's' ? 3 : -1;
			case 3:
				if (ch == 'e') {
					charAccept = true;
					finalState = true;
				}

				return -1;
			default:
				return -1;
		}
	}
}
