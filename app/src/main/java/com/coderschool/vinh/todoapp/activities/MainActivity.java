package com.coderschool.vinh.todoapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coderschool.vinh.todoapp.R;
import com.coderschool.vinh.todoapp.adapter.TaskAdapter;
import com.coderschool.vinh.todoapp.fragments.TaskCreatorDialogFragment;
import com.coderschool.vinh.todoapp.fragments.TaskDialogFragment;
import com.coderschool.vinh.todoapp.fragments.TaskEditorDialogFragment;
import com.coderschool.vinh.todoapp.models.Task;
import com.coderschool.vinh.todoapp.repositories.LocalDBHandler;
import com.coderschool.vinh.todoapp.repositories.TaskPreferences;

import java.util.ArrayList;

import static com.coderschool.vinh.todoapp.fragments.TaskDialogFragment.TASK_DIALOG_FRAGMENT;

public class MainActivity extends AppCompatActivity
        implements TaskEditorDialogFragment.TaskEditorDialogOnFinishedListener,
        TaskCreatorDialogFragment.TaskCreatorDialogOnFinishedListener,
        AdapterView.OnItemLongClickListener,
        AdapterView.OnItemClickListener,
        View.OnClickListener {
    private ListView lvTasks;
    private FloatingActionButton fab;

    private TaskDialogFragment taskDialogFragment;

    private TaskAdapter adapter;
    private ArrayList<Task> tasks;

    private LocalDBHandler dbTasks;
    private TaskPreferences taskPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        dbTasks = new LocalDBHandler(this);
        taskPreferences = new TaskPreferences(MainActivity.this);

        lvTasks = (ListView) findViewById(R.id.list_task_item);
        lvTasks.setOnItemLongClickListener(this);
        lvTasks.setOnItemClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.FloatingActionButton);
        fab.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    private void showTaskDialog(@Nullable Task task) {
        taskDialogFragment = task != null
                ? TaskEditorDialogFragment.newInstance(task)
                : TaskCreatorDialogFragment.newInstance();
        taskDialogFragment.show(getSupportFragmentManager(), TASK_DIALOG_FRAGMENT);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.removeTask(position);
        return true;
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

    @Override
    public void onTaskCreatorDialogFinished(Task task) {
        adapter.addTask(0, task);
    }

    @Override
    public void onTaskEditorDialogFinished(Task task) {
        adapter.modifyTask(taskPreferences.getCurrentPosition(), task);
    }
}