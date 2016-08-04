package phd.Nabsim.Utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatUtility {

	public static String convertDateToStringUTC(Date date){
		String result = null;
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		result = formatter.format(date);
		
		return result;
	}
	
	public static Date convertStringToDate(String date){
		Date result = null;			

		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			result = sdf.parse(date);
		} catch (Exception e){
			System.out.println("error converting string to date");
		}
		
		return result;
	}
}
