package com.vibeosys.fitnessapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by akshay on 11-03-2017.
 */
public class DateUtils {

    final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    final static SimpleDateFormat dateReadFormat = new SimpleDateFormat("dd MMM yyyy");

    public static Date dateWithoutTime(Date date) throws ParseException {
        return dateFormat.parse(dateFormat.format(date));

    }

    public static String getReadDateInFormat(java.util.Date date) {
        return dateReadFormat.format(date);
    }
}
