package com.coderschool.vinh.todoapp.fragments;

import com.coderschool.vinh.todoapp.models.DialogResponse;

public interface TaskDialogListener {
    void onTaskDialogFinished(DialogResponse response);
}