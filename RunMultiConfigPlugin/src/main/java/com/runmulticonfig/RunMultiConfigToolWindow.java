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
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author jiangzhen
 */
public class RunMultiConfigToolWindow
{
	private JPanel panel;
	private JList canRunConfigList;
	private JList runConfigList;
	private JButton debugAllConfig;
	private JButton runAllConfig;
	private JPanel canRunConfigPanel;
	private JPanel runConfigPanel;
	private JButton moveToRunList;
	private JButton moveToCanRunList;
	private Project project;

	private Queue<Runnable> executeTaskQueue = new LinkedList<>();

	public RunMultiConfigToolWindow(Project project) {
		this.project = project;

		canRunConfigList.setModel(new DefaultListModel());
		runConfigList.setModel(new DefaultListModel());

		moveToRunList.addActionListener(e -> {
			moveSelectConfigList(canRunConfigList, runConfigList);
		});

		moveToCanRunList.addActionListener(e -> {
			moveSelectConfigList(runConfigList, canRunConfigList);
		});

		debugAllConfig.addActionListener(e -> {
			runConfigList(runConfigList, DefaultDebugExecutor.getDebugExecutorInstance());
		});

		// 把运行配置添加到可运行列表中
		RunManager runManager = RunManager.getInstance(project);
		DefaultListModel model = (DefaultListModel)canRunConfigList.getModel();
		List<RunnerAndConfigurationSettings> allConfigurationSetting = runManager.getAllSettings();
		for (RunnerAndConfigurationSettings setting : allConfigurationSetting) {
			if (ApplicationConfiguration.class.isAssignableFrom(setting.getConfiguration().getClass())) {
				model.addElement(setting.getName());
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
				String name = environment.getRunnerAndConfigurationSettings().getName();
				if (!inConfigList(name, canRunConfigList) && !inConfigList(name, runConfigList)) {
					DefaultListModel dlm = (DefaultListModel) canRunConfigList.getModel();
					dlm.addElement(name);
				}

				// 执行队列中的下一个任务
				Runnable poll = executeTaskQueue.poll();
				if (poll != null) {
					poll.run();
				}
			}
		});
	}

	private boolean inConfigList(String name, JList list) {
		DefaultListModel dlm = (DefaultListModel) canRunConfigList.getModel();
		for (int i = 0; i < dlm.getSize(); ++i) {
			String tmpName = (String)dlm.get(i);
			if (tmpName.equals(name)) {
			    return true;
			}
		}

		return false;
	}

	private void runConfigList(JList list, Executor executor) {
		ListModel listModel = list.getModel();
		for (int i = 0; i < listModel.getSize(); ++i) {
			String name = (String)listModel.getElementAt(i);
			RunnerAndConfigurationSettings setting = getConfigSettingByName(name);

			executeTaskQueue.offer(() -> {
				ProgramRunnerUtil.executeConfiguration(setting, executor);
			});
		}

		Runnable poll = executeTaskQueue.poll();
		if (poll != null) {
			poll.run();
		}
	}

	private void moveSelectConfigList(JList src, JList tar) {
		DefaultListModel srcModel = (DefaultListModel) src.getModel();
		List selectedValuesList = src.getSelectedValuesList();
		for (Object o : selectedValuesList) {
			String name = (String) o;
			srcModel.removeElement(name);

			DefaultListModel tarModel = (DefaultListModel)tar.getModel();
			tarModel.addElement(name);
		}
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

	private RunnerAndConfigurationSettings getConfigSettingByName(String name) {
		RunManager runManager = RunManager.getInstance(project);
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
