package com.mycode.research.jvm.my.oop.constant;

/**
 * @author jiangzhen
 * @date 2019/8/14 20:14
 */
public class AttributeInfo {
    private short nameIndex;
    private int length;
    private byte[] info;

    public AttributeInfo(short nameIndex, int length, byte[] info) {
        this.nameIndex = nameIndex;
        this.length = length;
        this.info = new byte[info.length];
        System.arraycopy(info, 0, this.info, 0, info.length);
    }

    public short getNameIndex() {
        return nameIndex;
    }

    public int getLength() {
        return length;
    }

    public byte[] getInfo() {
        return info;
    }
}
