package com.mycode.research.jvm.oop;

import java.util.*;
import sun.jvm.hotspot.memory.SystemDictionary;
import sun.jvm.hotspot.oops.InstanceKlass;
import sun.jvm.hotspot.oops.Klass;
import sun.jvm.hotspot.oops.Oop;
import sun.jvm.hotspot.runtime.VM;
import sun.jvm.hotspot.tools.Tool;

import static sun.misc.OSEnvironment.initialize;

/**
 * @author jiangzhen
 */
public class SystemDictionaryTest extends Tool
{
	public static void main(String[] args) {
		VM.registerVMInitializedObserver(new Observer() {
			public void update(Observable o, Object data) {
				initialize();
			}
		});

		SystemDictionaryTest test = new SystemDictionaryTest();
		String[] newArgs = {"22012"};
		test.execute(newArgs);
	}

	@Override
	public void run() {
		List<InstanceKlass> klassList = new ArrayList<>();
		SystemDictionary systemDictionary = VM.getVM().getSystemDictionary();
		systemDictionary.classesDo(new SystemDictionary.ClassVisitor() {
			@Override
			public void visit(Klass klass) {
				if (klass instanceof InstanceKlass) {
					InstanceKlass ik = (InstanceKlass)klass;
					klassList.add(ik);
				}
			}
		});

		klassList.sort(Comparator.comparing(k -> k.getName().asString()));

		Map<String, Integer> m = new HashMap<>();
		for (InstanceKlass instanceKlass : klassList) {
			Integer count = m.get(instanceKlass.getInitState().toString());
			if (count == null) {
				count = 0;
			}

			++count;
			m.put(instanceKlass.getInitState().toString(), count);

			Oop classLoader = instanceKlass.getClassLoader();
//			System.out.println(instanceKlass.getName().asString() + "    " + instanceKlass.getInitState() + "   " + (classLoader == null ? "null" : classLoader.getKlass()));
		}

		for (Map.Entry<String, Integer> entry : m.entrySet()) {
//			System.out.println(entry.getKey() + ":   " + entry.getValue());
		}
	}
}
