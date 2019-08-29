package com.mycode.compiler.automate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangzhen
 * @date 2019/8/29 11:45
 */
public class MyTransform {
    public void re2NFA(String re) {

    }

    /**
     * 具体算法请参考龙书第二版3.7.4
     *     ε     ε
     *     -> n1 ->
     * i ->        -> f
     *     -> n2 ->
     *     ε     ε
     */
    public static NFA NFAOrNFA(NFA n1, NFA n2) {
        NFA nfa = new NFA(1 + n1.getStateCount() + n2.getStateCount() + 1);

        // 构造起点movetable
        nfa.addMoveTableEntry(0, "ε", 1);
        nfa.addMoveTableEntry(0, "ε", 1 + n1.getStateCount());

        // 根据n1构造movetable
        addNFA(nfa, n1, 1);

        // 根据n2构造movetable
        addNFA(nfa, n2, 1 + n2.getStateCount());

        // 构造终点moveTable
        nfa.addMoveTableEntry(1 + n1.getStateCount() - 1, "ε", 1 + n1.getStateCount() + n2.getStateCount());
        nfa.addMoveTableEntry(1 + n1.getStateCount() + n2.getStateCount() - 1, "ε", 1 + n1.getStateCount() + n2.getStateCount());

        return nfa;
    }

    /**
     * 具体算法请参考龙书第二版3.7.4
     * -> n1 -> n2 ->
     */
    public static NFA NFAUnionNFA(NFA n1, NFA n2) {
        NFA nfa = new NFA(n1.getStateCount() + n2.getStateCount());
        addNFA(nfa, n1, 0);
        addNFA(nfa, n2, n1.getStateCount());
        return nfa;
    }

    /**
     * 具体算法请参考龙书第二版3.7.4
     */
    public static NFA NFAStar(NFA n) {
        NFA nfa = new NFA(1 + n.getStateCount() + 1);

        nfa.addMoveTableEntry(0, "ε", 1);
        nfa.addMoveTableEntry(0, "ε", nfa.getStateCount() - 1);

        addNFA(nfa, n, 1);

        nfa.addMoveTableEntry(n.getStateCount(), "ε", 1);
        nfa.addMoveTableEntry(n.getStateCount(), "ε", nfa.getStateCount() - 1);

        return nfa;
    }

    private static void addNFA(NFA src, NFA tar, int inc) {
        for (int i = 0; i < tar.getStateCount(); ++i) {
            // 更新状态
            int newState = inc + i;

            // 更新moveTable
            Map<String, List<Integer>> moveTable = tar.getMoveTable(i);
            Map<String, List<Integer>> newMoveTable = updateMoveTable(moveTable, inc);

            // 放入合并之后的nfa中
            for (Map.Entry<String, List<Integer>> entry : newMoveTable.entrySet()) {
                for (Integer nextState : entry.getValue()) {
                    src.addMoveTableEntry(newState, entry.getKey(), nextState);
                }
            }
        }
    }

    private static Map<String, List<Integer>> updateMoveTable(Map<String, List<Integer>> moveTable, int inc) {
        Map<String, List<Integer>> result = new HashMap<>();

        for (Map.Entry<String, List<Integer>> entry : moveTable.entrySet()) {
            List<Integer> newList = new ArrayList<>();
            for (Integer v : entry.getValue()) {
                newList.add(v + inc);
            }

            result.put(entry.getKey(), newList);
        }

        return result;
    }
}
