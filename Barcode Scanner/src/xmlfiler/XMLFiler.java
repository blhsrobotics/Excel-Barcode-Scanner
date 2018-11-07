package xmlfiler;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLFiler {
	private Marshaller mar;
	private Unmarshaller unmar;
	private File dayLocation;
	
	public XMLFiler(File dayLoc, Object classType) throws JAXBException {
		dayLocation = dayLoc;
		JAXBContext jxaber = JAXBContext.newInstance(classType.getClass());
		mar = jxaber.createMarshaller();
		unmar = jxaber.createUnmarshaller();
		dayLocation = dayLoc;
		mar.setProperty(mar.JAXB_FORMATTED_OUTPUT, true);
	}
	
	public void write(Object obj) throws JAXBException {
		mar.marshal(obj, dayLocation);
	}
	
	public Object read()  {
		try {
			return unmar.unmarshal(dayLocation);
		} catch (JAXBException e) {
			System.out.println("Date was new");
		}
		return null;
	}
	
}

