package com.runmulticonfig;

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

	public JPanel getContent() {
		return panel;
	}
}
