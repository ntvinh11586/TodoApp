package com.coderschool.vinh.todoapp.models;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

import java.util.Calendar;

@Parcel
public class Task {
    public int id;
    public String name;
    public String priority;
    public Calendar date;

    public Task() {
    }

    public Task(String name, String priority, Calendar date) {
        this.name = name;
        this.priority = priority;
        this.date = date;
    }

    public Task(int id, String name, String priority, @NonNull Calendar date) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.date = date;
    }
}
