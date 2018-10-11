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
	static Cell studentName;
	static Cell studentHours;
	static int StringRow = 1;
	public ScannerLibrary(File path) throws IOException, EncryptedDocumentException, InvalidFormatException {
		
		
		hasSignedIn();
		file = path;
		book = new WorkBook(file);
		
		System.out.println("made book");
		primary = book.getPrimarySheet(sheetName);
		
		primary = checkPrimary(primary);
		System.out.println(primary.toString());
		
		studentID = book.findDataInRow("Student IDs", 1, primary,10);
	    studentName = book.findDataInRow("Student Names", 1, primary,10);
		studentHours = book.findDataInRow("Student Hours ", 1, primary, 10);
		
	    if(studentID==null) {
	    	System.out.println("Couldn't find marker cells, creating them");
	    	createMarkerCells();
	    	studentID = book.findDataInRow("Student IDs", 1, primary,10);
		    studentName = book.findDataInRow("Student Names", 1, primary,10);
			studentHours = book.findDataInRow("Student Hours ", 1, primary, 10);
			
			System.out.println("check is: "+book.checkCellString(book.findRef(1, 1), primary));
			System.out.println("check two is: " +book.checkCellString(book.findRef(1, 2), primary));
			System.out.println(studentID);
	    }
	    
		System.out.println("StudentID Column is: " +studentID.getAddress());
	    System.out.println("StudentName Column is: " +studentName.getAddress());
	    setMergers();
	    createList(studentID.getColumnIndex(),studentName.getColumnIndex(), primary);
	    closeBook();
	
	}
	
	public String findStudent(int id) {
		return studentNumberMap.get(id);
		
	}
	
	public static void createList(int columnOne,int columnTwo, Sheet sheet) {
		studentNumberMap.clear();
		try {
			int x= 2;
		
			while(true) {
				System.out.println(book.checkCellNumeric(new CellReference(x,columnOne), sheet) +", " + book.checkCellString(new CellReference(x,columnTwo), sheet));
				studentNumberMap.put(book.checkCellNumeric(new CellReference(x,columnOne), sheet), book.checkCellString(new CellReference(x,columnTwo), sheet));
				x++;
			}
		}
		catch(NullPointerException e) {
		System.out.println("Error: "
				+ "Likely ran out of numbers in the StudentID/StudentName columns");
		
		}
	}

	public static void addStudent(double barcode, String name) {
		
		int x = 2;
		try{
			while(true) {
		
			book.checkCellNumeric(new CellReference(x,studentID.getColumnIndex()), primary);
			x++;
			}
		}
		catch(NullPointerException e) {
			book.bufferedSetCell(new CellReference(x,studentID.getColumnIndex()), primary, barcode);
			book.bufferedSetCell(new CellReference(x,studentName.getColumnIndex()), primary, name);
			createList(studentID.getColumnIndex(),studentName.getColumnIndex(), primary);
		}
	}

	public static void addDay(String day) {
		
		int x = 1;
		try {
			while(true) {
				book.checkCellString(book.findRef(StringRow, x), primary);
				System.out.println(book.checkCellString(book.findRef(StringRow, x), primary));
				x++;
			}
		}
		catch(NullPointerException e) {
			book.setMerger(book.findRef(StringRow, x), book.findRef(StringRow, x+2), primary);
			book.bufferedSetCell(book.findRef(StringRow, x), primary, day);
			book.bufferedSetCell(book.findRef(StringRow, x+1), primary, "not null");
			book.bufferedSetCell(book.findRef(StringRow, x+2), primary, "not null");
		}
	}
	
	public static void setMergers() {
		try{
		book.setMerger(book.findRef(1,1),book.findRef(2,1), primary);
		
		book.setMerger(book.findRef(1,2),book.findRef(2,2), primary);
	    
		book.setMerger(book.findRef(1, 3), book.findRef(2, 3), primary);
		
		book.bufferedSetCell(book.findRef(2, 1), primary, "not null");
	    book.bufferedSetCell(book.findRef(2,2), primary, "not null");
		book.bufferedSetCell(book.findRef(2, 3), primary, "not null");
		}
		catch(IllegalStateException e) {
			System.out.println("Merger was already set for stringRows");
		}
	}

	public void closeBook() throws IOException {
		book.closeBook();
	}

	public void hasSignedIn() {
		System.out.println(ZonedDateTime.now().toString().substring(11,19));
		System.out.println(ZonedDateTime.now().toString().substring(0, 10));
	}
	
	public static void createMarkerCells() {
		book.bufferedSetCell(book.findRef(1,1), primary, "Student IDs");
		book.bufferedSetCell(book.findRef(1, 2), primary, "Student Names");
		book.bufferedSetCell(book.findRef(1, 3), primary, "Student Hours");
	}
	
	public static Sheet checkPrimary(Sheet prim) {
		if(prim==null) {
			System.out.println("Primary was null");
			
			return book.createSheet(new String("Student IDs"));
			}
		
		return prim;
	}
	
}