package com.mycode.compiler.automate;

import java.util.Map;
import java.util.Set;

/**
 * @author jiangzhen
 * @date 2019/8/29 11:45
 */
public class NFATransform {
    /**
     * 具体算法请参考龙书第二版3.7.4
     * 这里的状态表示和龙书里的略有差别，{@link NFA}
     *     ε     ε
     *     -> n1(不包括终态) ->
     * i ->                   -> f -> 终态
     *     -> n2(不包括终态) ->
     *     ε     ε
     */
    public static NFA NFAOrNFA(NFA n1, NFA n2) {
        if (n1 == NFA.EMPTY_NFA) {
            return n2;
        }

        if (n2 == NFA.EMPTY_NFA) {
            return n1;
        }

        NFA nfa = new NFA(1 + (n1.getStateCount() - 1) + (n2.getStateCount() - 1) + 1 + 1);

        // 构造i movetable
        nfa.addMoveTableEntry(0,  NFA.EMPTY_INPUT, 1);
        nfa.addMoveTableEntry(0, NFA.EMPTY_INPUT, 1 + (n1.getStateCount() - 1));

        // 根据n1构造movetable
        nfa.addNFA(1, n1, 0, n1.getStateCount() - 2, 1);
        nfa.removeAnyOther(n1.getStateCount() - 1);

        // 根据n2构造movetable
        nfa.addNFA(n1.getStateCount(), n2, 0, n2.getStateCount() - 2, n1.getStateCount());
        nfa.removeAnyOther(n1.getStateCount() - 1 + n2.getStateCount() - 2);

        // 构造f moveTable
        nfa.addMoveTableEntry(1 + (n1.getStateCount() - 2), NFA.EMPTY_INPUT, 1 + n1.getStateCount() + n2.getStateCount());
        nfa.addMoveTableEntry(1 + (n1.getStateCount() - 1) + (n2.getStateCount() - 2), NFA.EMPTY_INPUT, 1 + n1.getStateCount() + n2.getStateCount());

        // 构造终态
        nfa.addMoveTableEntry(1 + (n1.getStateCount()- 1) + (n2.getStateCount() - 1), NFA.ANY_INPUT, nfa.getStateCount() - 1);

        return nfa;
    }

    /**
     * 具体算法请参考龙书第二版3.7.4
     * -> n1(不包含终态) -> (不包含始态)n2 ->
     */
    public static NFA NFAUnionNFA(NFA n1, NFA n2) {
        if (n1 == NFA.EMPTY_NFA) {
            return n2;
        }

        if (n2 == NFA.EMPTY_NFA) {
            return n1;
        }

        NFA nfa = new NFA(n1.getStateCount() - 1 + (n2.getStateCount() - 1));
        nfa.addNFA(0, n1, 0, n1.getStateCount() - 2, 0);
        nfa.removeAnyOther(n1.getStateCount() - 2);
        nfa.addNFA(n1.getStateCount() - 1, n2, 1, n2.getStateCount() - 1,  n1.getStateCount() - 1 - 1);

        // 拷贝n2的始态转换表到nfa中
        Map<String, Set<Integer>> moveTable = n2.getMoveTable(0);
        Map<String, Set<Integer>> newMoveTable = nfa.updateMoveTable(moveTable, n1.getStateCount() - 1 - 1);
        nfa.addMoveTable(n1.getStateCount() - 2, newMoveTable);

        return nfa;
    }

    /**
     * 具体算法请参考龙书第二版3.7.4
     */
    public static NFA NFAStar(NFA n) {
        if (n == NFA.EMPTY_NFA) {
            return n;
        }

        NFA nfa = new NFA(1 + n.getStateCount() + 1);

        nfa.addMoveTableEntry(0, NFA.EMPTY_INPUT, 1);
        nfa.addMoveTableEntry(0, NFA.EMPTY_INPUT, n.getStateCount());

        nfa.addNFA(1, n, 0, n.getStateCount() - 2, 1);
        nfa.removeAnyOther(n.getStateCount() - 2);

        nfa.addMoveTableEntry(n.getStateCount() - 2, NFA.EMPTY_INPUT, 1);
        nfa.addMoveTableEntry(n.getStateCount() - 2, NFA.EMPTY_INPUT, nfa.getStateCount() - 2);

        nfa.addMoveTableEntry(nfa.getStateCount() - 2, NFA.ANY_INPUT, nfa.getStateCount());
        return nfa;
    }
}
