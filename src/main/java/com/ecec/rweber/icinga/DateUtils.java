package com.ecec.rweber.icinga;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

	public static String formatDate(long time){
		String result = null;
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		result = format.format(new Date(time));
		
		return result;
	}

	public static long toTimestamp(String time){
		long result = 0;
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			Date date = format.parse(time);
			
			result = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
