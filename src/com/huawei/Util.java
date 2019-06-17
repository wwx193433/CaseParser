package com.huawei;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public String formatDuration(String start, String end) {
		String costTime = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
			Date startDate = sdf.parse(start);
			Date endDate = sdf.parse(end);
			long diff = endDate.getTime() - startDate.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
	        long hours = (diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
	        long minutes = (diff % (1000 * 60 * 60)) / (1000 * 60);
	        long seconds = (diff % (1000 * 60)) / 1000;
	        costTime = days + "d " + hours + "h " + minutes + "min "
	                + seconds + "sec 0msec";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return costTime;
	}

	public static void main(String[] args) {
		String start = "2019-04-05 10:18:20";
		String end = "2019-04-05 12:22:22";
		String costTime = new Util().formatDuration(start, end);
		System.out.println(costTime);
	}
}
