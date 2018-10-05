package GUIs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import scanning.ScannerLibrary;

public class StudentGUI extends JFrame{
	public JTextField writeIn = new JTextField();
	public JButton next = new JButton("Confirm");
	public JLabel num = new JLabel();
	Scanner scan = new Scanner(System.in);
	public StudentGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ScannerLibrary.addStudent(scan.nextDouble(),writeIn.getText());
                dispose();
            }});
	setSize(100,00);
	add(writeIn,BorderLayout.CENTER);
	add(next,BorderLayout.SOUTH);
	add(num, BorderLayout.NORTH);
	setVisible(true);
	
	num.setText(Double.toString(scan.nextDouble()));
	setVisible(true);
	}
	
}
