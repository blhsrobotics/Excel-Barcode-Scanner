package scanning;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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

import excel.WorkBook;

public class ScannerLibrary {
	int amountOfStudents;
	static Map<Double,String> studentNumberMap = new HashMap<>();
	File file;
	String line = "not yet";
	String sheetName = "Student IDs";
	Scanner system = new Scanner(System.in);
	static WorkBook book;
	Sheet[] sheets;
	Sheet primary;
	final String[] markers = new String[] {
			"ID'S","NAMES",};
	static Cell studentID;
	static Cell studentName;
	public ScannerLibrary(File path) throws IOException, EncryptedDocumentException, InvalidFormatException {
	
		file = path;
		book = new WorkBook(file);
		System.out.println("made book");
		
		sheets = book.getSheets();
		primary = book.getPrimarySheet(sheetName);
	    studentID = book.findDataInRow("Student IDs", 1, primary,50);
	    studentName = book.findDataInRow("Student Names", 1, primary,50);
	    createList(studentID.getColumnIndex(),studentName.getColumnIndex(), primary);
	    System.out.println("StudentID Column is: " +studentID.getAddress());
	    System.out.println("StudentName Column is: " +studentName.getAddress());
	    addStudent(988837,"chase",primary);
	    addStudent(983759,"Sidnee",primary);
	    
	    createList(studentID.getColumnIndex(),studentName.getColumnIndex(), primary);
	    
	    book.closeBook();
	}
	
	public void findStudent(int id, Sheet sheet) {
		
	}
	
	public static void createList(int columnOne,int columnTwo, Sheet sheet) {
		studentNumberMap.clear();
		try {
			int x= 1;
		
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

	public static void addStudent(double barcode, String name, Sheet sheet) {
		int x = 1;
		try{
			while(true) {
		
			book.checkCellNumeric(new CellReference(x,studentID.getColumnIndex()), sheet);
			x++;
			}
		}
		catch(NullPointerException e) {
			book.bufferedSetCell(new CellReference(x,studentID.getColumnIndex()), sheet, barcode);
			book.bufferedSetCell(new CellReference(x,studentName.getColumnIndex()), sheet, name);
			createList(studentID.getColumnIndex(),studentName.getColumnIndex(), sheet);
		}
	}
}