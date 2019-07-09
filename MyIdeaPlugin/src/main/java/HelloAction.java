import com.intellij.execution.*;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.util.messages.MessageBusConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.jetbrains.annotations.NotNull;

/**
 * @author jiangzhen
 */
public class HelloAction extends AnAction {
	private static AtomicLong id = new AtomicLong(100);

    public HelloAction() {
        super("Hello");
    }

    public void actionPerformed(AnActionEvent event) {
        RunManager runManager = RunManager.getInstance(event.getProject());
	    List<RunnerAndConfigurationSettings> allConfigurationSetting = runManager.getAllSettings();
	    List<RunnerAndConfigurationSettings> needRunSettingList = new ArrayList<>();
	    for (RunnerAndConfigurationSettings configSetting : allConfigurationSetting)
	    {
		    RunConfiguration configuration = configSetting.getConfiguration();
		    if (ApplicationConfiguration.class.isAssignableFrom(configuration.getClass())) {
			    needRunSettingList.add(configSetting);
		    }
	    }

	    MessageBusConnection connection = ApplicationManager.getApplication().getMessageBus().connect();
	    connection.subscribe(ExecutionManager.EXECUTION_TOPIC, new ExecutionListener() {
		    @Override
		    public void processStartScheduled(@NotNull String executorId, @NotNull ExecutionEnvironment environment) {
		    }

		    @Override
		    public void processNotStarted(@NotNull String executorId, @NotNull ExecutionEnvironment environment) {
		    }

		    @Override
		    public void processStarted(@NotNull String executorId, @NotNull ExecutionEnvironment environment, @NotNull ProcessHandler handler) {
			    if (needRunSettingList.size() > 0) {
				    RunnerAndConfigurationSettings settings = needRunSettingList.remove(0);
				    ProgramRunnerUtil.executeConfiguration(settings, DefaultDebugExecutor.getDebugExecutorInstance());
			    }
		    }
	    });

	    if (needRunSettingList.size() > 0) {
		    RunnerAndConfigurationSettings settings = needRunSettingList.remove(0);
		    ProgramRunnerUtil.executeConfiguration(settings, DefaultDebugExecutor.getDebugExecutorInstance());
	    }
    }
}
