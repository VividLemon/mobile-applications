package com.example.finalproject.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.R;
import com.example.finalproject.models.Device;
import com.example.finalproject.models.Deviceable;

import java.util.ArrayList;
import java.util.Locale;

public class SQLDevicesDataAccess implements Deviceable {
    public static final String TAG = "SQLDevicesDataAccess";
    private Context context;
    private MySQLiteOpenHelper dbHelper; // convert to local upon final edits with ability to test
    private SQLiteDatabase database;
    public static final String TABLE_NAME = "devices";
    public static final String COLUMN_DEIVCE_ID = "_deviceid";
    public static final String COLUMN_NAME = "device_name";
    public static final String COLUMN_DESCRIPTION = "devices_description";


    public static final String TABLE_CREATE_DEVICES = String.format(Locale.US, "create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
            TABLE_NAME,
            COLUMN_DEIVCE_ID,
            COLUMN_NAME,
            COLUMN_DESCRIPTION
    );

    public SQLDevicesDataAccess(Context context) {
        this.context = context;
        this.dbHelper = new MySQLiteOpenHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    public ArrayList<String> getAllDevicesNames(Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        String query = String.format(Locale.US, "SELECT %s FROM %s", COLUMN_NAME, TABLE_NAME);
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(0);
                arrayList.add(name);
                cursor.moveToNext();
            }
            cursor.close();
        } else {
            arrayList.add(context.getString(R.string.no_devices));
        }
        return arrayList;
    }

    @Override
    public ArrayList<Device> getAllDevices() {
        ArrayList<Device> devices = new ArrayList<>();
        String query = String.format(Locale.US, "SELECT %s, %s, %s FROM %s", COLUMN_DEIVCE_ID, COLUMN_NAME, COLUMN_DESCRIPTION, TABLE_NAME);
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                Device device = new Device(id, name, desc);
                devices.add(device);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return devices;
    }

    @Override
    public Device insertDevice(Device device) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, device.getName());
        contentValues.put(COLUMN_DESCRIPTION, device.getDesc());
        long insertId = database.insert(TABLE_NAME, null, contentValues);
        return device;
    }

    @Override
    public Device updateDevice(Device device) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, device.getName());
        contentValues.put(COLUMN_DESCRIPTION, device.getDesc());
        int rowsUpdated = database.update(TABLE_NAME, contentValues, COLUMN_DEIVCE_ID + " = " + device.getId(), null);
        return device;
    }

    @Override
    public int deleteDevice(Device device) {
        return database.delete(TABLE_NAME, COLUMN_DEIVCE_ID + " = " + device.getId(), null);
    }

    @Override
    public Device getDeviceById(long id) {
        String query = String.format(Locale.US, "SELECT %s, %s, %s FROM %s WHERE %s = %d", COLUMN_DEIVCE_ID, COLUMN_NAME, COLUMN_DESCRIPTION, TABLE_NAME, COLUMN_DEIVCE_ID, id);
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.getCount() == 1) {
            cursor.moveToFirst();
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            cursor.close();
            return new Device(id, name, desc);
        } else {
            return null;
        }
    }
}
