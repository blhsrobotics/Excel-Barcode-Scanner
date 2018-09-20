package main;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;

import excel.WorkBook;

public class Main {

	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		String workbook = new String("C:\\Users\\Panther Robotics\\workspace\\Test#1\\A&qw.xls");
		String test = "sheet2";
		WorkBook work = new WorkBook(workbook,test);
	}

}
