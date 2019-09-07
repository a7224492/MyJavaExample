package com.mycode.research.jvm.my.oop.constant;

import com.mycode.research.jvm.my.oop.JField;
import com.mycode.research.jvm.my.oop.JKlass;
import com.mycode.research.jvm.my.oop.JMethod;
import com.mycode.research.jvm.my.oop.SystemDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangzhen
 * @date 2019/8/14 14:37
 */
public class JConstantPool {
    private List<Byte> tagList = new ArrayList<>();
    private List<Object> entryList = new ArrayList<>();

    public JConstantPool() {
        tagList.add((byte)0);
        entryList.add(new Object());
    }

    public Object getConstantPoolEntry(int index) {
        return entryList.get(index);
    }

    public JField getField(int index) {
        FieldRef fieldRef = (FieldRef)getConstantPoolEntry(index);
        String className = (String) getConstantPoolEntry(fieldRef.getClassIndex());

        NameAndType nameAndType = (NameAndType) getConstantPoolEntry(fieldRef.getNameAndTypeIndex());
        String fieldName = (String) getConstantPoolEntry(nameAndType.getNameIndex());

        JKlass klass = SystemDictionary.getKlass(className);
        if (klass == null) {
            throw new IllegalStateException("Can't find klass by name");
        }

        JField field = klass.findField(fieldName);
        return field;
    }

    public JMethod getMethod(int index) {
        MethodRef methodRef = (MethodRef) getConstantPoolEntry(index);
        short classNameIndex = (short) getConstantPoolEntry(methodRef.getClassIndex());
        String className = (String) getConstantPoolEntry(classNameIndex);

        NameAndType nameAndType = (NameAndType) getConstantPoolEntry(methodRef.getNameAndTypeIndex());
        String methodName = (String) getConstantPoolEntry(nameAndType.getNameIndex());

        JKlass klass = SystemDictionary.getKlass(className);
        if (klass == null) {
            throw new IllegalStateException("Can't find klass by name");
        }

        JMethod method = klass.findMethod(methodName);
        return method;
    }

    public void addTag(byte tag) {
        tagList.add(tag);
    }

    public void addEntry(Object entry) {
        entryList.add(entry);
    }
}
