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
import org.apache.poi.ss.usermodel.Row;
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
	static WorkBook book;
	Sheet[] sheets;
	Sheet primary;
	public ScannerLibrary(File path) throws IOException {
		 file = path;
	try {
		book = new WorkBook(file);
		sheets = book.getSheets();
		primary = book.getPrimarySheet(sheetName);
		book.bufferedSetCell("D2", primary, "test2");
		//book.setMerger("B2", "D2", primary);
		//System.out.println(book.checkCell("A2", primary));
		System.out.println(book.checkCell("D3", primary));
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
<<<<<<< HEAD
	
	public void findStudent(int id, Sheet sheet) {
=======
	public void findStudent(int id, int column, int start) {
>>>>>>> 0cb233451dbc236cde0313f3ff3f8a9eaea076f3
		
	}
	
}
