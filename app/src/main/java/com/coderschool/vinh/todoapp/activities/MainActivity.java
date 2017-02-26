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
import com.coderschool.vinh.todoapp.fragments.TaskDialogFragment;
import com.coderschool.vinh.todoapp.fragments.TaskDialogListener;
import com.coderschool.vinh.todoapp.models.DatabaseHandler;
import com.coderschool.vinh.todoapp.models.Date;
import com.coderschool.vinh.todoapp.models.SQLPackage;
import com.coderschool.vinh.todoapp.models.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements TaskDialogListener,
            AdapterView.OnItemLongClickListener,
            AdapterView.OnItemClickListener,
            View.OnClickListener {
    private static final String FRAGMENT_EDIT_NAME = "TaskDialogFragment";

    private TaskAdapter adapter;
    private ArrayList<Task> tasks;

    private DatabaseHandler dbTasks;
    private int changedPosition;    // ???

    private ListView lvTasks;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // get dbTasks
        dbTasks = new DatabaseHandler(this);
        ArrayList<SQLPackage> sqlPackages = dbTasks.getAllPackages();

        tasks = new ArrayList<>();
        for (SQLPackage pk : sqlPackages) {
            tasks.add(new Task(pk.name, pk.priority,
                    new Date(pk.day, pk.month, pk.year)));
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
    protected void onPause() {
        super.onPause();
        dbTasks.deleteAllPackages();
        for (Task task : tasks) {
            dbTasks.addPackage(new SQLPackage(task.name, task.priority,
                    task.date.day, task.date.month, task.date.year));
        }
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        TaskDialogFragment editNameDialogFragment = TaskDialogFragment.newInstance(null);
        editNameDialogFragment.show(fm, FRAGMENT_EDIT_NAME);
    }

    @Override
    public void onTaskDialogFinished(int isChanged,
                                     final String taskName,
                                     final String priority,
                                     final Date dueDate) {
        if (isChanged == 0) {
            // add on app
            Task newTask = new Task(taskName, priority, dueDate);
            tasks.add(0, newTask);
            adapter.notifyDataSetChanged();
        } else {
            // update on app
            Task task = tasks.get(changedPosition);
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
        changedPosition = position;
        FragmentManager fm = getSupportFragmentManager();
        TaskDialogFragment editNameDialogFragment
                = TaskDialogFragment.newInstance(tasks.get(position));
        editNameDialogFragment.show(fm, FRAGMENT_EDIT_NAME);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.FloatingActionButton) {
            showEditDialog();
        }
    }
}