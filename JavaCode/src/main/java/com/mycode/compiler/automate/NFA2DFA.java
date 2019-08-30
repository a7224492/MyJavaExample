package com.mycode.compiler.automate;

import java.util.*;

/**
 * @author jiangzhen
 * @date 2019/8/30 17:28
 */
public class NFA2DFA {
    public static DFA nfa2Dfa(NFA nfa) {
        List<Map<String, Integer>> result = new ArrayList<>();

        // 标记表
        Set<Set<Integer>> markSet = new HashSet<>();

        // 设置初始状态
        LinkedList<Set<Integer>> queue = new LinkedList<>();
        Set<Integer> startStateSet = nfa.getEmptyClosure(0);
        queue.offer(startStateSet);

        int state = 0;
        while (!queue.isEmpty()) {
            // 标记已经访问过的状态集
            Set<Integer> currentSet = queue.poll();
            markSet.add(currentSet);
            result.add(new HashMap<>());

            Map<String, Set<Integer>> m = nfa.moveToNextStateSet(currentSet);
            for (Map.Entry<String, Set<Integer>> entry : m.entrySet()) {
                for (Integer nextState : entry.getValue()) {
                    Set<Integer> emptyClosure = nfa.getEmptyClosure(nextState);
                    if (!markSet.contains(emptyClosure)) {
                        queue.offer(emptyClosure);

                        // 添加到DFA中
                        Map<String, Integer> moveTable = result.get(result.size() - 1);
                        moveTable.put(entry.getKey(), ++state);
                    }
                }
            }
        }

        return new DFA(result);
    }
}
