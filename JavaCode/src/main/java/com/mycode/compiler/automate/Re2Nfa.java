package com.mycode.compiler.automate;

import com.mycode.compiler.Defines;

/**
 * @author jiangzhen
 * @date 2019/8/29 18:52
 */
public class Re2Nfa {
    private String re;
    private int p = 0;

    public Re2Nfa(String re) {
        this.re = re;
    }

    public NFA match() {
        char c = getCh();
        if (c == 0) {
            return NFA.EMPTY_NFA;
        } else if (c == '(') {
            NFA n1 = match();
            // 跳过')'
            ++p;
            return matchR(n1);
        } else if (Defines.isLetter((byte)c) || Defines.isNumber((byte)c)){
            NFA n = NFA.build(c);
            NFA n1 = match();
            NFA n2 = NFATransform.NFAUnionNFA(n, n1);
            return matchR(n2);
        } else {
            throw new IllegalStateException("re parse error");
        }
    }

    private NFA matchR(NFA n1) {
        char ch = getCh();
        if (ch == 0) {
            return n1;
        } else if (ch == '|') {
            NFA n2 = match();
            NFA n3 = matchR(n2);
            return NFATransform.NFAOrNFA(n1, n3);
        } else if (ch == '*') {
            NFA n2 = NFATransform.NFAStar(n1);
            return matchR(n2);
        } else {
            throw new IllegalStateException("re parse error");
        }
    }

    private char getCh() {
        if (p > re.length()) {
            return 0;
        } else {
            return re.charAt(p++);
        }
    }
}
