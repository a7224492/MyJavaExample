package com.mycode.compiler.automate;

import java.util.*;

/**
 * @author jiangzhen
 * @date 2019/8/30 17:28
 */
public class DFA {
    /**
     * 保存自动机的所有状态
     *
     * 这里使用的自动机都只有一个开始状态和一个接受状态
     * 第一个状态是开始状态，最后一个状态时接受状态
     *
     * 状态机处于倒数第二个状态时，输入任意字符串，都会转到终态
     * 这样设计的目的是方便词法分析中token的解析代码编写
     */
    private List<Map<String, Integer>> stateList = new ArrayList<>();

    public DFA(List<Map<String, Integer>> stateList) {
        this.stateList.addAll(stateList);
    }

    public void addMoveTableEntry(int state, String input, int nextStat) {
        Map<String, Integer> moveTable = stateList.get(state);
        moveTable.put(input, nextStat);
    }
}
