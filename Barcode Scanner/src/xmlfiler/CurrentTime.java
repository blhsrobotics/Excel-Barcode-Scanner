package xmlfiler;

import java.time.ZonedDateTime;

public class CurrentTime {
	
	public static String getTime() {
		return ZonedDateTime.now().toString().substring(11, 19);
	}
	
	public static String getDay() {
		return ZonedDateTime.now().toString().substring(0, 10);
	}
	
}
