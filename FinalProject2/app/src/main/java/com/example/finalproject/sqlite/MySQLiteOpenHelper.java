package com.example.finalproject.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String TAG = "SQLiteOpenHelper";
    public static final String DATA_BASE_NAME = "workouts.sqlite";
    public static final int DATA_BASE_VERSION = 1;

    public MySQLiteOpenHelper(Context context){
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = SQLWorkoutDataAccess.TABLE_CREATE_WORKOUTS;
        db.execSQL(sql);
        sql = SQLDevicesDataAccess.TABLE_CREATE_DEVICES;
        db.execSQL(sql);
        Log.d(TAG, sql);
    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "VERSION: " + DATA_BASE_VERSION);
        db.execSQL("DROP TABLE IF EXISTS " + "workouts");
        db.execSQL("DROP TABLE IF EXISTS " + "devices");
        onCreate(db);

    }
}
