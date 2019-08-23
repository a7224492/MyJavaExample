package com.mycode.research.jvm.my.interpreter;

import com.mycode.research.jvm.my.oop.JMethod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jiangzhen
 * @date 2019/8/15 14:38
 */
public class JFrame {
    /**
     * 操作数栈
     */
    private LinkedList<Object> operandStack = new LinkedList<>();

    /**
     * 局部变量表
     */
    private List<Object> localTable = new ArrayList<>();

    /**
     * 当前方法
     */
    private JMethod currentMethod;

    public LinkedList<Object> getOperandStack() {
        return operandStack;
    }

    public List<Object> getLocalTable() {
        return localTable;
    }

    public JMethod getCurrentMethod() {
        return currentMethod;
    }

    public void setOperandStack(LinkedList<Object> operandStack) {
        this.operandStack = operandStack;
    }

    public void setLocalTable(List<Object> localTable) {
        this.localTable = localTable;
    }

    public void setCurrentMethod(JMethod currentMethod) {
        this.currentMethod = currentMethod;
    }
}
