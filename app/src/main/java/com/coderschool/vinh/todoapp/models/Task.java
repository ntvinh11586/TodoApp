package com.coderschool.vinh.todoapp.models;


public class Task {

    public String name;
    public String priority;
    public Date date;

    public Task(String name, String priority, Date date) {
        this.name = name;
        this.priority = priority;
        this.date = date;
    }

}
