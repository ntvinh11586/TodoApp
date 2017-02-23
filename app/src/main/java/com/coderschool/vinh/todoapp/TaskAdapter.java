package com.coderschool.vinh.todoapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coderschool.vinh.todoapp.models.Task;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);

        TextView tvName = (TextView)convertView.findViewById(R.id.text_task_name);
        TextView tvDate = (TextView)convertView.findViewById(R.id.text_task_date);
        TextView tvPriority = (TextView)convertView.findViewById(R.id.text_task_priority);


        tvName.setText(task.name);

        String monthInLeter = "";
        switch (task.date.month) {
            case 1: monthInLeter = "Jan"; break;
            case 2: monthInLeter = "Feb"; break;
            case 3: monthInLeter = "Mar"; break;
            case 4: monthInLeter = "Apr"; break;
            case 5: monthInLeter = "May"; break;
            case 6: monthInLeter = "Jun"; break;
            case 7: monthInLeter = "Jul"; break;
            case 8: monthInLeter = "Aug"; break;
            case 9: monthInLeter = "Sep"; break;
            case 10: monthInLeter = "Oct"; break;
            case 11: monthInLeter = "Nov"; break;
            case 12: monthInLeter = "Dec"; break;
        }

        tvDate.setText(Integer.toString(task.date.day) + " " + monthInLeter + ", " + Integer.toString(task.date.year));
        tvPriority.setText(task.priority);

        if (tvPriority.getText().toString().equals("Low")) {
            tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPriorityLow));
        } else if (tvPriority.getText().toString().equals("Medium")) {
            tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPriorityMedium));
        } else {
            tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPriorityHigh));
        }
        return convertView;
    }
}

