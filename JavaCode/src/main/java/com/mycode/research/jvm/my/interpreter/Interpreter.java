package com.mycode.research.jvm.my.interpreter;

import com.mycode.research.jvm.my.code.Code;
import com.mycode.research.jvm.my.code.InterpreterCode;
import com.mycode.research.jvm.my.oop.JKlass;
import com.mycode.research.jvm.my.oop.JMethod;
import com.mycode.research.jvm.my.oop.SystemDictionary;
import com.mycode.research.jvm.my.oop.constant.JConstantPool;
import com.mycode.research.jvm.my.oop.constant.MethodRef;
import com.mycode.research.jvm.my.oop.constant.NameAndType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 解释器模块，用来执行字节码
 *
 * @author jiangzhen
 */
public class Interpreter {
    private LinkedList<JFrame> frameList = new LinkedList<>();

    private JFrame currentFrame;

    /**
     * 执行字节码
     */
    public void executeCode(InterpreterCode interpreterCode) {
        int index = -1;

        switch (interpreterCode.getCode()) {
            case ALOAD0:
                Object o = currentFrame.getLocalTable().get(0);
                currentFrame.getOperandStack().push(o);
                break;
            case INVOKEVIRTUAL:
            case INVOKESPECIAL:
            case INVOKESTATIC:
                // 1. 得到当前所在类的常量池
                JConstantPool constantPool = currentFrame.getCurrentMethod().getConstantPool();

                // 2. 得到方法名
                index = interpreterCode.parseOperand();
                MethodRef methodRef = (MethodRef) constantPool.getConstantPoolEntry(index);
                short classNameIndex = (short) constantPool.getConstantPoolEntry(methodRef.getClassIndex());
                String className = (String) constantPool.getConstantPoolEntry(classNameIndex);
                NameAndType nameAndType = (NameAndType) constantPool.getConstantPoolEntry(methodRef.getNameAndTypeIndex());
                String methodName = (String) constantPool.getConstantPoolEntry(nameAndType.getNameIndex());

                if (className.equals("java/io/PrintStream") && methodName.equals("println")) {
                    // 目前暂不处理这些jdk里面的方法，如果遇到，直接调用
                    String param = String.valueOf(currentFrame.getOperandStack().pop());
                    System.out.println(param);
                } else {
                    JKlass klass = SystemDictionary.getKlass(className);
                    if (klass == null) {
                        throw new IllegalStateException("Can't find klass by name");
                    }

                    // 3. 根据方法名找到对应的方法类
                    JMethod method = klass.findMethod(methodName);

                    // 4. 得到参数
                    List<Object> paramList = new ArrayList<>(method.getParam().length());
                    for (int i = method.getParam().length() - 1; i >= 0; --i) {
                        // 倒序遍历的原因是为了保证参数的传入顺序和被调用方法的参数顺序保持一致
                        Object param = currentFrame.getOperandStack().pop();
                        paramList.add(0, param);
                    }

                    // 5. 调用方法
                    callMethod(method, paramList);
                }

                break;
            case GETSTATIC:
                break;
            case BIPUSH:
                currentFrame.getOperandStack().push(interpreterCode.getOperands()[0]);
                break;
            case ISTORE1:
                index = 1;
                break;
            case ISTORE2:
                index = 2;
                break;
            case ISTORE3:
                index = 3;
                break;
            case IADD:
                int op1,op2;
                Object v1 = currentFrame.getOperandStack().pop();
                Object v2 = currentFrame.getOperandStack().pop();
                if (v1 instanceof Byte) {
                    op1 = (int)((byte)v1);
                } else {
                    op1 = (int)v1;
                }

                if (v2 instanceof Byte) {
                    op2 = (int)((byte)v2);
                } else {
                    op2 = (int)v2;
                }

                currentFrame.getOperandStack().push(op1 + op2);
                break;
            case ILOAD0:
                index = 0;
                break;
            case ILOAD1:
                index = 1;
                break;
            case ILOAD2:
                index = 2;
                break;
            case ILOAD3:
                index = 3;
                break;
            case IRETURN:
            case RETURN:
                LinkedList<Object> operandStack = currentFrame.getOperandStack();

                frameList.pop();
                currentFrame = frameList.peek();

                while (!operandStack.isEmpty()) {
                    currentFrame.getOperandStack().push(operandStack.pop());
                }

                break;
            default:
                System.err.println("Can't find instruction!");
                break;
        }

        switch (interpreterCode.getCode()) {
            case ILOAD0:
            case ILOAD1:
            case ILOAD2:
            case ILOAD3:
                currentFrame.getOperandStack().push(currentFrame.getLocalTable().get(index));
                break;
            case ISTORE1:
            case ISTORE2:
            case ISTORE3:
                Object o = currentFrame.getOperandStack().pop();
                if (index >= currentFrame.getLocalTable().size()) {
                    currentFrame.getLocalTable().add(o);
                } else {
                    currentFrame.getLocalTable().set(index, o);
                }
                break;
        }
    }

    /**
     * 调用一个java方法
     * @param method
     */
    public void callMethod(JMethod method, List<Object> paramList) {
        List<Object> localTable = new ArrayList<>();
        for (Object param : paramList) {
            localTable.add(param);
        }

        // 新建栈帧
        JFrame frame = new JFrame();
        frame.setCurrentMethod(method);
        frame.setOperandStack(new LinkedList<>());
        frame.setLocalTable(localTable);

        frameList.push(frame);
        currentFrame = frame;

        int i = 0;
        byte[] codes = method.getCode();
        while (i < codes.length) {
            // 1. 取指
            byte codeId = codes[i];
            Code code = Code.parse(codeId);
            if (code == null) {
                throw new IllegalStateException("Can't parse code from byte array");
            }

            byte[] operand = new byte[code.len() - 1];
            System.arraycopy(codes, i + 1, operand, 0, operand.length);
            InterpreterCode interpreterCode = new InterpreterCode(code, operand);

            // 取指完成，i移到下一个指令的地址
            i += code.len();

            // 2. 译码
            executeCode(interpreterCode);
        }
    }
}
