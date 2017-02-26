package com.coderschool.vinh.todoapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.coderschool.vinh.todoapp.R;
import com.coderschool.vinh.todoapp.adapter.TaskAdapter;
import com.coderschool.vinh.todoapp.fragments.TaskDialog;
import com.coderschool.vinh.todoapp.models.Date;
import com.coderschool.vinh.todoapp.models.DialogResponse;
import com.coderschool.vinh.todoapp.models.Task;
import com.coderschool.vinh.todoapp.repositories.DBHandler;
import com.coderschool.vinh.todoapp.repositories.TaskPreferences;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements TaskDialog.TaskDialogOnFinishedListener,
            AdapterView.OnItemLongClickListener,
            AdapterView.OnItemClickListener,
            View.OnClickListener {
    private static final String FRAGMENT_EDIT_NAME = "TaskDialog";

    private FloatingActionButton fab;
    private ListView lvTasks;

    private TaskAdapter adapter;
    private ArrayList<Task> tasks;

    private DBHandler dbTasks;
    private TaskPreferences taskPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // get dbTasks
        dbTasks = new DBHandler(this);
        ArrayList<Task> sqlPackages = dbTasks.getAllPackages();

        tasks = new ArrayList<>();
        for (Task pk : sqlPackages) {
            tasks.add(new Task(pk.name, pk.priority, pk.date));
        }

        lvTasks = (ListView)findViewById(R.id.list_task_item);
        adapter = new TaskAdapter(this, tasks);
        lvTasks.setAdapter(adapter);
        lvTasks.setOnItemLongClickListener(this);
        lvTasks.setOnItemClickListener(this);

        fab = (FloatingActionButton)findViewById(R.id.FloatingActionButton);
        fab.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        taskPreferences = new TaskPreferences(MainActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbTasks.deleteAllPackages();
        dbTasks.addAllPackages(tasks);
    }

    private void showTaskDialog(Task task) {
        FragmentManager fm = getSupportFragmentManager();
        TaskDialog editNameDialogFragment = TaskDialog.newInstance(task);
        editNameDialogFragment.show(fm, FRAGMENT_EDIT_NAME);
    }

    @Override
    public void onTaskDialogFinished(DialogResponse response) {
        int isChanged = response.getIsChanged();
        String taskName = response.getTaskName();
        String priority = response.getPriority();
        Date dueDate = response.getDueDate();

        if (isChanged == 0) {
            // add on app
            Task newTask = new Task(taskName, priority, dueDate);
            tasks.add(0, newTask);
            adapter.notifyDataSetChanged();
        } else {
            // update on app
            Task task = tasks.get(taskPreferences.getCurrentPosition());
            task.name = taskName;
            task.priority = priority;
            task.date = dueDate;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        tasks.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Delete item at " + position, Toast.LENGTH_SHORT).show();
        // return true or false?
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        taskPreferences.setCurrentPosition(position);
        showTaskDialog(tasks.get(position));
    }

    @Override
    public void onClick(View v) {
        showTaskDialog(null);
    }
}