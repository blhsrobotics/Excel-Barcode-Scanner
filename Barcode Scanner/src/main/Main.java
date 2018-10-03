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
		File path = null;
		JFileChooser chooser = new JFileChooser(Desktop.getDesktop().toString());
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			       null, "xlsx");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
				path = chooser.getSelectedFile();
				System.out.println(path.getAbsolutePath());
			}	
		
		
		
		ScannerLibrary library = new ScannerLibrary(path);
		
	}
	
	public static void running() {
		
		
	}


}
