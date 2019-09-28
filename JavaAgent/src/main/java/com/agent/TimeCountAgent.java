package com.agent;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author jiangzhen
 * @date 2019/9/24 20:08
 */
public class StepSaveAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("--------premain------------");
        inst.addTransformer(new ClassFileTransformer() {
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                try {
                    ClassReader cr= new ClassReader(className);
                    ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    TimeCountAdpter timeCountAdpter=new TimeCountAdpter(cw);

                    cr.accept(timeCountAdpter,ClassReader.EXPAND_FRAMES);

                    return cw.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("--------premain------------");
        inst.addTransformer(new ClassFileTransformer() {
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                try {
                    ClassReader cr = new ClassReader(className);
                    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    TimeCountAdpter timeCountAdpter = new TimeCountAdpter(cw);

                    cr.accept(timeCountAdpter,ClassReader.EXPAND_FRAMES);

                    return cw.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}
