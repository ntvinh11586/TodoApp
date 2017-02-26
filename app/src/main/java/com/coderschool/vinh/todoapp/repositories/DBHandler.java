package com.coderschool.vinh.todoapp.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coderschool.vinh.todoapp.models.SQLPackage;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TODO_APP_DATABASE";
    private static final String TABLE_PACKAGES = "TASK";

    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_PRIORITY = "PRIORITY";
    private static final String KEY_DAY = "DAY";
    private static final String KEY_MONTH = "MONTH";
    private static final String KEY_YEAR = "YEAR";

    public DBHandler(Context context) {
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

    public void addPackage(SQLPackage sqlPackage) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, sqlPackage.name);
        values.put(KEY_PRIORITY, sqlPackage.priority);
        values.put(KEY_DAY, sqlPackage.day);
        values.put(KEY_MONTH, sqlPackage.month);
        values.put(KEY_YEAR, sqlPackage.year);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PACKAGES, null, values);
        db.close();
    }

    public ArrayList<SQLPackage> getAllPackages() {
        ArrayList<SQLPackage> packageList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PACKAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SQLPackage sqlPackage = new SQLPackage();
                sqlPackage.id = Integer.parseInt(cursor.getString(0));
                sqlPackage.name = cursor.getString(1);
                sqlPackage.priority = cursor.getString(2);
                sqlPackage.day = Integer.parseInt(cursor.getString(3));
                sqlPackage.month = Integer.parseInt(cursor.getString(4));
                sqlPackage.year = Integer.parseInt(cursor.getString(5));
                packageList.add(sqlPackage);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return packageList;
    }

    public void deleteAllPackages() {
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
}
