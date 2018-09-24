package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import excel.WorkBook;
import scanning.ScannerLibrary;

public class Main {

	// REQUIRES ALL REFERENCED LIBRARIES. LIBRARIES DO NOT PUSH THROUGH GIT.
	public static void main(String[] args) throws EncryptedDocumentException, IOException, InvalidFormatException {
	/*	JFileChooser chooser = new JFileChooser(Desktop.getDesktop().toString());
		 FileNameExtensionFilter filter = new FileNameExtensionFilter(
			       null, "xlsx");
		 chooser.setFileFilter(filter);
		 int returnVal = chooser.showOpenDialog(null);
		 if (returnVal == JFileChooser.APPROVE_OPTION) {
				File selectedFile = chooser.getSelectedFile();
				System.out.println(selectedFile.getAbsolutePath());
			}	
		*/
		System.out.println("User directory is: "+System. getProperty("user.dir"));
		String workbook = new String("C:\\Users\\arlincha000\\Desktop\\StudentIDS.xlsx");
		String test = "sheet2";
		ScannerLibrary library = new ScannerLibrary(workbook);
		
	}
}
