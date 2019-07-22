package com.mycode.compiler.lexical.statemachine;

/**
 * @author jiangzhen
 */
public class WhiteStateMachine extends StateMachine
{
	public WhiteStateMachine() {
		super(Defines.TokenType.WHITE);
	}

	@Override
	protected int getNextState(int state, byte ch)
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
						finalState = true;
						return -1;
				}
			default:
				return -1;
		}
	}
}
