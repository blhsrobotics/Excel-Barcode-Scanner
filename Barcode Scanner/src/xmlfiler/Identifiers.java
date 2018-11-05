package xmlfiler;

import javax.xml.bind.annotation.XmlElement;

public class Identifiers {
	@XmlElement(name = "_ID_")
	private double Id;
	@XmlElement(name = "_Name_")
	private String name;
	
	public Identifiers() {}
	public Identifiers(double id, String name) {
		Id = id;
		this.name = name;
	}

	public double getId() {
		return Id;
	}

	public String getName() {
		return name;
	}
	
	public void put(double id, String name) {
		Id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return Id+", "+name;
		
	}
}
