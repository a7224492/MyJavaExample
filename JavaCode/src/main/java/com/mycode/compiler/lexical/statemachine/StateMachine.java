package com.mycode.compiler.lexical.statemachine;

/**
 * @author jiangzhen
 */
public abstract class StateMachine
{
	private Defines.TokenType type;
	private int state;

	protected boolean finalState = false;
	protected boolean charAccept = false;

	public StateMachine() {
		this.state = 0;
	}

	protected StateMachine(Defines.TokenType type) {
		this.type = type;
		this.state = 0;
	}

	public int move(byte ch) {
		int nextState = getNextState(state, ch);
		state = nextState;
		return state;
	}

	public static boolean isLetter(byte ch) {
		return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_';
 	}

 	public static boolean isNumber(byte ch) {
		return (ch >= '0' && ch <= '9');
    }

    public int getState() {
		return state;
    }

    public boolean isFinalState() {
		return finalState;
    }

    public void reset() {
		state = 0;
		finalState = false;
		charAccept = false;
    }

	public Defines.TokenType getType() {
		return type;
	}

	public boolean isCharAccept() {
		return charAccept;
	}

	protected abstract int getNextState(int state, byte ch);
}