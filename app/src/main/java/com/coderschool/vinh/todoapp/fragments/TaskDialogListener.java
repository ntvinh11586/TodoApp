package com.coderschool.vinh.todoapp.fragments;

import com.coderschool.vinh.todoapp.models.Date;

public interface TaskDialogListener {
        void onTaskDialogFinished(int isChanged, final String taskName, final String priority, final Date dueDate);
    }