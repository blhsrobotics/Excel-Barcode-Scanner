package xmlfiler;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.SAXParseException;

public class XmlDay {
	private static Day today = new Day();
	private static StudentLibrary lib = new StudentLibrary();
	private static XMLFiler librarian;
	private static XMLFiler filer;
	public static void main(String[] args) throws JAXBException, IOException {
		xmlDayCreator();
	}

	
	
	public static Day xmlDayCreator() throws JAXBException, IOException {
		today = new Day();
	    lib = new StudentLibrary();
		File[] paths = fileChooser();
		File location = paths[1];
		File directory = paths[0];
		filer = new XMLFiler(location,today);
		File studLib = paths[2];
		librarian = new XMLFiler(studLib,lib);
		if(!directory.exists()) {
			System.out.println("Directory did not exist, creating at :" +directory.getAbsolutePath());
			System.out.println("Creating directory succeeded: "+directory.mkdirs());
		}
		if(!location.exists()) {
			System.out.println("File location did not exist, creating at :"+location.getAbsolutePath());
			System.out.println("Creating file at location succeeded:"+ location.createNewFile());
		}
		if(!studLib.exists()) {
			System.out.println("Student library did not exist, creating at : "+studLib.getAbsolutePath());
			System.out.println("Creatind directory for library succeeded: "+studLib.getParentFile().mkdirs());
			System.out.println("Creating library at location succeeded: "+studLib.createNewFile());
		}
		
		try {
		today = (Day)filer.read();
		
		}
		catch(UnmarshalException e) {
			today = new Day(CurrentTime.getDay());
		}
		try {
		lib = (StudentLibrary)librarian.read();
		}
		catch(UnmarshalException e) {
			lib = new StudentLibrary();
		}
		populate();
		filer.write(today);
		return today;
	}
	
	public static File[] fileChooser() {
		JFileChooser chooser = new JFileChooser(Desktop.getDesktop().toString());
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File[] paths = new File[3];
			paths[0] = new File(chooser.getSelectedFile().getParentFile().toString()+"/Dates/");
			File loc = new File(chooser.getSelectedFile().getParentFile().toString()+"/Dates/"+CurrentTime.getDay()+".xml");
			File studLib = new File(chooser.getSelectedFile().getParentFile().toString()+"/StudIDs/students.xml");
			System.out.println("loc path in chooser: "+loc.toString());
			System.out.println("dir path in chooser: "+ paths[0].toString());
			System.out.println("Student directories :"+studLib.toString());
			paths[2] = studLib;
			paths[1] = loc;
			return paths;	
		}
		return null;	
	}
	
	public static void populate() {
		for(Identifiers identity:lib.getStudents()) {
			if(today.findStudent(identity.getId())==null)
				today.addStudent(new Student(identity));
			
		}
	}
	
	public static XMLFiler dayFiler() {
		return filer;
	}
	
	public static XMLFiler libFiler() {
		return librarian;
	}
}
