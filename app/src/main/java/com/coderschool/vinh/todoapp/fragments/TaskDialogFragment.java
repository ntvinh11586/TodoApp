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

import java.util.Calendar;

public class TaskDialogFragment extends DialogFragment
        implements View.OnClickListener, TextWatcher {
    protected EditText edTaskName;
    protected RadioGroup rgPriority;
    protected RadioButton rbPriorityLow;
    protected RadioButton rbPriorityMedium;
    protected RadioButton rbPriorityHigh;
    protected DatePicker tpDueDate;
    protected Button btnDiscard;
    protected Button btnSave;

    interface TaskDialogOnFinishedListener {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rgPriority = (RadioGroup) view.findViewById(R.id.radio_group);
        tpDueDate = (DatePicker) view.findViewById(R.id.date_picker_due_date);
        rbPriorityLow = (RadioButton) view.findViewById(R.id.radio_low);
        rbPriorityMedium = (RadioButton) view.findViewById(R.id.radio_medium);
        rbPriorityHigh = (RadioButton) view.findViewById(R.id.radio_high);

        // Enabling IME Solution:
        // http://stackoverflow.com/a/14815062/5557789
        edTaskName = (EditText) view.findViewById(R.id.text_input_text);
        edTaskName.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        edTaskName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edTaskName.addTextChangedListener(this);
        showSoftKeyboard(edTaskName);

        btnDiscard = (Button) view.findViewById(R.id.button_discard);
        btnDiscard.setOnClickListener(this);

        btnSave = (Button) view.findViewById(R.id.button_save);
        btnSave.setOnClickListener(this);

        setupTaskDialogBehavior();
    }

    @Override
    public void onResume() {
        // see more how to resize dialogs in DialogFragment:
        // https://guides.codepath.com/android/Using-DialogFragment#sizing-dialogs
        Window window = getDialog().getWindow();
        Point size = new Point();
        if (window != null) {
            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);
            window.setLayout((int) (size.x * 0.95), WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_discard) {
            onDiscardButtonClick();
        } else if (v.getId() == R.id.button_save) {
            onSaveButtonClick();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (getView() != null) {
            if (edTaskName.getText().toString().equals("")) {
                btnSave.setEnabled(false);
            } else {
                btnSave.setEnabled(true);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    void onDiscardButtonClick() {
    }

    void onSaveButtonClick() {
    }

    protected void setupTaskDialogBehavior() {
        rbPriorityMedium.setChecked(true);
        btnSave.setEnabled(false);
        tpDueDate.init(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                null
        );
    }

    public void showSoftKeyboard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        // It only works when we set SHOW_FORCED instead of IMPLICIT
        // http://stackoverflow.com/a/8991563/5557789 (in comment)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    protected Calendar getDate(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, datePicker.getYear());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        return calendar;
    }
}
