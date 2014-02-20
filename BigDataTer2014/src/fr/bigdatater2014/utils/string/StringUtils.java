package fr.bigdatater2014.utils.string;

import java.util.Date;
import java.text.SimpleDateFormat;

public class StringUtils {
	
	///////////////////////////////////////////////////////////////////////////////////////////
	// HTML convert
	///////////////////////////////////////////////////////////////////////////////////////////

	static public String removeHTMLMarkup(String htmlCode) {
		/* Initialize */
		int pos = 0;
		String result = "";
		/* Remove HTML markup */
		for(int i = 0, max = htmlCode.length(); i < max; i++) {
			if (htmlCode.charAt(i) == '<') {
				result += htmlCode.substring(pos, i);
				pos = i;
			} else if (htmlCode.charAt(i) == '>') {
				pos = i+1;
			}
		}
		result += htmlCode.substring(pos);
		return result;
	}
	
	static public Date dateFromString(String strDate) {
		return dateFromString(strDate, "yyyy-MM-dd HH:mm:ss");
	}
	static public Date dateFromString(String strDate, String format) {
		try {
	        SimpleDateFormat sdf = new SimpleDateFormat(format);
	        return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}
	static public String stringFromDate(Date date) {
		return stringFromDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	static public String stringFromDate(Date date, String format) {
		try {
	        SimpleDateFormat sdf = new SimpleDateFormat(format);
	        return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
}
