package com.coderschool.vinh.todoapp.Helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Vinh on 3/22/2017.
 */

public class DateTimeHelper {
    public static String getStringDateStandard(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }
}
