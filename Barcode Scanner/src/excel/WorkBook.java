package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;

public class WorkBook{
	static CellReference cellRefer;
	static Row cellRow;
	Cell cell;

	XSSFWorkbook book;
	FileInputStream in;
	Scanner scanning = new Scanner(System.in);
	FileOutputStream out;
	File path;
	Sheet[] sheets;
	Row currentRow;
	public WorkBook(File bookName) throws EncryptedDocumentException, IOException, InvalidFormatException {
		path = bookName;
		System.out.println(path);
		in = new FileInputStream(path);
	    
		book = new XSSFWorkbook(in);
		if(book==null) {
			System.out.println("book path was null");
			book = XSSFWorkbookFactory.createWorkbook(in);
			System.out.println("created book");
		}
	    out = new FileOutputStream(path);
		
	}
	
	public String checkCell(CellReference refer, Sheet sheet) {
		cell = cellFinder(refer,sheet);
			return cell.getStringCellValue();
	}
	
	public String checkCell(String location, Sheet sheet) {
		
		cell = cellFinder(cellRefFinder(location,sheet),sheet);
			return cell.getStringCellValue();
	}
	
	public Sheet obtainSheet(String name) {
		return book.getSheet(name);
	}
	
	public void setCell(String location, Sheet sheet,String value) {
		cell = cellFinder(cellRefFinder(location,sheet),sheet);
		cell.setCellValue(value);
	}
	
	public void setCell(String location, Sheet sheet, int value) {
	
		cell = cellFinder(cellRefFinder(location,sheet),sheet);
		cell.setCellValue(value);
	}
	
	public void setCell(String location, Sheet sheet, double value) {
	
		cell = cellFinder(cellRefFinder(location,sheet),sheet);
		cell.setCellValue(value);
	}
	
	public void setCell(String location, Sheet sheet, boolean value) {
		
		cell = cellFinder(cellRefFinder(location,sheet),sheet);
		cell.setCellValue(value);
	}
	
	public static Cell cellFinder(CellReference ref, Sheet sheet) {
		cellRefer = ref;
		cellRow = sheet.getRow(cellRefer.getRow());
		
		return (cellRow.getCell(cellRefer.getCol()));		
	}
	
	public static Cell cellFinder(CellAddress address, Sheet sheet) {
		
		cellRow = sheet.getRow(address.getRow());
		
		return (cellRow.getCell(address.getColumn()));		
	}
	
	public void saveBook() throws IOException {
		book.write(out);
		out.flush();
	}
	
	public void closeBook() throws IOException {
		book.write(out);
		out.flush();
		out.close();
		book.close();
	}
	
	public Sheet[] getSheets() {
		sheets = new Sheet[book.getNumberOfSheets()];
		for(int x = 0; x<book.getNumberOfSheets();x++){
			sheets[x] = book.getSheetAt(x);
		}
		return sheets;
	}
	
	public Sheet getPrimarySheet(String sheetName) {
		for(int x = 0; x<book.getNumberOfSheets();x++) {
			if(book.getSheetName(x).equals(sheetName))
			return book.getSheet(sheetName);
		}
		return null;
	}
	
	public Row checkRowCreate(String location,Sheet sheet) {
	public Row checkRowCellCreate(String location, Sheet sheet) {
		cellRefer = cellRefFinder(location,sheet);
		currentRow = sheet.getRow(cellRefer.getRow());
		if(currentRow ==null) {
		if((currentRow = sheet.getRow(cellRefer.getRow()))==null) {
			System.out.println("was null");
			currentRow = sheet.createRow(cellRefer.getRow());
			currentRow.createCell(cellRefer.getCol());
			return currentRow;
		}
		currentRow.createCell(cellRefer.getCol());	
		return currentRow;
	}
	
	public Row checkRowCellCreate(CellReference refer, Sheet sheet) {
			cellRefer = refer;
		if((currentRow = sheet.getRow(cellRefer.getRow()))==null) {
			System.out.println("was null");
			currentRow = sheet.createRow(cellRefer.getRow());
			currentRow.createCell(cellRefer.getCol());
			return currentRow;
		}
		currentRow.createCell(cellRefer.getCol());	
		return currentRow;
	}
	
	public CellReference cellRefFinder(String location, Sheet sheet) {
		return new CellReference(location);
	}
	
	public void setMerger(String cellOne, String cellTwo, Sheet sheet) {
		
		
		sheet.addMergedRegion(findRangeAddress(cellOne,cellTwo,sheet));
		
	}
	
	public void removeMerger(String cellOne, String cellTwo, Sheet sheet) {
	for( int x = 0; x<sheet.getNumMergedRegions();x++) {
		if((sheet.getMergedRegion(x).equals(findRangeAddress(cellOne,cellTwo,sheet)))){
			sheet.removeMergedRegion(x);
			System.out.println("Removed Region");
			return;
			}
		}
		System.out.println("Failed to find region to remove.");
		return;
	}
	
	public CellRangeAddress findRangeAddress(String cellOne, String cellTwo, Sheet sheet) {
		 return new CellRangeAddress(cellRefFinder(cellOne,sheet).getRow(), cellRefFinder(cellTwo,sheet).getRow(),cellRefFinder(cellOne,sheet).getCol(), cellRefFinder(cellTwo,sheet).getCol());
	}
	
	public Cell findDataInRow(String data, Row row, Sheet sheet) {
		cell = row.getCell(row.getFirstCellNum());
		while(!(cell==null)) {
			cell = cellFinder(new CellAddress(cell.getRowIndex(),cell.getColumnIndex()+1),sheet);
				if((checkCell(new CellReference(cell), sheet))==data) 
					return cell;
			}
		return null;
		
	}
	
	public void bufferedSetCell(String location, Sheet sheet, Boolean value) {
		checkRowCellCreate(location, sheet);
		setCell(location, sheet, value);
		System.out.println("Set "+location+" to "+value);
	}
	public void bufferedSetCell(String location, Sheet sheet, int value) {
		checkRowCellCreate(location, sheet);
		setCell(location, sheet, value);
		System.out.println("Set "+location+" to "+value);
	}
	public void bufferedSetCell(String location, Sheet sheet, double value) {
		checkRowCellCreate(location, sheet);
		setCell(location, sheet, value);
		System.out.println("Set "+location+" to "+value);
	}
	public void bufferedSetCell(String location, Sheet sheet, String value) {
		
		checkRowCellCreate(location, sheet);
		setCell(location, sheet, value);
		System.out.println("Set "+location+" to "+value);
	}
	public void bufferedSetCell(String location, Sheet sheet, char value) {
		checkRowCellCreate(location, sheet);
		setCell(location, sheet, value);
		System.out.println("Set "+location+" to "+value);
	}
<<<<<<< HEAD

	public Row findRow(Cell cell) {
		return cell.getRow();
		
	}
=======
>>>>>>> 0cb233451dbc236cde0313f3ff3f8a9eaea076f3
	
}