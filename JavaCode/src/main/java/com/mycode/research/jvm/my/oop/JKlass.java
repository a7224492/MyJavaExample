package com.mycode.research.jvm.my.oop;

import com.mycode.research.jvm.my.oop.constant.JConstantPool;

import java.util.List;

/**
 * @author jiangzhen
 * @date 2019/8/14 14:36
 */
public class JKlass {
    private List<JField> fieldList;
    private List<JMethod> methodList;
    private JConstantPool constantPool;
    private String name;

    public void setMethodList(List<JMethod> methodList) {
        this.methodList = methodList;
    }

    public void setConstantPool(JConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JField findField(String name) {
        for (JField field : fieldList) {
            if (field.getName().equals(name)) {
                return field;
            }
        }

        return null;
    }

    public JMethod findMethod(String name) {
        for (JMethod method : methodList) {
            if (method.getName().equals(name)) {
                return method;
            }
        }

        return null;
    }
}
