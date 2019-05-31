package com.runmulticonfig;

import com.intellij.execution.*;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBusConnection;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author jiangzhen
 */
public class RunMultiConfigToolWindow
{
	private JPanel panel;
	private JButton changeRunConfigState;
	private JList canRunConfigList;
	private JList runConfigList;
	private JButton debugAllConfig;
	private JButton runAllConfig;
	private JPanel canRunConfigPanel;
	private JPanel runConfigPanel;
	private JPanel changeStatePanel;

	public RunMultiConfigToolWindow(Project project) {
		changeRunConfigState.addActionListener(e -> {

		});

		canRunConfigList.setModel(new DefaultListModel());
		DefaultListModel dlm = (DefaultListModel) canRunConfigList.getModel();
		dlm.addElement("Test");

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
				DefaultListModel dlm = (DefaultListModel) canRunConfigList.getModel();
				for (int i = 0; i < dlm.getSize(); ++i) {
					String name = (String)dlm.get(i);
					if (name.equals(environment.getRunnerAndConfigurationSettings().getName())) {
						return;
					}
				}

				dlm.addElement(environment.getRunnerAndConfigurationSettings().getName());
			}
		});
	}

	public void findAllServer(Project project) {
		RunManager runManager = RunManager.getInstance(project);

		List<RunnerAndConfigurationSettings> allConfigurationSetting = runManager.getAllSettings();
		List<RunnerAndConfigurationSettings> applicationConfigSettingList = new ArrayList<>();
		for (RunnerAndConfigurationSettings configSetting : allConfigurationSetting) {
			RunConfiguration configuration = configSetting.getConfiguration();
			if (ApplicationConfiguration.class.isAssignableFrom(configuration.getClass())) {
				applicationConfigSettingList.add(configSetting);
			}
		}

		for (RunnerAndConfigurationSettings runnerAndConfigurationSettings : applicationConfigSettingList)
		{
			canRunConfigList.add(new JLabel(runnerAndConfigurationSettings.getName()));
		}
	}

	private RunnerAndConfigurationSettings getConfigSettingByName(RunManager runManager, String name) {
		for (RunnerAndConfigurationSettings configSetting : runManager.getAllSettings()) {
			if (configSetting.getName().equals(name)) {
				return configSetting;
			}
		}

		return null;
	}

	public JPanel getContent() {
		return panel;
	}
}
