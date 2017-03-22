package com.coderschool.vinh.todoapp.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coderschool.vinh.todoapp.models.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LocalDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TODO_APP_DATABASE";
    private static final String TABLE_PACKAGES = "TASK";

    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_PRIORITY = "PRIORITY";
    private static final String KEY_DAY = "DAY";
    private static final String KEY_MONTH = "MONTH";
    private static final String KEY_YEAR = "YEAR";

    public LocalDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PACKAGES_TABLE = "CREATE TABLE " + TABLE_PACKAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PRIORITY + " TEXT,"
                + KEY_DAY + " INTEGER,"
                + KEY_MONTH + " INTEGER,"
                + KEY_YEAR + " INTEGER" + ")";
        db.execSQL(CREATE_PACKAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACKAGES);
        onCreate(db);
    }

    public void addTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.name);
        values.put(KEY_PRIORITY, task.priority);
        values.put(KEY_DAY, task.date.get(Calendar.DAY_OF_MONTH));
        values.put(KEY_MONTH, task.date.get(Calendar.MONTH));
        values.put(KEY_YEAR, task.date.get(Calendar.YEAR));

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PACKAGES, null, values);
        db.close();
    }

    public void addAllTasks(List<Task> tasks) {
        for (Task task : tasks) {
            addTask(task);
        }
    }

    public ArrayList<Task> getAllTasks() {
        String selectQuery = "SELECT * FROM " + TABLE_PACKAGES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<Task> tasks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                tasks.add(readTaskFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    private Task readTaskFromCursor(Cursor cursor) {
        Calendar date = Calendar.getInstance();
        date.set(
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(3))
        );
        return new Task(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                date
        );
    }

    public void deleteAllTasks() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_PACKAGES, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void refreshAllTasks(ArrayList<Task> tasks) {
        deleteAllTasks();
        addAllTasks(tasks);
    }
}
