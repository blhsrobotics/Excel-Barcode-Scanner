package scanning;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.Applicat;
import excel.WorkBook;
import javafx.application.Application;
import xmlfiler.CurrentTime;
import xmlfiler.Day;
import xmlfiler.Identifiers;
import xmlfiler.Student;

/*
 * Copyright © 2010 by Chase E. Arline
All rights reserved. This program or any portion thereof
may not be reproduced or used in any manner whatsoever
without the express written permission of the publisher.
 */


public class ScannerLibrary {
	int amountOfStudents;
	static Map<Double,String> studentNumberMap = new HashMap<>();
	static Map<Integer,Double> rowBarcodeMap = new HashMap<>();
	File file;
	String line = "not yet";
	String sheetName = "Student IDs";
	Scanner system = new Scanner(System.in);
	static WorkBook book;
	Sheet[] sheets;
	static Sheet primary;
	final String[] markers = new String[] {
			"ID'S","NAMES",};
	static Cell studentID;
	static Cell studentNames;
	static Cell studentHours;
	static int stringRow = 0;
	static String studentHoursS = "Student Hours";
	static DecimalFormat decFormat = new DecimalFormat("#.##");
	static Day day;
	public ScannerLibrary(File path) throws IOException, EncryptedDocumentException, InvalidFormatException {
		//hasSignedIn();
		file = path;
		book = new WorkBook(file);
		
		System.out.println("made book");
		primary = book.getPrimarySheet(sheetName);
		
		primary = checkPrimary(primary);
		System.out.println(primary.toString());
		
		studentID = book.findDataInRow("Student IDs", stringRow, primary,10);
	    studentNames = book.findDataInRow("Student Names", stringRow, primary,10);
		studentHours = book.findDataInRow(studentHoursS, stringRow, primary, 10);
		
	    if(studentID==null) {
	    	System.out.println("Couldn't find marker cells, creating them");
	    	createMarkerCells();
	    	studentID = book.findDataInRow("Student IDs", stringRow, primary,10);
		    studentNames = book.findDataInRow("Student Names", stringRow, primary,10);
			studentHours = book.findDataInRow(studentHoursS, stringRow, primary, 20);
			System.out.println(studentHours);
			System.out.println("check is: "+book.checkCellString(new CellReference(stringRow,0), primary));
			System.out.println("check two is: " +book.checkCellString(new CellReference(stringRow, 1), primary));
			System.out.println("check three is" +book.checkCellString(new CellReference(stringRow, 2), primary));
			System.out.println("StudentID col index: "+studentID.getColumnIndex());
			primary.autoSizeColumn(studentID.getColumnIndex());
			primary.autoSizeColumn(studentNames.getColumnIndex());
			primary.autoSizeColumn(studentHours.getColumnIndex());
	    }
	    System.out.println(studentHours.getAddress());
		System.out.println("StudentID Column is: " +studentID.getAddress());
	    System.out.println("StudentName Column is: " +studentNames.getAddress());
	    setMergers();
	}
	
	public static void addStudent(double barcode, String name, ArrayList<Identifiers> students) {
		
		int x = stringRow+2;
		try{
			while(true) {
		
			book.checkCellNumeric(new CellReference(x,studentID.getColumnIndex()), primary);
			x++;
			}
		}
		catch(NullPointerException e) {
			book.bufferedSetCell(new CellReference(x,studentID.getColumnIndex()), primary, barcode);
			book.bufferedSetCell(new CellReference(x,studentNames.getColumnIndex()), primary, name);
			book.bufferedSetCell(new CellReference(x,studentHours.getColumnIndex()), primary, 0);
		}
		
		students.add(new Identifiers(barcode,name));
		System.out.println("Added student to library in xml");
	}
	public static void addCurrentDay() {
		
		int x = 0;
		try {
			while(true) {
				book.checkCellString(new CellReference(stringRow, x), primary);
				System.out.println(book.checkCellString(new CellReference(stringRow, x), primary));
				x++;
			}
		}
		catch(NullPointerException e) {
			
			book.bufferedSetCell(new CellReference(stringRow+1, x),primary,"Hours On Day");
			book.bufferedSetCell(new CellReference(stringRow, x), primary, CurrentTime.getDay());
			
			primary.autoSizeColumn(x);
			
		}
	}
	
	public static void setMergers() {
		try{
		book.setMerger(new CellReference(stringRow,studentID.getColumnIndex()),new CellReference(stringRow+1,studentID.getColumnIndex()), primary);
		
		book.setMerger(new CellReference(stringRow,studentNames.getColumnIndex()),new CellReference(stringRow+1,studentNames.getColumnIndex()), primary);
	    
		book.setMerger(new CellReference(stringRow, studentHours.getColumnIndex()), new CellReference(stringRow+1, studentHours.getColumnIndex()), primary);
		
		book.bufferedSetCell(new CellReference(stringRow+1, studentID.getColumnIndex()), primary, "not null");
	    book.bufferedSetCell(new CellReference(stringRow+1,studentNames.getColumnIndex()), primary, "not null");
		book.bufferedSetCell(new CellReference(stringRow+1, studentHours.getColumnIndex()), primary, "not null");
		}
		catch(IllegalStateException e) {
			System.out.println("Merger was already set for stringRows");
		}
	}
	
	public void closeBook(Day today) throws IOException {
		populateTimes(today);
		book.closeBook();
	}
	
	public static void createMarkerCells() {
		
		book.bufferedSetCell(new CellReference(stringRow,0), primary, "Student IDs");
		book.bufferedSetCell(new CellReference(stringRow, 1), primary, "Student Names");
		book.bufferedSetCell(new CellReference(stringRow, 2), primary, studentHoursS);
	}
	
	public static Sheet checkPrimary(Sheet prim) {
		if(prim==null) {
			System.out.println("Primary was null");
			
			return book.createSheet(new String("Student IDs"));
			}
		
		return prim;
	}
	
	public static boolean hasCurrentDay() {
		try{
			if(book.findDataInRow(CurrentTime.getDay(),stringRow, primary, 100)==null)
				return false;
		}
		catch(NullPointerException e) {
			return false;
		}
		return true;
	}

	public void onlyKeepPrimarySheet() {
		book.keepSingleSheet(primary);
	}

	public void signInOut(double id, Day today) {
		if(today.findStudent(id).isSignedIn()) 
			today.findStudent(id).signOut();
		else 
			today.findStudent(id).signIn();
		
	}
	public static void populateTimes(Day today) {
		int dateColumn = book.findDataInRow(CurrentTime.getDay(),stringRow, primary, 100).getColumnIndex();
		int hoursColumn = studentHours.getColumnIndex();
		int studentRow;
		CellReference totalHoursRef;
		CellReference dayHoursRef;
		for(Student student:today.getStudents()) {
			studentRow = book.findDataInColumn(student.getName(), studentNames.getColumnIndex(), primary, 50).getRowIndex();
			totalHoursRef = new CellReference(studentRow,hoursColumn);
			dayHoursRef = new CellReference(studentRow,dateColumn);
			try {
			book.bufferedSetCell(totalHoursRef, primary,book.checkCellNumeric(totalHoursRef, primary)-book.checkCellNumeric(dayHoursRef, primary));
			book.bufferedSetCell(dayHoursRef,primary,Double.parseDouble(decFormat.format(student.getTotalTimeToday())));
			book.bufferedSetCell(totalHoursRef, primary, book.checkCellNumeric(totalHoursRef, primary)+book.checkCellNumeric(dayHoursRef,primary));
			}
			catch(NullPointerException e) {
				book.bufferedSetCell(dayHoursRef,primary,Double.parseDouble(decFormat.format(student.getTotalTimeToday())));
				book.bufferedSetCell(totalHoursRef, primary, book.checkCellNumeric(totalHoursRef, primary)+book.checkCellNumeric(dayHoursRef,primary));
			}
			
			}
	}
}