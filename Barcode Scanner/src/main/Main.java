package main;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;

import excel.WorkBook;

public class Main {

	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		String workbook = new String("H:\\Eclipse\\Book1.xlsx");
		String test = "sheet2";
		WorkBook work = new WorkBook(workbook);
	}

}
