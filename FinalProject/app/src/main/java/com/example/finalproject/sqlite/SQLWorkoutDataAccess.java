package com.example.finalproject.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.Workoutable;
import com.example.finalproject.models.Workout;

import java.util.ArrayList;
import java.util.Locale;

public class SQLWorkoutDataAccess implements Workoutable {
    public static final String TAG = "SQLWorkoutDataAccess";
    private ArrayList<Workout> allWorkouts;
    private Context context;
    private MySQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;
    public static final String TABLE_NAME
    public static final String _id
    public static final String name
    public static final String TABLE_NAME
    public static final String TABLE_NAME
    public static final String TABLE_NAME
    public static final String TABLE_NAME
    public static final String TABLE_NAME
    public static final String TABLE_NAME
    public static final String TABLE_NAME
    public static final String TABLE_NAME


    @Override
    public ArrayList<Workout> getAllWorkouts() {
        ArrayList<Workout> workouts = new ArrayList<>();
        String query = String.format(Locale.US, "SELECT ")
    }

    @Override
    public Workout getWorkoutById() {
        return null;
    }

    @Override
    public Workout insertWorkout() {
        return null;
    }

    @Override
    public Workout updateWorkout() {
        return null;
    }

    @Override
    public Workout deleteWorkout() {
        return null;
    }
}
