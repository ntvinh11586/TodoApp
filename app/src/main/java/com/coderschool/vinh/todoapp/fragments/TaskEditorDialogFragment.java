package com.coderschool.vinh.todoapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RadioButton;

import com.coderschool.vinh.todoapp.models.Task;

import org.parceler.Parcels;

import java.util.Calendar;

public class TaskEditorDialogFragment extends TaskDialogFragment {
    private static final String ARGS_TASK = "Task";
    private static final String ARGS_POSITION = "Position";

    private TaskEditorDialogOnFinishedListener listener;

    public interface TaskEditorDialogOnFinishedListener
            extends TaskDialogOnFinishedListener {
        void onTaskEditorDialogFinished(Task task, int position);
    }

    public static TaskDialogFragment newInstance(@NonNull Task task, int position) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_TASK, Parcels.wrap(task));
        args.putInt(ARGS_POSITION, position);

        TaskDialogFragment taskDialogFragment = new TaskEditorDialogFragment();
        taskDialogFragment.setArguments(args);

        return taskDialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            listener = (TaskEditorDialogOnFinishedListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    protected void setupTaskDialogBehavior() {
        super.setupTaskDialogBehavior();
        Task task = Parcels.unwrap(getArguments().getParcelable(ARGS_TASK));
        edTaskName.setText(task.getName());

        switch (task.getPriority()) {
            case "Low":
                rbPriorityLow.setChecked(true);
                break;
            case "Medium":
                rbPriorityMedium.setChecked(true);
                break;
            case "High":
                rbPriorityHigh.setChecked(true);
                break;
        }

        tpDueDate.init(
                task.getDate().get(Calendar.YEAR),
                task.getDate().get(Calendar.MONTH),
                task.getDate().get(Calendar.DAY_OF_MONTH),
                null
        );
    }

    @Override
    void onSaveButtonClick() {
        super.onSaveButtonClick();

        if (getView() != null) {
            RadioButton rbPriority = (RadioButton) getView().findViewById(
                    rgPriority.getCheckedRadioButtonId()
            );
            String priority = rbPriority.getText().toString();
            String taskName = edTaskName.getText().toString();
            Calendar date = getDate(tpDueDate);
            int position = getArguments().getInt(ARGS_POSITION);

            listener.onTaskEditorDialogFinished(
                    new Task(taskName, priority, date),
                    position
            );
        }

        dismiss();
    }

    @Override
    void onDiscardButtonClick() {
        super.onDiscardButtonClick();
        dismiss();
    }
}
