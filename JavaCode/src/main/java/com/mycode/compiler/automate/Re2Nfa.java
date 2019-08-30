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
        if (c == '(') {
            NFA n1 = match();
            // 跳过')'
            ++p;
            NFA n2 = matchR(n1);
            return n2;
        } else if (Defines.isLetter((byte)c) || Defines.isNumber((byte)c)){

            NFA n1 = match();
            NFA n2 = matchR(n1);
            return n2;
        } else {
            throw new IllegalStateException("re parse error");
        }
    }

    private char getCh() {
        return re.charAt(p++);
    }

    private NFA matchR(NFA n1) {
        char ch = getCh();
        if (ch == '|') {
            NFA n2 = match();

        }

        return null;
    }
}
