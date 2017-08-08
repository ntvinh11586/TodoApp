package com.coderschool.vinh.todoapp.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TaskPreferences {
    private SharedPreferences pref;

    public TaskPreferences(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getCurrentPosition() {
        return pref.getInt("currentPosition", -1);
    }

    public void setCurrentPosition(int position) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("currentPosition", position);
        edit.apply();
    }
}
