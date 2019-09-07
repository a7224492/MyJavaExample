package com.mycode.compiler.syntax;

/**
 * @author jiangzhen
 * @date 2019/8/28 10:39
 */
public class Grammar {
    private static final String[] grammar = {
            "Stmts -> StmtsStmt",
            "Stmt  -> if(Exp)Stmt|while(Exp)Stmt",
            "Exp   -> ExpRelopExp|Digit",
            "Relop -> <|>|==|<=|>=",
            "Digit -> 0|1|2|3|4|5|6|7|8|9"
    };
}
