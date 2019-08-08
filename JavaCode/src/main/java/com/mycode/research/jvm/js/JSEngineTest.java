package com.mycode.research.jvm.js;

import java.util.List;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

/**
 * @author jiangzhen
 */
public class JSEngineTest {
	public static void main(String[] args) {
		ScriptEngineManager manager = new ScriptEngineManager();
		List<ScriptEngineFactory> engineFactories = manager.getEngineFactories();
		System.out.println(engineFactories);
	}
}
