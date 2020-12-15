package com.example.finalproject.models;

import android.content.Context;

import java.util.ArrayList;

public interface Deviceable {
    ArrayList<String> getAllDevicesNames(Context context);
    ArrayList<Device> getAllDevices();
    Device insertDevice(Device device);
    Device updateDevice(Device device);
    int deleteDevice(Device device);
    Device getDeviceById(long id);

}
