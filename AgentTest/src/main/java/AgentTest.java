import java.lang.instrument.Instrumentation;

/**
 * @author jiangzhen
 */
public class AgentTest
{
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("--------premain------------");
	}

	public static void agentmain(String agentArgs, Instrumentation inst) {

	}
}
