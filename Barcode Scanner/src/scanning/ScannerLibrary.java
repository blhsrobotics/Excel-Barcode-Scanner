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
	static Map<Integer, Integer> studentNumberMap = new HashMap<>();
	File file;
	String line = "not yet";
	String sheetName = "Student IDs";
	Scanner system = new Scanner(System.in);
	static WorkBook book;
	Sheet[] sheets;
	Sheet primary;
	public ScannerLibrary(File path) throws IOException, EncryptedDocumentException, InvalidFormatException {
	
		file = path;
		book = new WorkBook(file);
		System.out.println("made book");
		int yes = book.returnCellValue(int.class);
		System.out.println(yes);
		System.out.println("printed out yes");
		sheets = book.getSheets();
		primary = book.getPrimarySheet(sheetName);
		book.bufferedSetCell("D2", primary, "yes");
		System.out.println(book.checkCell(new CellReference("D2"), primary));
		book.closeBook();
	}
	
	public void findStudent(int id, Sheet sheet) {
		
	}
	
	public static void createList(int columnOne,int columnTwo, Sheet sheet) {
		for(int x= 0; x<studentNumberMap.size();x++) {
		//	studentNumberMap.put(book.checkCell(new CellReference(x,columnOne), sheet),book.checkCell(new CellReference(x,columnTwo), sheet));
		}
	
	}
}
