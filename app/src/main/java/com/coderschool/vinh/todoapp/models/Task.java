package com.coderschool.vinh.todoapp.models;


import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.coderschool.vinh.todoapp.R;

public class Task {
    public int id;
    public String name;
    public String priority;
    public Date date;

    public Task(String name, String priority, Date date) {
        this.name = name;
        this.priority = priority;
        this.date = date;
    }

    public Task(int id, String name, String priority, Date date) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.date = date;
    }

    public static int getColor(Context context, String s) {
        switch (s) {
            case "Low":
                return ContextCompat.getColor(context, R.color.colorPriorityLow);
            case "Medium":
                return ContextCompat.getColor(context, R.color.colorPriorityMedium);
            default:
                return ContextCompat.getColor(context, R.color.colorPriorityHigh);
        }
    }
}
