package com.mycode.research.jvm.agent;

import com.mycode.research.jvm.TestMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
	private static final String TARGET_CLASS_NAME = "com/mycode/research/jvm/TestMethod";

	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("--------premain------------");
		inst.addTransformer(new ClassFileTransformer()
		{
			@Override
			public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
				throws IllegalClassFormatException
			{
				System.out.println("Transform className is " + className);
				if (!className.equals(TARGET_CLASS_NAME)) {
					return null;
				}

				System.out.println("Start transform TestMethod.class");
				return getBytesFromFile(TARGET_CLASS_NAME);
			}
		}, true);
	}

	public static byte[] getBytesFromFile(String fileName) {
		System.out.println("get bytes from file, fileName is " + fileName);

		InputStream is = null;
		try {
			// precondition
			is = ClassLoader.getSystemResourceAsStream(fileName + ".class");
			long length = is.available();
			byte[] bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset <bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				System.err.println("Could not completely read file " + fileName);
			}
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void agentmain(String agentArgs, Instrumentation inst)
		throws UnmodifiableClassException
	{
		System.out.println("-----------agentmain--------------");
		inst.retransformClasses(TestMethod.class);
	}
}
