package GUIs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import scanning.ScannerLibrary;

public class DayGUI extends JFrame{
	public JTextArea writeIn = new JTextArea(" MM/DD/YYYY");
	public JButton next = new JButton("Confirm");
	Scanner scan = new Scanner(System.in);
	public DayGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ScannerLibrary.addDay(writeIn.getText());
                dispose();
            }});
		
	add(writeIn,BorderLayout.CENTER);
	add(next,BorderLayout.SOUTH);
	setVisible(true);
	setSize(100,100);
	setVisible(true);
	
	}
	
}
