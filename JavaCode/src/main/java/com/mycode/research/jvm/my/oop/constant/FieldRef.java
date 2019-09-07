package com.mycode.research.jvm.my.oop.constant;

/**
 * @author jiangzhen
 * @date 2019/8/15 13:52
 */
public class FieldRef {
    private short classIndex;
    private short nameAndTypeIndex;

    public FieldRef(short classIndex, short nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public short getClassIndex() {
        return classIndex;
    }

    public short getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }
}
