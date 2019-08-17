package com.mycode.research.jvm.my;

import com.mycode.research.jvm.my.code.Code;
import com.mycode.research.jvm.my.code.InterpreterCode;
import com.mycode.research.jvm.my.oop.JMethod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
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
                byte[] operand = interpreterCode.getOperand();
                index = operand[0] << 8 | operand[1];
                JMethod method = currentFrame.getCurrentMethod().getConstantPool().getMethod(index);

                List<Object> paramList = new ArrayList<>(method.getParam().length());
                for (int i = method.getParam().length() - 1; i >= 0; --i) {
                    // 倒序遍历的原因是为了保证参数的传入顺序和被调用方法的参数顺序保持一致
                    Object param = currentFrame.getOperandStack().pop();
                    paramList.add(0, param);
                }
                callMethod(method, paramList);
                break;
            case GETSTATIC:
//                operand = interpreterCode.getOperand();
//                index = operand[0] << 8 | operand[1];
//                JField field = currentFrame.getCurrentMethod().getConstantPool().getField(index);
//                currentFrame.getOperandStack().push(field);

                break;
            case BIPUSH:
                currentFrame.getOperandStack().push(interpreterCode.getOperand()[0]);
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
                currentFrame.getOperandStack().push(currentFrame.getLocalTable().get(index));
                break;
            case ILOAD1:
                index = 1;
                currentFrame.getOperandStack().push(currentFrame.getLocalTable().get(index));
                break;
            case ILOAD2:
                index = 2;
                currentFrame.getOperandStack().push(currentFrame.getLocalTable().get(index));
                break;
            case ILOAD3:
                index = 3;
                currentFrame.getOperandStack().push(currentFrame.getLocalTable().get(index));
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
