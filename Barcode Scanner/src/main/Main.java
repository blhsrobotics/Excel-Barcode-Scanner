package main;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import excel.WorkBook;
import scanning.ScannerLibrary;

public class Main {
	// REQUIRES ALL REFERENCED LIBRARIES. LIBRARIES DO NOT PUSH THROUGH GIT.
	public static void main(String[] args) throws EncryptedDocumentException, IOException, InvalidFormatException {
		String workbook = new String("C:\\Users\\PantherRobotics3218\\Desktop\\StudentIDS.xlsx");
		String test = "sheet2";
		ScannerLibrary library = new ScannerLibrary(workbook);
	}

}
