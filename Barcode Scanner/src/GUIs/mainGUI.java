package GUIs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import scanning.ScannerLibrary;

public class mainGUI extends JFrame {
	JButton addDay = new JButton("Add day");
	JButton addStudent = new JButton("Add Student");
	JLabel stud = new JLabel();
	String currentDay;
	Scanner sys = new Scanner(System.in);
	public mainGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              new StudentGUI();
            }});
		
		addDay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              new DayGUI();
            }});
		setSize(200,200);
		add(stud,BorderLayout.NORTH);
		add(addStudent,BorderLayout.CENTER);
		add(addDay,BorderLayout.SOUTH);
		setVisible(true);
		while(true) {
			stud.setText(Double.toString(sys.nextDouble()));
			setVisible(true);
		}

	}
	public void windowClosing(WindowEvent e) {
		
	}
	
	
}
