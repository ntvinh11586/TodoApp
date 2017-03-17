package com.coderschool.vinh.todoapp.models;

public class DialogResponse {
    private boolean isChanged;
    private Task task;

    public DialogResponse(boolean isChanged, Task task) {
        this.isChanged = isChanged;
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public boolean getIsChangeable() {
        return isChanged;
    }
}
