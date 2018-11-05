package xmlfiler;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.xml.sax.SAXParseException;
@XmlRootElement(name = "Day")
public class Day{
@XmlAttribute(name = "current_Day")
private static String today;
@XmlElementWrapper(name = "Students")
@XmlElement(name = "Student")
private ArrayList<Student> students = new ArrayList<Student>();
	
	public Day(){}

	public Day(String today) {
		this.today = today;
	}
	
	public void addStudent(Student stud) {
		students.add(stud);
	}
	
	public void signIn(Student stud) {
		try {
		if(stud.isSignedIn())
			System.out.println("already signed in...");
		else
			stud.signIn();
		}
		catch(NullPointerException e) {
			System.out.println("Student couldnt be found");
		}
		}
	
	public void signOut(Student stud) {
		if(stud.isSignedIn())
			stud.signOut();
		else
			System.out.println("already signed out...");
	}

	public void setDay(String today) {
		this.today = today;
	}
	
	public ArrayList<Student> getStudents(){
		return students;
	}
	
	public boolean studentExists(double id) {
		for(int x=0; x<students.size();x++) {
			if(students.get(x).getId() == id) 
				return true;
		}
		return false;
	}
	
	public Student findStudent(double id) {
		for(int x=0; x<students.size();x++) {
			if(students.get(x).getId() == id) 
				return students.get(x);
		}
		return null;
	}
	
	public String getDay() {
		return today;
	}
}
