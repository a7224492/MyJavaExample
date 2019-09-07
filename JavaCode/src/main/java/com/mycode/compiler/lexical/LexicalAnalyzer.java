package com.mycode.compiler.lexical;

import com.mycode.compiler.lexical.statemachine.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 词法分析器
 *
 * @author jiangzhen
 */
public class LexicalAnalyzer
{
	private static final int BUFFER_SIZE = 1024;
	private static final int BUFFER_NUMBER = 2;
	private static final int BUFFER_TOTAL_SIZE = BUFFER_SIZE * BUFFER_NUMBER;
	private static final int EOF = -1;

	private InputStream inputStream;
	private byte[] buffer = new byte[BUFFER_TOTAL_SIZE];
	private int begin;
	private int forward;

	private StateMachine[] stateMachines = {
		new IfStateMachine(), new ElseStateMachine(), new IdStateMachine(), new RelopStateMachine(),
		new WhiteStateMachine(), new DigitStateMachine()
	};

	private int[] forwards = new int[stateMachines.length];
	private int tokenIndex = -1;

	private List<Integer> noFinishStateMachineList = new ArrayList<>();

	public void init(InputStream inputStream) {
		this.begin = 0;
		this.forward = 0;
		this.inputStream = inputStream;

		try
		{
			if (inputStream.available() <= 0) {
				throw new IllegalArgumentException("InputStream length is not > 0");
			}

			int readLength = inputStream.read(buffer,0, BUFFER_TOTAL_SIZE - 1);
			buffer[readLength] = EOF;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public Token parseToken() throws IOException {
		reset();

		byte nextCh;
		do {
			nextCh = getNextCh();
			moveAllStateMachine(nextCh);
			if (noFinishStateMachineList.isEmpty()) {
				setTokenIndex();
				forward = forwards[tokenIndex];
				return buildToken();
			}
		} while (nextCh != EOF);

		return null;
	}

	private void moveAllStateMachine(byte ch) {
		for (int i = 0; i < noFinishStateMachineList.size(); ++i) {
			StateMachine stateMachine = stateMachines[noFinishStateMachineList.get(i)];
			stateMachine.move(ch);
			if (stateMachine.getState() == -1) {
				if (stateMachine.isFinalState()) {
					int stateMachineIndex = noFinishStateMachineList.get(i);
					forwards[stateMachineIndex] = (stateMachine.isCharAccept() ? forward : forward - 1);
				}

				noFinishStateMachineList.remove(i);
				--i;
			}
		}
	}

	private byte getNextCh() throws IOException {
		if ((forward - begin) > BUFFER_SIZE) {
			throw new IllegalStateException("lexme length more than BUFFER_SIZE");
		}

		byte ch = buffer[forward++];
		if (ch == EOF) {
			// 缓冲区已经处理完，需要读入新的数据
			if (inputStream.available() > 0) {
				// 输入流中还有字符没有处理
				System.arraycopy(buffer, BUFFER_SIZE, buffer, 0, BUFFER_SIZE - 1);
				int readLength = inputStream.read(buffer, BUFFER_SIZE - 1, BUFFER_SIZE);
				buffer[BUFFER_SIZE - 1 + readLength] = EOF;

				// begin和forwar不会相差BUFFER_SIZE
				begin -= BUFFER_SIZE;
				forward -= BUFFER_SIZE;
			} else {
				// 输入流中没有字符了
				return EOF;
			}
		}

		return ch;
	}

	private void setTokenIndex() {
		// 取最前面的token
		for (int i = 0; i < stateMachines.length; ++i) {
			StateMachine stateMachine = stateMachines[i];
			if (stateMachine.isFinalState()) {
				tokenIndex = i;
				return;
			}
		}
	}

	private Token buildToken() {
		if (tokenIndex < 0) {
			return null;
		}

		// 取最前面的token
		StateMachine stateMachine = stateMachines[tokenIndex];
		Defines.TokenType stateType = stateMachine.getType();
		// TODO 符号表还未设置
		Token token = new Token(stateType, new String(buffer, begin, forward - begin), 0);
		begin = forward;
		return token;
	}

	/**
	 * 重新设置状态及状态
	 */
	private void reset() {
		for (int i = 0; i < stateMachines.length; ++i) {
			stateMachines[i].reset();
			forwards[i] = 0;
		}

		tokenIndex = -1;
		for (int i = 0; i < stateMachines.length; ++i) {
			noFinishStateMachineList.add(i);
		}
	}
}