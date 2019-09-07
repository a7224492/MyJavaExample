package com.mycode.compiler.lexical.statemachine;

import com.mycode.compiler.Defines;
import com.mycode.compiler.lexical.Token;

import static com.mycode.compiler.Defines.isNumber;

/**
 * 数字
 *
 * @author jiangzhen
 */
public class DigitSM extends StateMachine
{
	public DigitSM() {
		super(new Token(Defines.TokenType.DIGIT));
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
					return 3;
				} else {
					return 7;
				}
			case 2:
				if (isNumber(ch)) {
					return 4;
				} else {
					return -1;
				}
			case 3:
				if (ch == '+' || ch == '-') {
					return 5;
				} else if (isNumber(ch)) {
					return 6;
				} else {
					return -1;
				}
			case 4:
				if (isNumber(ch)) {
					return 4;
				} else if (ch == 'E') {
					return 3;
				} else {
					return 7;
				}
			case 5:
				return isNumber(ch) ? 6 : -1;
			case 6:
				if (isNumber(ch)) {
					return 6;
				} else {
					return 7;
				}
			default:
				return -1;
		}
	}
}
