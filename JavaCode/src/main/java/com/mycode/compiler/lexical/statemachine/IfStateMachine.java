package com.mycode.compiler.lexical.statemachine;

/**
 * @author jiangzhen
 */
public class IfStateMachine extends StateMachine
{
	public IfStateMachine() {
		super(Defines.TokenType.IF);
	}

	@Override
	protected int getNextState(int state, byte ch)
	{
		switch (state) {
			case 0:
				return ch == 'i' ? 1 : -1;
			case 1:
				if (ch == 'f')
				{
					charAccept = true;
					finalState = true;
				}

				return -1;
			default:
				return -1;
		}
	}
}
