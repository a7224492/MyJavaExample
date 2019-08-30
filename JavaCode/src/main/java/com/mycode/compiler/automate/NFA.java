package com.mycode.compiler.automate;

import java.util.*;

/**
 * @author jiangzhen
 * @date 2019/8/29 12:49
 */
public class NFA {
    /**
     * 表示输入任意字符
     */
    public static final String ANY_INPUT = "*";

    /**
     * 表示其他字符串
     */
    public static final String OTHER_INPUT = "^";

    public static final String EMPTY_INPUT = "ε";

    /**
     * 保存自动机的所有状态
     *
     * 这里使用的自动机都只有一个开始状态和一个接受状态
     * 第一个状态是开始状态，最后一个状态时接受状态
     *
     * 状态机处于倒数第二个状态时，输入任意字符串，都会转到终态
     * 这样设计的目的是方便词法分析中token的解析代码编写
     */
    private List<Map<String, Set<Integer>>> stateList = new ArrayList<>();

    public NFA(int stateCount) {
        for (int i = 0; i < stateCount; ++i) {
            stateList.add(new HashMap<>());
        }
    }

    /**
     * 允许使用纯字符串来定义一个状态机,字符串格式举例如下
     * {"a1", "b2"} 状态0的转换表 输入a转到状态1 输入b转到状态2
     * {"c0"}       状态1的转换表 输入c转到状态0
     * {}           状态2的转换表
     *
     * 一个状态如果没有转换表，也需要填写{}，因为此方法会把table的长度作为玩家声明的状态数量
     *
     * NFA初始化完成后，会在末尾自动添加一个状态作为终态
     *
     * @param table 用字符串表示的状态转换图
     */
    public static NFA build(String[][] table) {
        NFA n = new NFA(table.length + 1);

        for (int i = 0; i < table.length; ++i) {
            for (String entry : table[i]) {
                entry = entry.trim();
                int nextState   = entry.charAt(entry.length() - 1) - 48;
                if (nextState >= table.length) {
                    throw new IllegalStateException("table中声明的状态，第" + ( i + 1) +"行，" + entry + "超过了table的大小，不符合要求");
                }

                String input = entry.substring(0, entry.length() - 1);
                n.addMoveTableEntry(i, input, nextState);
            }
        }

        if (n.stateList.get(n.getStateCount() - 2).isEmpty()) {
            // 声明的最后一个状态没有转换表，使用ANY_INPUT
            n.addMoveTableEntry(n.getStateCount() - 2, ANY_INPUT, n.getStateCount() - 1);
        } else {
            // 声明的最后一个状态有转换表，使用OTHER_INPUT
            n.addMoveTableEntry(n.getStateCount() - 2, OTHER_INPUT, n.getStateCount() - 1);
        }

        return n;
    }

    /**
     * 状态转换
     * @param currentState 当前状态
     * @param input 输入字符
     * @return 可以转换的状态
     */
    public Set<Integer> moveToNextState(int currentState, String input) {
        if (currentState < 0 || currentState >= stateList.size()) {
            throw new IllegalArgumentException("currentState doesn't exist!");
        }

        Map<String, Set<Integer>> nextStateMap = stateList.get(currentState);
        if (nextStateMap.containsKey(ANY_INPUT)) {
            return nextStateMap.get(ANY_INPUT);
        }

        Set<Integer> nextStateSet = nextStateMap.get(input);
        if (nextStateSet == null || nextStateSet.isEmpty()) {
            if (nextStateMap.containsKey(OTHER_INPUT)) {
                return nextStateMap.get(OTHER_INPUT);
            }
        }

        return nextStateSet;
    }

    public void addMoveTableEntry(int state, String input, int next) {
        Map<String, Set<Integer>> moveTable = stateList.get(state);
        Set<Integer> nextStateList = moveTable.get(input);
        if (nextStateList == null) {
            nextStateList = new HashSet<>();
        }

        nextStateList.add(next);
        moveTable.put(input, nextStateList);
    }

    public void addState() {
        stateList.add(new HashMap<>());
    }

    public void addNFA(int desStartState, NFA n, int startState, int endState, int inc) {
        int newState = desStartState;
        for (int nstate = startState; nstate <= endState; ++nstate) {
            // 更新moveTable
            Map<String, Set<Integer>> moveTable = n.getMoveTable(nstate);
            Map<String, Set<Integer>> newMoveTable = updateMoveTable(moveTable, inc);

            // 放入合并之后的nfa中
            addMoveTable(newState, newMoveTable);
            ++newState;
        }
    }

    public Map<String, Set<Integer>> updateMoveTable(Map<String, Set<Integer>> moveTable, int inc) {
        Map<String, Set<Integer>> result = new HashMap<>();

        for (Map.Entry<String, Set<Integer>> entry : moveTable.entrySet()) {
            Set<Integer> newList = new HashSet<>();
            for (Integer v : entry.getValue()) {
                newList.add(v + inc);
            }

            result.put(entry.getKey(), newList);
        }

        return result;
    }

    public int getStateCount() {
        return stateList.size();
    }

    public Map<String, Set<Integer>> getMoveTable(int i) {
        return stateList.get(i);
    }

    public void addMoveTable(int state, Map<String, Set<Integer>> moveTable) {
        for (Map.Entry<String, Set<Integer>> entry : moveTable.entrySet()) {
            for (Integer nextState : entry.getValue()) {
                addMoveTableEntry(state, entry.getKey(), nextState);
            }
        }
    }

    public void removeAnyOther(int state) {
        Map<String, Set<Integer>> moveTable = stateList.get(state);
        moveTable.remove(ANY_INPUT);
        moveTable.remove(OTHER_INPUT);
    }
}
