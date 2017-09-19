package com.coderschool.vinh.todoapp.helpers;

import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeHelper {
    private static String STANDARD_DATE_FORMAT = "yyyy-MM-dd";
    private static String FULL_STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private String dateFormat;
    private Locale locale;

    private DateTimeHelper(String dateFormat, Locale locale) {
        this.dateFormat = dateFormat;
        this.locale = locale;
    }

    public static DateTimeHelper createStandardDateTime() {
        return new DateTimeHelper(STANDARD_DATE_FORMAT, Locale.getDefault());
    }

    public static DateTimeHelper createFullStandardDateTime() {
        return new DateTimeHelper(FULL_STANDARD_DATE_FORMAT, Locale.getDefault());
    }

    public String getStringDate(Calendar calendar) {
        return (new SimpleDateFormat(dateFormat, locale)).format(calendar.getTime());
    }

    @Nullable
    public Calendar getCalendar(String stringDate) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((new SimpleDateFormat(dateFormat, locale)).parse(stringDate));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
