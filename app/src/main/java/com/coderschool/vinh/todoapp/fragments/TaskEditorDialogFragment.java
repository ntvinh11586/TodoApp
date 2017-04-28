package com.coderschool.vinh.todoapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RadioButton;

import com.coderschool.vinh.todoapp.models.Task;

import org.parceler.Parcels;

import java.util.Calendar;

public class TaskEditorDialogFragment extends TaskDialogFragment {
    private TaskEditorDialogOnFinishedListener listener;

    public interface TaskEditorDialogOnFinishedListener
            extends TaskDialogOnFinishedListener {
        void onTaskEditorDialogFinished(Task task);
    }

    public static TaskDialogFragment newInstance(@NonNull Task task) {
        Bundle args = new Bundle();
        args.putBoolean(ARGS_AVAILABLE, true);
        args.putParcelable(ARGS_TASK, Parcels.wrap(task));

        TaskDialogFragment taskDialogFragment = new TaskEditorDialogFragment();
        taskDialogFragment.setArguments(args);

        return taskDialogFragment;
    }

    @Override
    protected void setupTaskNameBehavior() {
        super.setupTaskNameBehavior();
        Task task = Parcels.unwrap(getArguments().getParcelable("task"));
        edTaskName.setText(task.name);
        if (!edTaskName.getText().toString().equals("")) {
            btnSave.setEnabled(true);
        }

        switch (task.priority) {
            case "Low":
                rbPriorityLow.setChecked(true);
                rbPriorityMedium.setChecked(false);
                rbPriorityHigh.setChecked(false);
                break;
            case "Medium":
                rbPriorityLow.setChecked(false);
                rbPriorityMedium.setChecked(true);
                rbPriorityHigh.setChecked(false);
                break;
            case "High":
                rbPriorityLow.setChecked(false);
                rbPriorityMedium.setChecked(false);
                rbPriorityHigh.setChecked(true);
                break;
        }

        int day = task.date.get(Calendar.DAY_OF_MONTH);
        int month = task.date.get(Calendar.MONTH);
        int year = task.date.get(Calendar.YEAR);

        tpDueDate.init(year, month, day, null);
    }

    @Override
    void onClickSaveButton() {
        super.onClickSaveButton();
        RadioButton rbPriority = (RadioButton) getView().findViewById(
                rgPriority.getCheckedRadioButtonId()
        );
        String priority = rbPriority.getText().toString();
        String taskName = edTaskName.getText().toString();

        Task task = new Task(taskName, priority, getDate(tpDueDate));
        listener = (TaskEditorDialogOnFinishedListener) getActivity();
        listener.onTaskEditorDialogFinished(task);

        getDialog().dismiss();
    }

    @Override
    void onClickDiscardButton() {
        super.onClickDiscardButton();
        getDialog().dismiss();
    }
}
