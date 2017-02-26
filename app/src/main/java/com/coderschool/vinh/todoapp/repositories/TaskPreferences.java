package com.coderschool.vinh.todoapp.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by vinh on 2/26/17.
 */

public class TaskPreferences {
    private Context context;
    private SharedPreferences pref;

    public TaskPreferences(Context context) {
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setCurrentPosition(int position) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("currentPosition", position);
        edit.apply();
    }

    public int getCurrentPosition() {
        return pref.getInt("currentPosition", -1);
    }

}
