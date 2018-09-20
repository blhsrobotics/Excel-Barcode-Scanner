package scanning;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;

import excel.WorkBook;

public class ScannerLibrary {
	int amountOfStudents;
	Map<Integer, Integer> studentNumberMap = new HashMap<>();
	File file;
	String line = "not yet";
	String sheetName;
	Scanner system = new Scanner(System.in);
	String start = "A2";
	
	String locationLetter=start.substring(0, 1);
	public ScannerLibrary(int amountOfStudents, String fileLocation, String sheetName) throws EncryptedDocumentException, IOException {
		System.out.println("Sheet name?");
		sheetName = system.next();
		this.amountOfStudents = amountOfStudents;
		WorkBook book = new WorkBook(fileLocation);
		Sheet studentSheet = book.obtainSheet(sheetName);
	    
		while(!line.equals(null)) {
			line = book.checkCell(location, studentSheet);
			
		}

	
	
	}
	
}
