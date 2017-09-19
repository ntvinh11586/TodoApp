package com.coderschool.vinh.todoapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coderschool.vinh.todoapp.R;
import com.coderschool.vinh.todoapp.helpers.DateTimeHelper;
import com.coderschool.vinh.todoapp.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private TextView tvName;
    private TextView tvDate;
    private TextView tvPriority;

    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_task, parent, false);
        }

        Task task = getItem(position);
        if (task != null) {
            tvDate = (TextView) convertView.findViewById(R.id.text_task_date);
            tvDate.setText(
                    (DateTimeHelper.createStandardDateTime())
                            .getStringDate(task.getDate())
            );

            tvName = (TextView) convertView.findViewById(R.id.text_task_name);
            tvName.setText(task.getName());

            tvPriority = (TextView) convertView.findViewById(R.id.text_task_priority);
            tvPriority.setText(task.getPriority());
            tvPriority.setTextColor(getColor(task.getPriority()));
        }

        return convertView;
    }

    private int getColor(String colorStr) {
        switch (colorStr) {
            case "Low":
                return ContextCompat.getColor(getContext(), R.color.colorPriorityLow);
            case "Medium":
                return ContextCompat.getColor(getContext(), R.color.colorPriorityMedium);
            case "High":
                return ContextCompat.getColor(getContext(), R.color.colorPriorityHigh);
            default:
                return ContextCompat.getColor(getContext(), R.color.colorPriorityMedium);
        }
    }

    public void removeTask(int position) {
        remove(getItem(position));
    }

    public void addTask(int position, Task task) {
        insert(task, position);
    }

    public void modifyTask(int position, Task task) {
        removeTask(position);
        addTask(position, task);
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            tasks.add(getItem(i));
        }
        return tasks;
    }

    public Task getTask(int position) {
        return getItem(position);
    }
}

