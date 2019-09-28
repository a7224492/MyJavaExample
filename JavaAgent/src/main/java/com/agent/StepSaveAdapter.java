package com.agent;

import java.util.Arrays;
import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class StepSaveAdapter extends ClassVisitor implements Opcodes {
    static final List<AspectData> ASPECT_DATA_LIST = Arrays.asList(
        new AspectData(
            "com/kodgames/battlecore/service/battle/BattleService",
            "startGame",
            "(Lcom/kodgames/battlecore/service/battle/common/xbean/BattleRoom;)V",
            "(Lcom/kodgames/battlecore/service/battle/common/xbean/BattleRoom;)V",
            new int[] {ALOAD}
        ),
        new AspectData(
            "com/kodgames/battlecore/service/battle/BattleService",
            "roundFinish",
            "(IIILcom/kodgames/message/proto/battle/BattleProtoBuf$BCMatchResultSYN;)V",
            "(IIILcom/kodgames/message/proto/battle/BattleProtoBuf$BCMatchResultSYN;)V",
            new int[] {ILOAD, ILOAD, ILOAD, ALOAD}
        ),
        new AspectData(
            "com/kodgames/battleserver/service/battle/processer/BattleProcesser",
            "processStep",
            "(II[BLjava/util/List;)V",
            "(II[BLjava/util/List;)Z",
            new int[] {ILOAD, ILOAD, ALOAD, ALOAD}
        ),
        new AspectData(
            "com/kodgames/battleserver/service/battle/region/guizhou/anlong/processer/TingProcess_AnLong",
            "processStep",
            "(II[BLjava/util/List;)V",
            "(II[BLjava/util/List;)Z",
            new int[] {ILOAD, ILOAD, ALOAD, ALOAD}
        )
    );

    private String owner;
    private boolean isInterface;

    StepSaveAdapter(ClassVisitor classVisitor) {
        super(ASM6, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        owner = name;
        isInterface = (access & ACC_INTERFACE) != 0;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, final String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        for (final AspectData aspectData : ASPECT_DATA_LIST)
        {
            if (aspectData.methodName.equals(name) && aspectData.des.equals(descriptor)) {
                return new MethodVisitor(ASM6, mv) {
                    @Override
                    public void visitCode() {
                        for (int i = 1; i <= aspectData.opCode.length; ++i) {
                            mv.visitVarInsn(aspectData.opCode[i - 1], i);
                        }

                        mv.visitMethodInsn(INVOKESTATIC, "com/agent/battle/SaveStep", aspectData.methodName, aspectData.aspectDes, false);
                        mv.visitCode();
                    }
                };
            }
        }

        return mv;
    }

    @Override
    public void visitEnd() {
        if (!isInterface) {
            int acc = Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL;
            String filedName = "UDASMCN";
            FieldVisitor fv = cv.visitField(acc, filedName,
                    "Ljava/lang/String;", null, owner);
            if (fv != null) {
                fv.visitEnd();
            }
        }
        cv.visitEnd();
    }
}

class AspectData {
    String className;
    String methodName;
    String aspectDes;
    String des;
    int opCode[];

    public AspectData(String className, String methodName, String aspectDes, String des, int[] opCode)
    {
        this.className = className;
        this.methodName = methodName;
        this.aspectDes = aspectDes;
        this.des = des;
        this.opCode = opCode;
    }
}
