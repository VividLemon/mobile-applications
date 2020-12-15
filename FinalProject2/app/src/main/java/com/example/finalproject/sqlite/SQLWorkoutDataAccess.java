package com.example.finalproject.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.finalproject.models.Workout;
import com.example.finalproject.models.Workoutable;

import java.util.ArrayList;
import java.util.Locale;

public class SQLWorkoutDataAccess implements Workoutable {
    public static final String TAG = "SQLWorkoutDataAccess";
    private ArrayList<Workout> allWorkouts;
    private Context context;
    private MySQLiteOpenHelper dbHelper; // convert to local upon final edits with ability to test
    private SQLiteDatabase database;
    public static final String TABLE_NAME = "workout";
    public static final String COLUMN_WORKOUT_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_DIFFICULTY = "difficulty";
    public static final String COLUMN_DEVICE_ID = "_deviceId";
    public static final String TABLE_CREATE_WORKOUTS = String.format(Locale.US, "create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER)",
            TABLE_NAME,
            COLUMN_WORKOUT_ID,
            COLUMN_NAME,
            COLUMN_TYPE,
            COLUMN_DESCRIPTION,
            COLUMN_LINK,
            COLUMN_DIFFICULTY,
            COLUMN_DEVICE_ID
            );

    public SQLWorkoutDataAccess(Context context){
        this.context = context;
        this.dbHelper = new MySQLiteOpenHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }


    @Override
    public ArrayList<Workout> getAllWorkouts() {
        ArrayList<Workout> workouts = new ArrayList<>();
        String query = String.format(Locale.US, "SELECT %s, %s, %s, %s, %s, %s, %s FROM %s",
                COLUMN_WORKOUT_ID,
                COLUMN_NAME,
                COLUMN_TYPE,
                COLUMN_DESCRIPTION,
                COLUMN_LINK,
                COLUMN_DIFFICULTY,
                COLUMN_DEVICE_ID,
                TABLE_NAME);
        Cursor cursor = database.rawQuery(query, null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                String description = cursor.getString(3);
                String link = cursor.getString(4);
                int difficulty = cursor.getInt(5);
                long deviceId = cursor.getLong(6);
                Workout workout = new Workout(id, name, type, description, link, difficulty, deviceId);
                workouts.add(workout);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return workouts;
    }

    public String getDeviceName(long id){
        String query = String.format(Locale.US, "" +
                "SELECT %s.%s, devices.device_name FROM %s INNER JOIN devices ON %s.%s = devices.%s WHERE %s = %d ", TABLE_NAME, COLUMN_WORKOUT_ID, TABLE_NAME, TABLE_NAME, COLUMN_DEVICE_ID, COLUMN_DEVICE_ID, COLUMN_WORKOUT_ID, id);
        Log.d(TAG, "QUERY: " + query);
        Cursor cursor = database.rawQuery(query, null);
        if(cursor != null && cursor.getCount() == 1){
            cursor.moveToFirst();
            String newId = cursor.getString(0);
            String deviceName = cursor.getString(1);
            cursor.close();
            return deviceName;
        }else{
            return null;
        }
    }

    @Override
    public ArrayList<Workout> getAllWorkoutsByType(int type) {
        ArrayList<Workout> workouts = new ArrayList<>();
        if(type == 0) { // Cardio                                 //Name, type, desc, link, diff TABLENAME, TABLENAME.COLUMNDEVICE, COLUMN DEVICE, TYPE
            String query = String.format(Locale.US, "SELECT %s, %s, %s, %s, %s, devices.device_name, devices.devices_description FROM %s INNER JOIN devices ON %s.%s = devices.%s WHERE %s = 'Cardio'",
                    COLUMN_NAME,
                    COLUMN_TYPE,
                    COLUMN_DESCRIPTION,
                    COLUMN_LINK,
                    COLUMN_DIFFICULTY,
                    TABLE_NAME,
                    TABLE_NAME,
                    COLUMN_DEVICE_ID,
                    COLUMN_DEVICE_ID,
                    COLUMN_TYPE);
            Cursor cursor = database.rawQuery(query, null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    String name = cursor.getString(0);
                    String typeExtra = cursor.getString(1);
                    String description = cursor.getString(2);
                    String link = cursor.getString(3);
                    int diff = cursor.getInt(4);
                    String deviceName = cursor.getString(5);
                    String deviceDesc = cursor.getString(6);
                    Workout workout = new Workout(name, typeExtra, description, link, diff, deviceName, deviceDesc);
                    workouts.add(workout);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }else if(type == 1) { // Strength
            String query = String.format(Locale.US, "SELECT %s, %s, %s, %s, %s, devices.device_name, devices.devices_description FROM %s INNER JOIN devices ON %s.%s = devices.%s WHERE %s = 'Strength'",
                    COLUMN_NAME,
                    COLUMN_TYPE,
                    COLUMN_DESCRIPTION,
                    COLUMN_LINK,
                    COLUMN_DIFFICULTY,
                    TABLE_NAME,
                    TABLE_NAME,
                    COLUMN_DEVICE_ID,
                    COLUMN_DEVICE_ID,
                    COLUMN_TYPE);
            Cursor cursor = database.rawQuery(query, null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    String name = cursor.getString(0);
                    String typeExtra = cursor.getString(1);
                    String description = cursor.getString(2);
                    String link = cursor.getString(3);
                    int diff = cursor.getInt(4);
                    String deviceName = cursor.getString(5);
                    String deviceDesc = cursor.getString(6);
                    Workout workout = new Workout(name, typeExtra, description, link, diff, deviceName, deviceDesc);
                    workouts.add(workout);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }else{ //default
            String query = String.format(Locale.US, "SELECT %s, %s, %s, %s, %s, devices.device_name, devices.devices_description FROM %s INNER JOIN devices ON %s.%s = devices.%s WHERE %s = 'Cardio'",
                    COLUMN_NAME,
                    COLUMN_TYPE,
                    COLUMN_DESCRIPTION,
                    COLUMN_LINK,
                    COLUMN_DIFFICULTY,
                    TABLE_NAME,
                    TABLE_NAME,
                    COLUMN_DEVICE_ID,
                    COLUMN_DEVICE_ID,
                    COLUMN_TYPE);
            Cursor cursor = database.rawQuery(query, null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    String name = cursor.getString(0);
                    String typeExtra = cursor.getString(1);
                    String description = cursor.getString(2);
                    String link = cursor.getString(3);
                    int diff = cursor.getInt(4);
                    String deviceName = cursor.getString(5);
                    String deviceDesc = cursor.getString(6);
                    Workout workout = new Workout(name, typeExtra, description, link, diff, deviceName, deviceDesc);
                    workouts.add(workout);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }


        return workouts;
    }


    @Override
    public Workout getWorkoutById(long id) {
        String query = String.format(Locale.US, "SELECT %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s = %d",
                COLUMN_WORKOUT_ID,
                COLUMN_NAME,
                COLUMN_TYPE,
                COLUMN_DESCRIPTION,
                COLUMN_LINK,
                COLUMN_DIFFICULTY,
                COLUMN_DEVICE_ID,
                TABLE_NAME,
                COLUMN_WORKOUT_ID,
                id);
        Cursor cursor = database.rawQuery(query, null);
        if(cursor != null && cursor.getCount() == 1){
            cursor.moveToFirst();
            String name = cursor.getString(1);
            String type = cursor.getString(2);
            String description = cursor.getString(3);
            String link = cursor.getString(4);
            int difficulty = cursor.getInt(5);
            long deviceId = cursor.getLong(6);
            cursor.close();
            return new Workout(id, name, type, description, link, difficulty, deviceId);
        }else{
            return null;
        }
    }

    @Override
    public Workout insertWorkout(Workout workout) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, workout.getName());
        contentValues.put(COLUMN_TYPE, workout.getTypeOfWorkout());
        contentValues.put(COLUMN_DESCRIPTION, workout.getDescription());
        contentValues.put(COLUMN_LINK, workout.getLink());
        contentValues.put(COLUMN_DIFFICULTY, workout.getDifficulty());
        contentValues.put(COLUMN_DEVICE_ID, workout.getDeviceId());
        long insertId = database.insert(TABLE_NAME, null, contentValues);
        return workout;
    }

    @Override
    public Workout updateWorkout(Workout workout) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, workout.getName());
        contentValues.put(COLUMN_TYPE, workout.getTypeOfWorkout());
        contentValues.put(COLUMN_DESCRIPTION, workout.getDescription());
        contentValues.put(COLUMN_LINK, workout.getLink());
        contentValues.put(COLUMN_DIFFICULTY, workout.getDifficulty());
        contentValues.put(COLUMN_DEVICE_ID, workout.getDeviceId());
        int rowsUpdated = database.update(TABLE_NAME, contentValues, COLUMN_WORKOUT_ID + " = " + workout.getId(), null);
        return workout;
    }

    @Override
    public int deleteWorkout(Workout workout) {
        return database.delete(TABLE_NAME, COLUMN_WORKOUT_ID + " = " + workout.getId(), null);
    }



}
