package com.chandan.android.comicsworld.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String getTodayDateString() {
        return getDateString(new Date());
    }

    public static String getDateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date);
    }

    public static String getFormattedDate(String rawDate, String format) {

        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date storeDate = new Date();

        try {
            storeDate = oldFormat.parse(rawDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat newFormat = new SimpleDateFormat(format, Locale.US);

        return newFormat.format(storeDate);
    }

    public static String getFormattedDateToday() {
        return getFormattedDate(getTodayDateString(), "MMM d, yyyy");
    }

//    public static Date getDateFromString(String dateStr) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        try {
//            Date date = format.parse(dateStr);
//            System.out.println(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
