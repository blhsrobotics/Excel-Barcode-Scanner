package xmlfiler;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Accounts")


public class StudentLibrary {
	@XmlElementWrapper(name = "Students")
	@XmlElement(name = "student")
	private ArrayList<Identifiers> students = new ArrayList<Identifiers>();
	
	public StudentLibrary() {}
	
	public ArrayList<Identifiers> getStudents() {
		return students;
	}
	
	public void addStudent(Student stud) {
		if(findStudent(stud.getId())==null)
						students.add(new Identifiers(stud.getId(),stud.getName()));
	}

	public void removeStudent(Student stud) {
		students.remove(stud);
	}
	
	public Identifiers findStudent(double id) {
		for(int x=0; x<students.size();x++) {
			if(students.get(x).getId() == id) 
				return students.get(x);
		}
		return null;
	}
	
}
