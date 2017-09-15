package com.coderschool.vinh.todoapp.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.coderschool.vinh.todoapp.helpers.DateTimeHelper;
import com.coderschool.vinh.todoapp.models.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LocalDBHandler extends SQLiteOpenHelper {
    private static LocalDBHandler localDBHandler;

    private static final String DATABASE_NAME = "TODO_APP_DATABASE";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PACKAGES = "TASK";

    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_PRIORITY = "PRIORITY";
    private static final String KEY_DATE = "DATE";

    // We can also use cursor.getColumnIndex(KEY_)
    private static final int KEY_ID_INDEX = 0;
    private static final int KEY_NAME_INDEX = 1;
    private static final int KEY_PRIORITY_INDEX = 2;
    private static final int KEY_DATE_INDEX = 3;


    public static synchronized LocalDBHandler getInstance(Context context) {
        if (localDBHandler == null) {
            localDBHandler = new LocalDBHandler(context);
        }
        return localDBHandler;
    }

    private LocalDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PACKAGES_TABLE =
                "CREATE TABLE " + TABLE_PACKAGES
                        + "("
                        + KEY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_NAME + " TEXT,"
                        + KEY_PRIORITY + " TEXT,"
                        + KEY_DATE + " DATE"
                        + ")";
        db.execSQL(CREATE_PACKAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACKAGES);
            onCreate(db);
        }
    }

    public void addTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, task.name);
            values.put(KEY_PRIORITY, task.priority);
            values.put(KEY_DATE, DateTimeHelper.getStringDateFullStandard(task.date));

            db.insertOrThrow(TABLE_PACKAGES, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void addAllTasks(List<Task> tasks) {
        for (Task task : tasks) {
            addTask(task);
        }
    }

    public List<Task> getTasks() {
        SQLiteDatabase db = getWritableDatabase();
        List<Task> tasks = new ArrayList<>();

        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PACKAGES, null);

            if (cursor.moveToFirst()) {
                do {
                    Task task = getTaskFromCursor(cursor);
                    if (task != null) {
                        tasks.add(task);
                    }
                } while (cursor.moveToNext());
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return tasks;
    }

    private Task getTaskFromCursor(@NonNull Cursor cursor) {
        int id = Integer.parseInt(cursor.getString(KEY_ID_INDEX));
        String name = cursor.getString(KEY_NAME_INDEX);
        String priority = cursor.getString(KEY_PRIORITY_INDEX);
        Calendar date = DateTimeHelper.getCalendarFullStandardPattern(cursor.getString(KEY_DATE_INDEX));

        if (isTaskDataValidated(id, name, priority, date)) {
            return new Task(id, name, priority, date);
        }
        return null;
    }

    private boolean isTaskDataValidated(int id, String name, String priority, Calendar date) {
        return id >= 0 && name != null && priority != null & date != null;
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

    public void refreshTasks(List<Task> tasks) {
        deleteAllTasks();
        addAllTasks(tasks);
    }
}
