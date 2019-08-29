package com.mycode.compiler.automate;

import java.util.*;

/**
 * @author jiangzhen
 * @date 2019/8/29 12:49
 */
public class NFA {
    /**
     * 保存自动机的所有状态
     * 这里使用的自动机都只有一个开始状态和一个接受状态
     * 第一个状态是开始状态，最后一个状态时接受状态
     */
    private List<Map<String, List<Integer>>> stateList = new ArrayList<>();

    public NFA(int stateCount) {
        for (int i = 0; i < stateCount; ++i) {
            stateList.add(new HashMap<>());
        }
    }

    /**
     * 允许使用纯字符串来定义一个状态机
     * @param table 用字符串表示的状态转换图
     */
    public static NFA build(String[][] table) {
        NFA n = new NFA(table.length);

        for (int i = 0; i < table.length; ++i) {
            for (String entry : table[i]) {
                entry = entry.trim();
                int nextState   = entry.charAt(entry.length() - 1) - 48;
                String input = entry.substring(0, entry.length() - 1);

                n.addMoveTableEntry(i, input, nextState);
            }
        }

        return n;
    }

    public void addMoveTableEntry(int state, String input, int next) {
        Map<String, List<Integer>> moveTable = stateList.get(state);
        List<Integer> nextStateList = moveTable.get(input);
        if (nextStateList == null) {
            nextStateList = new ArrayList<>();
        }

        nextStateList.add(next);
        moveTable.put(input, nextStateList);
    }

    public Map<String, List<Integer>> getMoveTable(int state) {
        return stateList.get(state);
    }

    public int getStateCount() {
        return stateList.size();
    }
}
