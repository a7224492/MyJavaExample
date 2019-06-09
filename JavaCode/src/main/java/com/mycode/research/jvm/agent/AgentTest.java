package com.mycode.research.jvm.agent;

import com.mycode.research.jvm.TestMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

/**
 * @author jiangzhen
 */
public class AgentTest
{
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("--------premain------------");
		inst.addTransformer(new ClassFileTransformer() {
			@Override
			public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
				return classfileBuffer;
			}
		});
	}

	public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
		System.out.println("---------agentmain-------------------");
		inst.retransformClasses(TestMethod.class);
	}
}
