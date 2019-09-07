package com.mycode.compiler.lexical.statemachine;

/**
 * @author jiangzhen
 */
public class DigitStateMachine extends StateMachine
{
	public DigitStateMachine() {
		super(Defines.TokenType.DIGIT);
	}

	@Override
	protected int getNextState(int state, byte ch)
	{
		switch (state) {
			case 0:
				if (isNumber(ch)) {
					return 1;
				} else {
					return -1;
				}
			case 1:
				if (isNumber(ch)) {
					return 1;
				} else if (ch == '.') {
					return 2;
				} else if (ch == 'E') {
					return 4;
				} else {
					finalState = true;
					return -1;
				}
			case 2:
				if (isNumber(ch)) {
					return 3;
				} else {
					return -1;
				}
			case 3:
				if (isNumber(ch)) {
					return 3;
				} else if (ch == 'E') {
					return 4;
				} else {
					finalState = true;
					return -1;
				}
			case 4:
				if (ch == '+' || ch == '-') {
					return 5;
				} else if (isNumber(ch)) {
					return 6;
				} else {
					return -1;
				}
			case 5:
				return isNumber(ch) ? 6 : -1;
			case 6:
				if (isNumber(ch)) {
					return 6;
				} else {
					finalState = true;
					return -1;
				}
			default:
				return -1;
		}
	}
}
