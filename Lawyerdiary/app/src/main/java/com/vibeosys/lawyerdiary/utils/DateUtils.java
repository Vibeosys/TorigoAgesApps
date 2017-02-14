package com.vibeosys.lawyerdiary.utils;

import android.util.Log;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Vibeosys software on 14-09-2016.
 */

/**
 * Its utility class that give the date in requested format
 * change the format or to convert time stamp to time or String to date.
 */
public class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();
    // Date in readable format e.g. 01 Jan 2017
    final SimpleDateFormat dateReadFormat = new SimpleDateFormat("dd MMM yyyy");
    // Date and time format e.g. 31-o1-2017 15:30:22
    final SimpleDateFormat dateTimeReadFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    // Date and time format e.g. 01 Jan 2017 02:30 PM
    final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
    // Time format e.g. 02:30 PM
    final SimpleDateFormat timeReadFormat = new SimpleDateFormat("hh:mm aa");


    public String getLocalDateInReadableFormat(java.util.Date date) {
        return dateReadFormat.format(date);
    }

    public String getDateTimeReadFormat(java.util.Date date) {
        return dateTimeReadFormat.format(date);
    }

    public String getDateTimeFormat(java.util.Date date) {
        return dateTimeFormat.format(date);
    }

    public String getLocalTimeInReadableFormat(java.util.Date date) {
        return timeReadFormat.format(date);
    }

    public java.util.Date getFormattedDate(String strDate) {
        java.util.Date date = null;
        try {
            date = dateTimeReadFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * Convert time string to datetime stamp into long integer
     * Give the time string in 02:30 PM format only that convert it into the time stamp
     *
     * @param time String time in 02:30 PM format
     * @return long timestamp of the given time
     */
    public long getLongTime(String time) {
        java.util.Date date = null;
        try {
            date = timeReadFormat.parse(time);

        } catch (ParseException e) {
            Log.e(TAG, "Error to convert time to long" + e.toString());
            e.printStackTrace();
        }
        if (date != null)
            return date.getTime();
        else return 0;
    }

    /**
     * Convert date string to datetime stamp into long integer
     * Give the time string in 01 Jan 2017 format only that convert it into the time stamp
     *
     * @param time date string is in 01 Jan 2017 format only
     * @return long timestamp of the given date
     */
    public long getLongDate(String time) {
        java.util.Date date = null;
        try {
            date = dateReadFormat.parse(time);

        } catch (ParseException e) {
            Log.e(TAG, "Error to convert time to long" + e.toString());
            e.printStackTrace();
        }
        if (date != null)
            return date.getTime();
        else return 0;
    }

    /**
     * Convert the string to Date(java.util.Date)
     * Give the date-time in 01 Jan 2017 02:30 PM in this format this will convert it into the Date
     *
     * @param strDate String date and time in this 01 Jan 2017 02:30 PM format only
     * @return java.util.Date the converted date and time
     */
    public java.util.Date convertTimeToDate(String strDate) {
        DateFormat df2 = new SimpleDateFormat("dd MMM yyyy hh:mm aa", Locale.ENGLISH);
        java.util.Date date = null;
        try {
            date = df2.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
