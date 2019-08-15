package com.mycode.research.jvm.my.oop;

import com.mycode.research.jvm.my.oop.constant.JConstantPool;

/**
 * @author jiangzhen
 * @date 2019/8/14 13:49
 */
public class JMethod {
    private byte[] code;
    private String name;
    private String returnType;
    private String param;

    private JConstantPool constantPool;

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public JConstantPool getConstantPool() {
        return constantPool;
    }

    public void setConstantPool(JConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
