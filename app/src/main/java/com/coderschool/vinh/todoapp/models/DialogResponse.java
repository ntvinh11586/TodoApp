package com.coderschool.vinh.todoapp.models;

/**
 * Created by vinh on 2/26/17.
 */

public class DialogResponse {
    private int isChanged;
    private String taskName;
    private String priority;
    private Date dueDate;

    public DialogResponse(int isChanged, String taskName, String priority, Date dueDate) {
        this.isChanged = isChanged;
        this.taskName = taskName;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public int getIsChanged() {
        return isChanged;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getPriority() {
        return priority;
    }

    public Date getDueDate() {
        return dueDate;
    }
}
