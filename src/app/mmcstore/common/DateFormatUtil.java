package app.mmcstore.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

	public static String getCurrentTimeInStringFromDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
		String timestr = formatter.format(date);
		return timestr;
	}

	public static String dateToString(Date date, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		String datestr = formatter.format(date);
		return datestr;
	}

	public static Date stringDateToDate(String datestr, Date date, String dateFormat) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.parse(datestr);
	}

}
