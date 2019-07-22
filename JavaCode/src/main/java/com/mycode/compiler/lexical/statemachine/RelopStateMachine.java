package com.mycode.compiler.lexical.statemachine;

/**
 * @author jiangzhen
 */
public class RelopStateMachine extends StateMachine
{
	public RelopStateMachine() {
		super(Defines.TokenType.RELOP);
	}

	@Override
	protected int getNextState(int state, byte ch)
	{
		switch (state) {
			case 0:
				switch (ch) {
					case '<':
						return 1;
					case '=':
						charAccept = true;
						finalState = true;
						return -1;
					case '>':
						return 6;
					default:
						return -1;
				}
			case 1:
				switch (ch) {
					case '=':
						charAccept = true;
						finalState = true;
						return -1;
					case '>':
						charAccept = true;
						finalState = true;
						return -1;
					default:
						finalState = true;
						return -1;
				}
			case 6:
				switch (ch) {
					case '=':
						charAccept = true;
						finalState = true;
						return -1;
					default:
						finalState = true;
						return -1;
				}
			default:
				return -1;
		}
	}
}
