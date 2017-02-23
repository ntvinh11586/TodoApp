package com.coderschool.vinh.todoapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by vinh on 2/23/17.
 */
public class TodoApp extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
