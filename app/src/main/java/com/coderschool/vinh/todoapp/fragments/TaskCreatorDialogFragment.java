package com.coderschool.vinh.todoapp.fragments;

import android.os.Bundle;
import android.widget.RadioButton;

import com.coderschool.vinh.todoapp.models.Task;

import java.util.Calendar;

public class TaskCreatorDialogFragment extends TaskDialogFragment {
    private TaskCreatorDialogOnFinishedListener listener;

    public interface TaskCreatorDialogOnFinishedListener
            extends TaskDialogOnFinishedListener {
        void onTaskCreatorDialogFinished(Task task);
    }

    public static TaskDialogFragment newInstance() {
        Bundle args = new Bundle();
        args.putBoolean(ARGS_AVAILABLE, false);

        TaskDialogFragment taskDialogFragment = new TaskCreatorDialogFragment();
        taskDialogFragment.setArguments(args);

        return taskDialogFragment;
    }

    @Override
    protected void setupTaskNameBehavior() {
        super.setupTaskNameBehavior();
        tpDueDate.init(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                null
        );
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
        listener = (TaskCreatorDialogOnFinishedListener) getActivity();
        listener.onTaskCreatorDialogFinished(task);

        getDialog().dismiss();
    }

    @Override
    void onClickDiscardButton() {
        super.onClickDiscardButton();
        getDialog().dismiss();
    }
}
