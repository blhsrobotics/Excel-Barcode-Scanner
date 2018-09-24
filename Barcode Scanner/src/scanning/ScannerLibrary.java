package scanning;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import excel.WorkBook;

public class ScannerLibrary {
	int amountOfStudents;
	Map<Integer, Integer> studentNumberMap = new HashMap<>();
	File file;
	String line = "not yet";
	String sheetName = "Student IDs";
	Scanner system = new Scanner(System.in);
	String start = "A2";
	String locationLetter=start.substring(0, 1);
	static WorkBook book;
	Sheet[] sheets;
	Sheet primary;
	public ScannerLibrary(String fileLocation) throws IOException {
		
			file = new File(fileLocation);
		
	try {
		book = new WorkBook(file);
		sheets = book.getSheets();
		primary = book.getPrimarySheet(sheetName);
		bufferedSetCell("C3", primary, "red");
		book.removeMerger("B2", "D2", primary);
		System.out.println(book.checkCell("A2", primary));
		book.closeBook();
	} catch (EncryptedDocumentException | InvalidFormatException | IOException | NullPointerException e) {
		book.closeBook();
		e.printStackTrace();
		}
		// Rename sheet name on master workbook to sheetName-
		// for some reason setCell for a string did not work. 
		// possibly the row/cell has to be created. not sure.
		// need to make rows, then use rows.createCell to fix it
	}
	
	public void findStudent(int id, char column, int start) {
		
	}
	public static void bufferedSetCell(String location, Sheet sheet, Boolean value) {
		book.checkRowCreate(location, sheet);
		book.setCell(location, sheet, value);
	}
	public static void bufferedSetCell(String location, Sheet sheet, int value) {
		book.checkRowCreate(location, sheet);
		book.setCell(location, sheet, value);
	}
	public static void bufferedSetCell(String location, Sheet sheet, double value) {
		book.checkRowCreate(location, sheet);
		book.setCell(location, sheet, value);
	}
	public static void bufferedSetCell(String location, Sheet sheet, String value) {
		book.checkRowCreate(location, sheet);
		book.setCell(location, sheet, value);
	}
	public static void bufferedSetCell(String location, Sheet sheet, char value) {
		book.checkRowCreate(location, sheet);
		book.setCell(location, sheet, value);
	}
}
