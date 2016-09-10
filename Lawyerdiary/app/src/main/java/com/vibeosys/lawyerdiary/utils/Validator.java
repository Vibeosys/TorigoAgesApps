package com.vibeosys.lawyerdiary.utils;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by akshay on 10-09-2016.
 */
public class Validator {

    public static boolean isValidMail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        if (phone.length() > 10 || phone.length() < 10)
            return false;
        else
            return Patterns.PHONE.matcher(phone).matches();
    }

    public boolean validateUserName(final String username) {

        Pattern pattern;
        Matcher matcher;

        final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(username);
        return matcher.matches();

    }
}