package com.mycode.compiler.lexical;

import com.mycode.compiler.lexical.statemachine.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * 词法分析器
 *
 * @author jiangzhen
 */
public class LexicalAnalyzer {
    /**
     * 一个缓冲区的大小
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * 缓冲区数量
     */
    private static final int BUFFER_COUNT = 2;

    /**
     * 缓冲区总大小
     */
    private static final int BUFFER_TOTAL_SIZE = BUFFER_SIZE * BUFFER_COUNT;

    /**
     * 表示缓冲区的最后一位
     */
    private static final int EOF = -1;

    /**
     * 输入字符流，可能是一个文件或者一个字符串
     */
    private InputStream inputStream;

    /**
     * 缓冲区
     * |<-       BUFFER_SIZE(LEFT)    -> <-      BUFFER_SIZE(RIGHT)     ->
     * +--------------------------------+--------------------------------+
     * |                                |                      EOF(1byte)|
     * +--------------------------------+--------------------------------+
     */
    private byte[] buffer = new byte[BUFFER_TOTAL_SIZE];

    /**
     * 本次解析的开始位置
     */
    private int begin;

    /**
     * 前向指针
     */
    private int forward;

    /**
     * 定义过的状态机
     */
    private StateMachine[] stateMachines = {
            new IfSM(), new WhileSM(), new IdSM(), new RelopSM(),
            new WhiteSM(), new DigitSM()
    };

    public void init(InputStream inputStream) {
        this.begin = 0;
        this.forward = 0;
        this.inputStream = inputStream;

        try {
            if (inputStream.available() <= 0) {
                throw new IllegalArgumentException("InputStream length is not > 0");
            }

            int readLength = inputStream.read(buffer, 0, BUFFER_TOTAL_SIZE - 1);
            buffer[readLength] = EOF;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析出一个token
     */
    public Token parseToken() throws IOException {
        // 重置一下状态，以免收到上次计算结果的影响
        reset();

        byte nextCh;
        do {
            nextCh = getNextCh();

            // 输入一个字符，移动所有的状态机状态
            moveAllStateMachine(nextCh);
            if (allStateMachineFinish()) {
                // 所有的状态机都已经结束
                // 因为接受的状态机不止一个，所以需要选取一个作为最终结果
                StateMachine sm = peekFinalSM();
                if (sm == null) {
                    // 所有的状态机都已经结束，但是没有状态机接受，非法字符串，编译出错
                    throw new IllegalStateException("Illegal string");
                }

                // 设置下一个token的搜索起点
                forward -= 1;
                begin = forward;
                return sm.getToken();
            }
        } while (nextCh != EOF);

        return null;
    }

    private boolean allStateMachineFinish() {
        int count = 0;
        for (StateMachine stateMachine : stateMachines) {
            if (stateMachine.fail() || stateMachine.accept()) {
                ++count;
            }
        }

        return count == stateMachines.length;
    }

    private void moveAllStateMachine(byte ch) {
        for (StateMachine stateMachine : stateMachines) {
            if (!stateMachine.fail()) {
                stateMachine.move(ch);
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
                // 把right的数据复制到left
                System.arraycopy(buffer, BUFFER_SIZE, buffer, 0, BUFFER_SIZE - 1);

                // 把新数据读到right,最后一位设置为EOF
                int readLength = inputStream.read(buffer, BUFFER_SIZE - 1, BUFFER_SIZE);
                buffer[BUFFER_SIZE - 1 + readLength] = EOF;

                // 更新begin和forwar
                // begin和forward不会相差BUFFER_SIZE
                begin -= BUFFER_SIZE;
                forward -= BUFFER_SIZE;
            } else {
                // 输入流中没有字符了
                return EOF;
            }
        }

        return ch;
    }

    /**
     * 从所有的状态机中选取出最终结果
     * 定义的状态机数组中已经按照优先级排列了，所以找到的第一个状态机就是最终结果
     */
    private StateMachine peekFinalSM() {
        for (StateMachine stateMachine : stateMachines) {
            if (stateMachine.accept()) {
                return stateMachine;
            }
        }

        return null;
    }

    /**
     * 重新设置状态
     */
    private void reset() {
        for (StateMachine stateMachine : stateMachines) {
            stateMachine.reset();
        }
    }
}