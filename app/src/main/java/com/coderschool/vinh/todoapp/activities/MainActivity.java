package com.coderschool.vinh.todoapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coderschool.vinh.todoapp.R;
import com.coderschool.vinh.todoapp.adapter.TaskAdapter;
import com.coderschool.vinh.todoapp.fragments.TaskCreatorDialogFragment;
import com.coderschool.vinh.todoapp.fragments.TaskEditorDialogFragment;
import com.coderschool.vinh.todoapp.models.Task;
import com.coderschool.vinh.todoapp.repositories.LocalDBHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements TaskEditorDialogFragment.TaskEditorDialogOnFinishedListener,
        TaskCreatorDialogFragment.TaskCreatorDialogOnFinishedListener,
        AdapterView.OnItemLongClickListener,
        AdapterView.OnItemClickListener,
        View.OnClickListener {
    private ListView lvTasks;
    private FloatingActionButton fab;

    private TaskAdapter adapter;
    private ArrayList<Task> tasks;

    private LocalDBHandler dbTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        lvTasks = (ListView) findViewById(R.id.list_task_item);
        lvTasks.setOnItemLongClickListener(this);
        lvTasks.setOnItemClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.FloatingActionButton);
        fab.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        dbTasks = new LocalDBHandler(this);
        tasks = dbTasks.getAllTasks();
        adapter = new TaskAdapter(this, tasks);
        lvTasks.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        if (dbTasks != null) {
            dbTasks.refreshAllTasks(tasks);
        }
        super.onPause();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.removeTask(position);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TaskEditorDialogFragment.newInstance(tasks.get(position), position)
                .show(getSupportFragmentManager(), null);
    }

    @Override
    public void onClick(View v) {
        TaskCreatorDialogFragment.newInstance()
                .show(getSupportFragmentManager(), null);
    }

    @Override
    public void onTaskCreatorDialogFinished(Task task) {
        adapter.addTask(0, task);
    }

    @Override
    public void onTaskEditorDialogFinished(Task task, int position) {
        adapter.modifyTask(position, task);
    }
}