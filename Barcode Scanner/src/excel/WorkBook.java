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
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
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
	
	public String checkCellString(CellReference refer, Sheet sheet) {
		cell = cellFinder(refer,sheet);
		return cell.getStringCellValue();
	}
	
	public double checkCellNumeric(CellReference refer, Sheet sheet) {
		cell = cellFinder(refer,sheet);
		return cell.getNumericCellValue();
	}
	
	public boolean checkCellBoolean(CellReference refer, Sheet sheet) {
	cell = cellFinder(refer,sheet);
	return cell.getBooleanCellValue();
	}
	
	public Sheet obtainSheet(String name) {
		return book.getSheet(name);
	}
	
	public void setCell(CellReference refer, Sheet sheet,String value) {
		cell = cellFinder(refer,sheet);
		cell.setCellValue(value);
		
	}
	
	public void setCell(CellReference refer, Sheet sheet, int value) {
		cell = cellFinder(refer,sheet);
		cell.setCellValue(value);
	}

	public void setCell(CellReference refer, Sheet sheet, double value) {
		cell = cellFinder(refer,sheet);
		cell.setCellValue(value);
	}
	
	public void setCell(CellReference refer, Sheet sheet, boolean value) {
		cell = cellFinder(refer,sheet);
		cell.setCellValue(value);
	}
	
	public static Cell cellFinder(CellReference ref, Sheet sheet) {
		cellRefer = ref;
		cellRow = sheet.getRow(cellRefer.getRow());
		return (cellRow.getCell(cellRefer.getCol()));		
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
	
	
	public Cell checkRowCellCreate(CellReference refer, Sheet sheet) {
		cellRefer = refer;
		if((currentRow = sheet.getRow(cellRefer.getRow()))==null) {
			System.out.println("was null at: "+refer.formatAsString());
			currentRow = sheet.createRow(cellRefer.getRow());
			 cell = currentRow.createCell(cellRefer.getCol());
			 
			 return cell;
		}
		
		if((cell = currentRow.getCell(cellRefer.getCol()))==null) {
			cell = currentRow.createCell(cellRefer.getCol());	
			return cell;
		}
		
		cell = currentRow.getCell(cellRefer.getCol());
		return cell;
	}
	
	public Row checkRowCreate(CellReference refer, Sheet sheet) {
		cellRefer = refer;
		if((currentRow = sheet.getRow(cellRefer.getRow()))==null) {
			System.out.println("was null");
			currentRow = sheet.createRow(cellRefer.getRow());
			return currentRow;
		}	
		return currentRow;
	}
	
	public CellReference cellRefFinder(String location) {
		CellReference refer = new CellReference(location);
		return refer;
	}
	
	public void setMerger(CellReference cellOne, CellReference cellTwo, Sheet sheet) {
		sheet.addMergedRegion(findRangeAddress(cellOne,cellTwo,sheet));
	}
	
	public void removeMerger(CellReference cellOne, CellReference cellTwo, Sheet sheet) {
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
	
	public CellRangeAddress findRangeAddress(CellReference referOne, CellReference referTwo, Sheet sheet) {
		 return new CellRangeAddress(referOne.getRow(), referTwo.getRow(),referOne.getCol(), referTwo.getCol());
	}
	
	public Cell findDataInRow(String data, int rowNum, Sheet sheet,int search) {
		Row row = findRow(rowNum,sheet);
		cell = row.getCell(row.getFirstCellNum());
		
		int x = 0;
		while(x<=search) {
			cell = cellFinder(new CellReference(row.getRowNum(),x),sheet);
			if(!(cell==null)) {
			if((checkCellString(new CellReference(cell), sheet)).equals(data)) { 
				return cell;
			}
			
			}
			x++;
		}
			return null;
	}
	
	public Cell findDataInColumn(String data, int columnNum, Sheet sheet, int search) {
		cell = checkRowCellCreate(new CellReference(0,columnNum-1),sheet);
		int x = 0;
		while(x<=search) {
			cell = checkRowCellCreate(new CellReference(x,columnNum-1),sheet);
			if(!(cell==null)) {
				if((checkCellString(new CellReference(cell), sheet)).equals(data)) { 
				return cell;
				}
			}
			x++;
		}
			return null;
	}
	
	public void bufferedSetCell(CellReference refer, Sheet sheet, Boolean value) {
		checkRowCellCreate(refer, sheet);
		setCell(refer, sheet, value);
		
	}
	
	public void bufferedSetCell(CellReference refer, Sheet sheet, int value) {
		checkRowCellCreate(refer, sheet);
		setCell(refer, sheet, value);
		
	}
	
	public void bufferedSetCell(CellReference refer, Sheet sheet, double value) {
		checkRowCellCreate(refer, sheet);
		setCell(refer, sheet, value);
		
	}
	
	public void bufferedSetCell(CellReference refer, Sheet sheet, String value) {
		checkRowCellCreate(refer, sheet);
		setCell(refer, sheet, value);
		
	}
	
	public void bufferedSetCell(CellReference refer, Sheet sheet, char value) {
		checkRowCellCreate(refer, sheet);
		setCell(refer, sheet, value);
		
	}
	
	public Row findRow(int row, Sheet sheet) {
		cellRefer =new CellReference(row-1,0);
		
		return checkRowCreate(cellRefer,sheet);
	}
}