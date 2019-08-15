package com.mycode.research.jvm.my.oop.parser;

import com.mycode.research.jvm.my.oop.JKlass;
import com.mycode.research.jvm.my.oop.JMethod;
import com.mycode.research.jvm.my.oop.constant.AttributeInfo;
import com.mycode.research.jvm.my.oop.constant.JConstantPool;
import com.mycode.research.jvm.my.oop.constant.MethodRef;
import com.mycode.research.jvm.my.oop.constant.NameAndType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycode.research.jvm.my.oop.parser.ConstantPoolTag.*;

/**
 * @author jiangzhen
 * @date 2019/8/14 19:14
 */
public class ClassParser {
    private static DataInputStream in;
    private static JConstantPool constantPool;

    private static Pattern methodDesPattern = Pattern.compile("(.*)");

    public static JKlass loadClassFile(InputStream inputStream) throws IOException {
        in = new DataInputStream(inputStream);

        // 版本号解析
        int magic = in.readInt();
        short minorVersion = in.readShort();
        short majorVersion = in.readShort();

        // 常量池解析
        constantPool = parseConstantPool();

        // 访问标识
        short accessFlags = in.readShort();

        // 类层级
        short thisClass = in.readShort();
        short classNameIndex = (short) constantPool.getConstantPoolEntry(thisClass);
        String className = (String) constantPool.getConstantPoolEntry(classNameIndex);

        short superClass = in.readShort();
        short interfaceCount = in.readShort();
        for (int i = 0; i < interfaceCount; ++i) {
            in.readShort();
        }

        // 属性
        int fieldCount = in.readShort();
        for (int i = 0; i < fieldCount; ++i) {
            accessFlags = in.readShort();
            short nameIndex = in.readShort();
            short descriptorIndex = in.readShort();
            List<AttributeInfo> attributeInfoList = parseAttribute();
        }

        // 方法
        List<JMethod> methodList = new ArrayList<>();
        int methodCount = in.readShort();
        for (int i = 0; i < methodCount; ++i) {
            JMethod method = parseMethod();
            methodList.add(method);
        }

        List<AttributeInfo> attributeInfoList = parseAttribute();

        // 构造一个类
        JKlass klass = new JKlass();
        klass.setConstantPool(constantPool);
        klass.setMethodList(methodList);
        klass.setName(className);
        return klass;
    }

    private static JMethod parseMethod() throws IOException {
        // 1. 方法名
        short accessFlags = in.readShort();
        short nameIndex = in.readShort();
        String name = (String) constantPool.getConstantPoolEntry(nameIndex);

        // 2. 字节码
        byte[] codes = null;
        short descriptorIndex = in.readShort();
        List<AttributeInfo> attributeInfoList = parseAttribute();
        for (AttributeInfo attributeInfo : attributeInfoList) {
            nameIndex = attributeInfo.getNameIndex();
            String codeName = (String) constantPool.getConstantPoolEntry(nameIndex);
            switch (codeName) {
                case "Code":
                    byte[] info = attributeInfo.getInfo();

                    int codeLength = 0;
                    for (int j = 0; j < 4; ++j) {
                        codeLength = (codeLength << 4) | info[4 + j];
                    }

                    codes = new byte[codeLength];
                    System.arraycopy(info, 8, codes, 0,codeLength);
                    break;
            }
        }

        // 3. 参数
        String param = "";
        String des = (String) constantPool.getConstantPoolEntry(descriptorIndex);
        Matcher matcher = methodDesPattern.matcher(des);
        if (matcher.find()) {
            String findStr = matcher.group(1);
            if (findStr.length() > 2) {
                param = findStr.substring(1, findStr.length() - 2);
            }
        }

        JMethod method = new JMethod();
        method.setCode(codes);
        method.setName(name);
        method.setConstantPool(constantPool);
        method.setParam(param);

        return method;
    }

    private static JConstantPool parseConstantPool() throws IOException {
        JConstantPool constantPool = new JConstantPool();
        int count = in.readShort();
        for (int i = 1; i < count; ++i) {
            byte tag = in.readByte();
            constantPool.addTag(tag);
            switch (tag) {
                case CLASS:
                    short nameIndex = in.readShort();
                    constantPool.addEntry(nameIndex);
                    break;
                case FIELD_REF:
                    in.readShort();
                    in.readShort();
                    constantPool.addEntry(new Object());
                    break;
                case METHOD_REF:
                    short classIndex = in.readShort();
                    short nameAndTypeIndex = in.readShort();
                    constantPool.addEntry(new MethodRef(classIndex, nameAndTypeIndex));
                    break;
                case INTERFACE_METHOD_REF:
                    in.readShort();
                    in.readShort();
                    constantPool.addEntry(new Object());
                    break;
                case STRING:
                    short stringIndex = in.readShort();
                    constantPool.addEntry(stringIndex);
                    break;
                case INTEGER:
                    int intValue = in.readInt();
                    constantPool.addEntry(intValue);
                    break;
                case FLOAT:
                    float f = in.readFloat();
                    constantPool.addEntry(f);
                    break;
                case LONG:
                    long l = in.readLong();
                    constantPool.addEntry(l);
                    break;
                case DOUBLE:
                    Double d = in.readDouble();
                    constantPool.addEntry(d);
                    break;
                case NAME_AND_TYPE:
                    nameIndex = in.readShort();
                    short descriptorIndex = in.readShort();
                    constantPool.addEntry(new NameAndType(nameIndex, descriptorIndex));
                    break;
                case UTF8:
                    short length = in.readShort();
                    byte[] bytes = new byte[length];
                    in.read(bytes);
                    String s = new String(bytes);
                    constantPool.addEntry(s);
                    break;
                case METHOD_HANDLE:
                    in.read();
                    in.readShort();
                    constantPool.addEntry(new Object());
                    break;
                case INVOKE_DYNAMIC:
                    in.readShort();
                    in.readShort();
                    constantPool.addEntry(new Object());
                    break;
            }
        }

        return constantPool;
    }

    private static List<AttributeInfo> parseAttribute() throws IOException {
        List<AttributeInfo> attributeList = new ArrayList<>();
        short count = in.readShort();
        for (int i = 0; i < count; ++i) {
            short nameIndex = in.readShort();
            int length = in.readInt();
            byte[] bytes = new byte[length];
            in.read(bytes);

            attributeList.add(new AttributeInfo(nameIndex, length, bytes));
        }

        return attributeList;
    }
}
