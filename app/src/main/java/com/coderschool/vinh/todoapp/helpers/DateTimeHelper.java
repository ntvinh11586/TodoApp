package com.coderschool.vinh.todoapp.helpers;

import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeHelper {
    public static String getStringDateStandard(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getStringDateFullStandard(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }

    @Nullable
    public static Calendar getCalendarFullStandard(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse(stringDate));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
