package com.project.simoneconigliaro.thecurrentnews.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {
    public static String dateFormatter(String dateString){
        // Get only the part of date with yyyy-MM-dd'T'HH:mm
        dateString = dateString.substring(0,16);
        // Create format with pattern yyyy-MM-dd'T'HH:mm
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        long time = 0;
        long currentTime = System.currentTimeMillis();
        try {
            // Analyze dateString to find a date with the pattern given to dateFormat and get the time in millisecond
            time = dateFormat.parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Returns a string describing 'time' as a time relative to 'now'.
        CharSequence ago = android.text.format.DateUtils.getRelativeTimeSpanString(time, currentTime, android.text.format.DateUtils.MINUTE_IN_MILLIS);
        dateString = ago.toString();
        return dateString;
    }
}
