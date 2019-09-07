package com.mycode.research.jvm.my.oop.constant;

/**
 * @author jiangzhen
 * @date 2019/8/14 16:37
 */
public class NameAndType {
    private int nameIndex;
    private int descriptorIndex;

    public NameAndType(int nameIndex, int descriptorIndex) {
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }
}
