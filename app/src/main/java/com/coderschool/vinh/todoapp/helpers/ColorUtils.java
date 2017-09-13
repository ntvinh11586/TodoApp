package com.coderschool.vinh.todoapp.helpers;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.coderschool.vinh.todoapp.R;

public class ColorUtils {
    public static int getColor(Context context, String colorStr) {
        switch (colorStr) {
            case "Low":
                return ContextCompat.getColor(context, R.color.colorPriorityLow);
            case "Medium":
                return ContextCompat.getColor(context, R.color.colorPriorityMedium);
            case "High":
                return ContextCompat.getColor(context, R.color.colorPriorityHigh);
            default:
                return ContextCompat.getColor(context, R.color.colorPriorityHigh);
        }
    }
}
