package excel;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;

public class WorkBook{
	
	public WorkBook(String workBookName, String sheetName) throws EncryptedDocumentException, IOException {
		Scanner scanning = new Scanner(System.in);
		File workBooking = new File(workBookName);
		Workbook workbook = WorkbookFactory.create(workBooking);
		Sheet test = workbook.getSheet("sheet1");
		System.out.println("testing");
		CellReference cellRef = new CellReference("A3");
		Row row = test.getRow(cellRef.getRow());
		Cell cell = row.getCell(cellRef.getCol());
		
		CellAddress celling = new CellAddress("A3");
		cell.setCellValue(scanning.nextLine());
		System.out.println("number is :" +cell.getStringCellValue());
		
	}
	
}