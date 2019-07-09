package com.runmulticonfig;

import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

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

	public RunMultiConfigToolWindow() {
		changeRunConfigState.addActionListener(e -> {

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
			canRunConfigList.add(new Label(runnerAndConfigurationSettings.getName()));
		}
	}

	public JPanel getContent() {
		return panel;
	}
}
