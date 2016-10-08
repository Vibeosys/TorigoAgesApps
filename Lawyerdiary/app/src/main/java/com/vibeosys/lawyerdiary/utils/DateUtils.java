package com.vibeosys.lawyerdiary.utils;

import android.util.Log;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by akshay on 14-09-2016.
 */
public class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    final SimpleDateFormat dateReadFormat = new SimpleDateFormat("dd MMM yyyy");
    final SimpleDateFormat dateTimeReadFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
    final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    final SimpleDateFormat timeReadFormat = new SimpleDateFormat("hh:mm aa");
    final SimpleDateFormat SqlFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String getGMTCurrentDate() {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(new java.util.Date());
    }

    public String getLocalCurrentDate() {
        return dateFormat.format(new java.util.Date());
    }

    public String getLocalSQLCurrentDate() {
        return SqlFormat.format(new java.util.Date());
    }

    public String getLocalDateInFormat(java.util.Date date) {
        return dateFormat.format(date);
    }

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

    public String getLocalTimeInReadableFormat() {
        return timeReadFormat.format(new java.util.Date());
    }

    public String getGMTDateInFormat(Date date) {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(date);
    }

    public String getGMTCurrentTime() {
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return timeFormat.format(new java.util.Date());
    }

    public String getSqlOffsetTime(int hour, int min) {
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        long time = new java.util.Date().getTime() - getTimeOffsetAsPerLocal(hour, min);
        return timeFormat.format(new java.util.Date(time));
    }


    public String getLocalCurrentTime() {
        return timeFormat.format(new java.util.Date());
    }

    public String getLocalTimeInFormat(Date date) {
        return timeFormat.format(date);
    }

    public String getGMTTimeInFormat(Date date) {
        return timeFormat.format(date);
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

    public long getTimeOffsetAsPerLocal(int hours, int mins) {
        return ((hours * 60 * 60 * 1000) + (mins * 60 * 1000));
    }

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
}
