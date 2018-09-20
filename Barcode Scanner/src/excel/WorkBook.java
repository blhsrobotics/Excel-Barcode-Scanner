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
	CellReference cellRefer;
	Row cellRow;
	Cell cell;
	Workbook book;
	public WorkBook(String bookName) throws EncryptedDocumentException, IOException {
		Scanner scanning = new Scanner(System.in);
		File booking = new File(bookName);
	    book = WorkbookFactory.create(booking);
		Sheet test = book.getSheet("sheet1");
		System.out.println("testing");
		CellReference cellRef = new CellReference("A3");
		Row row = test.getRow(cellRef.getRow());
		Cell cell = row.getCell(cellRef.getCol());
		
		CellAddress celling = new CellAddress("A3");
		cell.setCellValue(scanning.nextLine());
		System.out.println("number is :" +cell.getStringCellValue());
		
	}
	
	public String checkCell(String location, Sheet sheet) {
		cellRefer = new CellReference(location);
		cellRow = sheet.getRow(cellRefer.getRow());
		cell = cellRow.getCell(cellRefer.getCol());
			return cell.getStringCellValue();
	
	}
	
	public Sheet obtainSheet(String name) {
		return book.getSheet(name);
	}
}