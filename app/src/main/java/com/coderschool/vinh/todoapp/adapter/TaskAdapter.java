package com.coderschool.vinh.todoapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coderschool.vinh.todoapp.R;
import com.coderschool.vinh.todoapp.helpers.ColorUtils;
import com.coderschool.vinh.todoapp.helpers.DateTimeHelper;
import com.coderschool.vinh.todoapp.models.Task;

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
            tvDate.setText(DateTimeHelper.getStringDateStandard(task.date));

            tvName = (TextView) convertView.findViewById(R.id.text_task_name);
            tvName.setText(task.name);

            tvPriority = (TextView) convertView.findViewById(R.id.text_task_priority);
            tvPriority.setText(task.priority);

            int color = ColorUtils.getColor(getContext(), task.priority);
            tvPriority.setTextColor(color);
        }

        return convertView;
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
}

