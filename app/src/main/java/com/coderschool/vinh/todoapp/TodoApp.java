package com.coderschool.vinh.todoapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class TodoApp extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
