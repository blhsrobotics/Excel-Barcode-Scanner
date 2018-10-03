package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class GUI extends JFrame{
	
	public JPanel panel = new JPanel(new MigLayout());
	public JButton addStud = new JButton("press to no");
	public JPanel studentNamePanel = new JPanel();
	public JLabel studentName = new JLabel("name");
	MigLayout mig = new MigLayout();
	public GUI() {
		setLayout(mig);
		setSize(300,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(panel,"wrap");
		panel.setSize(new Dimension(200,100));
		panel.add(addStud,"width 100:200:300");
		addStud.setSize(new Dimension(50,50));
		addStud.addActionListener(e -> System.out.println("No"));
		studentNamePanel.setSize(new Dimension(100,100));
		add(studentNamePanel);
		studentNamePanel.add(studentName);
		setVisible(true);
	}
	
	
}
