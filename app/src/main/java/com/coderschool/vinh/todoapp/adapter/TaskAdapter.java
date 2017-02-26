package com.coderschool.vinh.todoapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coderschool.vinh.todoapp.R;
import com.coderschool.vinh.todoapp.models.Date;
import com.coderschool.vinh.todoapp.models.Task;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    TextView tvName;
    TextView tvDate;
    TextView tvPriority;

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_task, parent, false);
        }

        tvName = (TextView)convertView.findViewById(R.id.text_task_name);
        tvDate = (TextView)convertView.findViewById(R.id.text_task_date);
        tvPriority = (TextView)convertView.findViewById(R.id.text_task_priority);

        tvDate.setText(
                Integer.toString(task.date.day) + " "
                + Date.getMonthString(task.date.month) + ", "
                + Integer.toString(task.date.year)
        );
        tvName.setText(task.name);

        int color = Task.getColor(getContext(), task.priority);
        tvPriority.setText(task.priority);
        tvPriority.setTextColor(color);

        return convertView;
    }
}
