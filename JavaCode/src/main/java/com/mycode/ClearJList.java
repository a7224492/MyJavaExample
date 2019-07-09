package com.mycode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.*;

/**
 * @author jiangzhen
 */
public class ClearJList extends JPanel
	implements ActionListener
{
	JButton jb1, jb2;
	JList list;
	int i = 1;

	public ClearJList(){
		Vector data;
		setLayout(new BorderLayout());
		list = new JList();
		list.setModel(new DefaultListModel());
		add(new JScrollPane(list),"Center");
		add(jb1 = new JButton("Add"), "East");
		add(jb2 = new JButton("Clear"), "West");
		jb1.addActionListener(this);
		jb2.addActionListener(this);
	}

	public Dimension getPreferredSize(){
		return new Dimension(50, 50);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jb1) {
			// add
			DefaultListModel dlm =
				(DefaultListModel) list.getModel();
			dlm.addElement
				((Object) Integer.toString(i++));
		}
		else {
			// clear
			list.setModel(new DefaultListModel());
		}
	}
	public static void main(String s[]) {
		JFrame frame = new JFrame("Clear JList");
		ClearJList panel = new ClearJList();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().add(panel,"Center");

		frame.setSize(200,200);
		frame.setVisible(true);
	}
}
