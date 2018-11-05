package xmlfiler;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Student {
	@XmlElement(name = "Identifiers")
	Identifiers coords = new Identifiers();
	@XmlElementWrapper(name = "Sign_Ins")
	@XmlElement(name = "time")
	private ArrayList<String> signInTimes = new ArrayList<String>();
	@XmlElementWrapper(name ="Sign_Outs")
	@XmlElement(name = "time")
	private ArrayList<String> signOutTimes = new ArrayList<String>();
	@XmlElement(name = "isSignedIn")
	private boolean signedIn = false;
	
	public Student() {}
	public Student(double id, String name) {
	coords.put(id, name);
	}
	
	public Student(Identifiers coordinates) {
		coords = coordinates;
	}
	
	public void signIn() {
		signInTimes.add(CurrentTime.getTime());
		signedIn = true;
	}
	public void signOut() {
		signOutTimes.add(CurrentTime.getTime());
		signedIn = false;
	}

	public boolean isSignedIn() {
		return signedIn;
	}

	public String getName() {
		return coords.getName();
	}
	
	public double getId() {
		return coords.getId();
	}

	@Override
	public String toString() {
		return coords.toString();
	}
	
	public double getTotalTimeToday() {
		double hours = 0;
		if(signInTimes.size()>signOutTimes.size()) {
			 for(int x = 0; x<signInTimes.size()-1;x++) {
					int hourTimeOne = Integer.parseInt(signInTimes.get(x).substring(0, 2));
					int hourTimeTwo = Integer.parseInt(signOutTimes.get(x).substring(0, 2));
					double minuteTimeOne = Double.parseDouble(signInTimes.get(x).substring(3,5));
					double minuteTimeTwo = Double.parseDouble(signOutTimes.get(x).substring(3,5));
					hours+= (hourTimeTwo-hourTimeOne)+(minuteTimeTwo-minuteTimeOne)/60;
					
			 	}
		}
		else if(signInTimes.size()==signOutTimes.size()){
			 for(int x = 0; x<signInTimes.size();x++) {
					int hourTimeOne = Integer.parseInt(signInTimes.get(x).substring(0, 2));
					int hourTimeTwo = Integer.parseInt(signOutTimes.get(x).substring(0, 2));
					double minuteTimeOne = Double.parseDouble(signInTimes.get(x).substring(3,5));
					double minuteTimeTwo = Double.parseDouble(signOutTimes.get(x).substring(3,5));
					hours+= (hourTimeTwo-hourTimeOne)+(minuteTimeTwo-minuteTimeOne)/60;
					
			 	}
		}
		
		return hours;
	}
}