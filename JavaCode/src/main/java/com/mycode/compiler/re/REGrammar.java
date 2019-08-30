package com.mycode.compiler.re;

/**
 * 正则表达式文法
 *
 * @author jiangzhen
 * @date 2019/8/28 20:58
 */
public class REGrammar {
    /**
     * 正则表达式文法(左递归)
     */
    private final static String[] re = {
            "Exp -> Exp|Exp | Exp* | (Exp) | ΣExp| ε", //  Σ就是字母集中的一个字母
    };

    /*
        使用立即左递归消除法，得到一个无左递归的文法，参考龙书第二版4.3.3
        α1 = |exp
        α2 = *
        β1 = (exp)
        β2 = ΣExp
        β3 = ε
     */

    /**
     * 消除左递归后的re文法
     * (abc)|(dfe)
     * a|b(c|d)
     */
    private final static String[] re2 = {
            "exp -> (exp)R   |  ΣExpR  |   ε      ",
            "R   -> |expR    |  *R     |  ε "
    };
}
