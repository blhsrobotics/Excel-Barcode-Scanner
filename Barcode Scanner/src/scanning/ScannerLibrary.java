package scanning;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
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

/*
 * Copyright © 2010 by Chase E. Arline
All rights reserved. This program or any portion thereof
may not be reproduced or used in any manner whatsoever
without the express written permission of the publisher.
 */


public class ScannerLibrary {
	int amountOfStudents;
	static Map<Double,String> studentNumberMap = new HashMap<>();
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
	
	public String findStudent(double id) {
		createList(studentID.getColumnIndex(),studentNames.getColumnIndex(),primary);
		return studentNumberMap.get(id);
		
	}
	
	public static void createList(int columnOne,int columnTwo, Sheet sheet) {
		studentNumberMap.clear();
		try {
			int x= stringRow+2;
			while(true) {
				studentNumberMap.put(book.checkCellNumeric(new CellReference(x,columnOne), sheet), book.checkCellString(new CellReference(x,columnTwo), sheet));
				System.out.println("added: "+book.checkCellNumeric(new CellReference(x,columnOne), primary));
				System.out.println("with :"+book.checkCellString(new CellReference(x,columnTwo), primary));
				x++;
			}
		}
		catch(NullPointerException e) {
		System.out.println("Error: "
				+ "Likely ran out of numbers in the StudentID/StudentName columns");
		
		}
	}
	
	public static void addStudent(double barcode, String name) {
		
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
			createList(studentID.getColumnIndex(),studentNames.getColumnIndex(), primary);
		}
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
			book.setMerger(new CellReference(stringRow, x), new CellReference(stringRow, x+2), primary);
			book.bufferedSetCell(new CellReference(stringRow+1, x), primary, "Sign in");
			book.bufferedSetCell(new CellReference(stringRow+1, x+2), primary, "Sign out");
			
			book.bufferedSetCell(new CellReference(stringRow+1, x+1),primary,"Hours On Day");
			book.bufferedSetCell(new CellReference(stringRow, x), primary, currentDay() );
			book.bufferedSetCell(new CellReference(stringRow, x+1), primary, "not null");
			book.bufferedSetCell(new CellReference(stringRow, x+2), primary, "not null");
			primary.autoSizeColumn(x);
			primary.autoSizeColumn(x+1);
			primary.autoSizeColumn(x+2);
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
	
	public void closeBook() throws IOException {
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
	
	public boolean hasSigned(double barcode) {
		int rowCell = book.findDataInColumn(findStudent(barcode), studentNames.getColumnIndex(), primary, 50).getRowIndex();
		int colCell = book.findDataInRow(currentDay(), stringRow, primary, 50).getColumnIndex();
		Cell cell = book.cellFinder(new CellReference(rowCell,colCell), primary);
		if(cell==null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void signInOut(double barcode) {
		
		System.out.println(barcode);
		System.out.println(findStudent(barcode));
		int rowCell = book.findDataInColumn(findStudent(barcode), studentNames.getColumnIndex(), primary, 50).getRowIndex();
		int colCell = book.findDataInRow(currentDay(), stringRow, primary, 50).getColumnIndex();
		System.out.println(new CellReference(rowCell,colCell).toString());
		if(hasSigned(barcode)) {
			book.bufferedSetCell(new CellReference(rowCell,studentHours.getColumnIndex()), primary, 
				 (book.checkCellNumeric(new CellReference(rowCell,studentHours.getColumnIndex()),primary)
				  -book.checkCellNumeric(new CellReference(rowCell,colCell+1), primary)));
			
			book.bufferedSetCell(new CellReference(rowCell,colCell+2), primary,currentTime());
			primary.autoSizeColumn(colCell+2);
			System.out.println(timeChange(rowCell,colCell));
			book.bufferedSetCell(new CellReference(rowCell,studentHours.getColumnIndex()), primary,
					(book.checkCellNumeric(new CellReference(rowCell,studentHours.getColumnIndex()), primary)+timeChange(rowCell,colCell)));
			
			book.bufferedSetCell(new CellReference(rowCell,colCell+1),primary, timeChange(rowCell,colCell));
		
		}
		else {
			book.bufferedSetCell(new CellReference(rowCell,colCell), primary, currentTime());
			primary.autoSizeColumn(colCell+2);
			book.bufferedSetCell(new CellReference(rowCell,colCell+1), primary, 0);
		}
	}
	public static String currentDay() {
		return ZonedDateTime.now().toString().substring(0, 10);
	}
	
	public static String currentTime() {
		return ZonedDateTime.now().toString().substring(11, 19);
	}

	public static double timeChange(int rowCell, int colCell) {
		String timeOne = book.checkCellString(new CellReference(rowCell, colCell),primary);
		String timeTwo = book.checkCellString(new CellReference(rowCell,colCell+2), primary);
		
		int hourTimeOne = Integer.parseInt(timeOne.substring(0, 2));
		int hourTimeTwo = Integer.parseInt(timeTwo.substring(0, 2));
		double minuteTimeOne = Double.parseDouble(timeOne.substring(3,5));
		double minuteTimeTwo = Double.parseDouble(timeTwo.substring(3,5));
		System.out.println("hour1: "+ hourTimeOne);
		System.out.println("hour2 "+ hourTimeTwo);
		System.out.println("minute1 "+ minuteTimeOne);
		System.out.println("minute2 "+ minuteTimeTwo);
		double timeChange = (hourTimeTwo-hourTimeOne)+(minuteTimeTwo-minuteTimeOne)/60;
			return timeChange;
	}

	public static boolean hasCurrentDay() {
		try{
			if(book.findDataInRow(currentDay(),stringRow, primary, 100)==null)
				return false;
		}
		catch(NullPointerException e) {
			return false;
		}
		return true;
	}
}
