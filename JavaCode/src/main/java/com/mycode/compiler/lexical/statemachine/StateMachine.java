package com.mycode.compiler.lexical.statemachine;

import com.mycode.compiler.lexical.Token;

/**
 * 表示一个词法单元的状态机
 *
 * @author jiangzhen
 */
public abstract class StateMachine
{
	/**
	 * 状态机类型
	 */
	protected Token token;

	/**
	 * 目前状态
	 */
	protected int state;

	public StateMachine() {
		this.state = 0;
	}

	protected StateMachine(Token token) {
		this.token = token;
		this.state = 0;
	}

    /**
     * 根据输入字符移动状态机到下一状态
     * @param ch 输入字符
     * @return true 输入字符被接受 false 输入字符没有被接受
     */
	public void move(byte ch) {
		// 计算下一个状态
		state = moveToNextState(ch);
		if (!accept()) {
			// 说明当前字符属于词法单元名的一部分
            StringBuilder name = token.getName();
            name.append((char) ch);
        }
	}

    public void reset() {
		state = 0;
		token.reset();
    }

    public boolean fail() {
		return state == -1;
	}

    public Token getToken() {
        return token;
    }

    /**
     * 如果状态属于接受状态，此方法会返回
     * @return
     */
    public abstract boolean accept();
	protected abstract int moveToNextState(byte ch);
}