package com.coderschool.vinh.todoapp.fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.coderschool.vinh.todoapp.R;
import com.coderschool.vinh.todoapp.models.Date;
import com.coderschool.vinh.todoapp.models.DialogResponse;
import com.coderschool.vinh.todoapp.models.Task;

import java.util.Calendar;

public class TaskDialogFragment extends DialogFragment
        implements View.OnClickListener, TextWatcher {
    private EditText edTaskName;
    private RadioGroup rgPriority;
    private RadioButton rbPriorityLow;
    private RadioButton rbPriorityMedium;
    private RadioButton rbPriorityHigh;
    private DatePicker tpDueDate;
    private Button btnDiscard;
    private Button btnSave;

    private int isChanged; // ???

    public TaskDialogFragment() {
    }

    public static TaskDialogFragment newInstance(Task task) {
        Bundle args = new Bundle();
        if (task != null) {
            args.putInt("available", 1);
            args.putString("name", task.name);
            args.putString("priority", task.priority);
            args.putInt("day", task.date.getDay());
            args.putInt("month", task.date.getMonth());
            args.putInt("year", task.date.getYear());
        } else {
            args.putInt("available", 0);
        }

        TaskDialogFragment frag = new TaskDialogFragment();
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_dialog, container);
    }

    public void showSoftKeyboard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        // it only works when we set SHOW_FORCED instead of IMPLICIT
        // http://stackoverflow.com/a/8991563/5557789 (in comment)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edTaskName = (EditText)view.findViewById(R.id.text_input_text);
        btnDiscard = (Button)view.findViewById(R.id.button_discard);
        btnSave = (Button)view.findViewById(R.id.button_save);
        rgPriority = (RadioGroup)view.findViewById(R.id.radio_group_priority_1);
        tpDueDate = (DatePicker)view.findViewById(R.id.date_picker_due_date);
        rbPriorityLow = (RadioButton)view.findViewById(R.id.radio_low);
        rbPriorityMedium = (RadioButton)view.findViewById(R.id.radio_medium);
        rbPriorityHigh = (RadioButton)view.findViewById(R.id.radio_high);

        // Solution for enabling IME
        // http://stackoverflow.com/a/14815062/5557789
        edTaskName.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        edTaskName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        showSoftKeyboard(edTaskName);

        isChanged = getArguments().getInt("available");
        if (isChanged == 1) {
            edTaskName.setText(getArguments().getString("name"));
            if (!edTaskName.getText().toString().equals("")) {
                btnSave.setEnabled(true);
            }

            switch (getArguments().getString("priority")) {
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

            int day = getArguments().getInt("day");
            int month = getArguments().getInt("month") - 1;
            int year = getArguments().getInt("year");

            tpDueDate.init(year, month, day, null);
        } else {
            tpDueDate.init(
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                    null
            );
        }

        edTaskName.addTextChangedListener(this);
        btnDiscard.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        // see more at this link:
        // https://github.com/codepath/android_guides/wiki/Using-DialogFragment#sizing-dialogs
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.95), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_discard) {
            getDialog().dismiss();
        } else if (v.getId() == R.id.button_save) {
            int selectedId = rgPriority.getCheckedRadioButtonId();
            RadioButton rbPriority = (RadioButton)getView().findViewById(selectedId);
            String priority = rbPriority.getText().toString();
            Date dueDate = new Date(tpDueDate.getDayOfMonth(), tpDueDate.getMonth() + 1, tpDueDate.getYear());
            String taskName = edTaskName.getText().toString();
            TaskDialogListener listener = (TaskDialogListener) getActivity();

            listener.onTaskDialogFinished(
                    new DialogResponse(isChanged, taskName, priority, dueDate)
            );
            getDialog().dismiss();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (edTaskName.getText().toString().equals("")) {
            btnSave.setEnabled(false);
        } else {
            btnSave.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}