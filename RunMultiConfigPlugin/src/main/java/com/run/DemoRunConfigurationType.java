package com.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.icons.AllIcons;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author jiangzhen
 */
public class DemoRunConfigurationType implements ConfigurationType
{
	@Override
	public String getDisplayName() {
		return "Demo";
	}

	@Override
	public String getConfigurationTypeDescription() {
		return "Demo Run Configuration Type";
	}

	@Override
	public Icon getIcon() {
		return AllIcons.General.Information;
	}

	@NotNull
	@Override
	public String getId() {
		return "DEMO_RUN_CONFIGURATION";
	}

	@Override
	public ConfigurationFactory[] getConfigurationFactories() {
		return new ConfigurationFactory[]{new DemoConfigurationFactory(this)};
	}
}
