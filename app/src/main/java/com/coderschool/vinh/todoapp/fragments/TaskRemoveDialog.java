package com.coderschool.vinh.todoapp.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.coderschool.vinh.todoapp.R;

public class TaskRemoveDialog extends DialogFragment {
    private static final String ARGS_POSITION = "Position";

    private TaskRemoveDialogListener listener;

    public interface TaskRemoveDialogListener {
        void onOKButtonClick(int position);
        void onCancelButtonClick(int position);
    }

    public static TaskRemoveDialog newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_POSITION, position);

        TaskRemoveDialog taskRemoveDialog = new TaskRemoveDialog();
        taskRemoveDialog.setArguments(bundle);

        return taskRemoveDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            listener = (TaskRemoveDialogListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Delete Task")
                .setMessage(R.string.are_you_sure_to_delete_this_task)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onOKButtonClick(getArguments().getInt(ARGS_POSITION));
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onCancelButtonClick(getArguments().getInt(ARGS_POSITION));
                    }
                })
                .create();
    }
}
