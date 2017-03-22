package com.coderschool.vinh.todoapp.fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.coderschool.vinh.todoapp.models.DialogResponse;
import com.coderschool.vinh.todoapp.models.Task;

import org.parceler.Parcels;

import java.util.Calendar;

public class TaskDialog extends DialogFragment
        implements View.OnClickListener, TextWatcher {
    public static final String FRAGMENT_EDIT_NAME = "TaskDialog";

    private EditText edTaskName;
    private RadioGroup rgPriority;
    private RadioButton rbPriorityLow;
    private RadioButton rbPriorityMedium;
    private RadioButton rbPriorityHigh;
    private DatePicker tpDueDate;
    private Button btnDiscard;
    private Button btnSave;

    private boolean isChanged; // ???

    public TaskDialog() {
    }

    public static TaskDialog newInstance() {
        Bundle args = new Bundle();
        args.putBoolean("available", false);

        TaskDialog frag = new TaskDialog();
        frag.setArguments(args);

        return frag;
    }

    public static TaskDialog newInstance(@NonNull Task task) {
        Bundle args = new Bundle();
        args.putBoolean("available", true);
        args.putParcelable("task", Parcels.wrap(task));

        TaskDialog frag = new TaskDialog();
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

        edTaskName = (EditText) view.findViewById(R.id.text_input_text);
        btnDiscard = (Button) view.findViewById(R.id.button_discard);
        btnSave = (Button) view.findViewById(R.id.button_save);
        rgPriority = (RadioGroup) view.findViewById(R.id.radio_group_priority_1);
        tpDueDate = (DatePicker) view.findViewById(R.id.date_picker_due_date);
        rbPriorityLow = (RadioButton) view.findViewById(R.id.radio_low);
        rbPriorityMedium = (RadioButton) view.findViewById(R.id.radio_medium);
        rbPriorityHigh = (RadioButton) view.findViewById(R.id.radio_high);

        // Solution for enabling IME
        // http://stackoverflow.com/a/14815062/5557789
        edTaskName.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        edTaskName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        showSoftKeyboard(edTaskName);

        isChanged = getArguments().getBoolean("available");
        Task task = Parcels.unwrap(getArguments().getParcelable("task"));
        if (isChanged) {
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

            RadioButton rbPriority = (RadioButton) getView().findViewById(selectedId);
            String priority = rbPriority.getText().toString();

            Task task = new Task(
                edTaskName.getText().toString(),
                priority,
                getDate(tpDueDate)
            );

            TaskDialogOnFinishedListener listener = (TaskDialogOnFinishedListener) getActivity();
            listener.onTaskDialogFinished(new DialogResponse(isChanged, task));

            getDialog().dismiss();
        }
    }

    private Calendar getDate(DatePicker datePicker) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, datePicker.getYear());
        cal.set(Calendar.MONTH, datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        return cal;
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

    public interface TaskDialogOnFinishedListener {
        void onTaskDialogFinished(DialogResponse response);
    }
}