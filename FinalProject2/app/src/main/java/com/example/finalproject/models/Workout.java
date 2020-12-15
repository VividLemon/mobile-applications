package com.example.finalproject.models;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Workout {
    private long id;
    private String name;
    private String typeOfWorkout; // declare an enum?
    private String description;
    private String link;
    private int difficulty;
    private long deviceId;
    private String deviceDescription;
    private String deviceName;
    public static final int TYPE_CARDIO = 0;
    public static final int TYPE_STRENGTH = 1;




    public Workout(String name, String typeOfWorkout, String description, String link, int difficulty, long deviceId) {
        this.name = name;
        this.typeOfWorkout = typeOfWorkout;
        this.description = description;
        this.link = link;
        this.difficulty = difficulty;
        this.deviceId = deviceId;
    }//THESE VALUES INCLUDE OPTIONAL

    public Workout(String name, String typeOfWorkout, String description, long deviceId) {
        this.name = name;
        this.typeOfWorkout = typeOfWorkout;
        this.description = description;
        this.deviceId = deviceId;
    }// THESE VALUES ARE REQUIRED. REFLECT THIS IN CREATION. Constructor generally unused

    public Workout(long id, String name, String typeOfWorkout, String description, String link, int difficulty, long deviceId) {
        this.id = id;
        this.name = name;
        this.typeOfWorkout = typeOfWorkout;
        this.description = description;
        this.link = link;
        this.difficulty = difficulty;
        this.deviceId = deviceId;
    }

    public Workout(String name, String typeOfWorkout, String description, String link, int difficulty, String deviceName, String deviceDescription){
        this.name = name;
        this.typeOfWorkout = typeOfWorkout;
        this.description = description;
        this.link = link;
        this.difficulty = difficulty;
        this.deviceName = deviceName;
        this.deviceDescription = deviceDescription;
    }

    public String getDeviceDescription(){return deviceDescription;}
    public String getDeviceName(){return deviceName;}


    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US, "ID: %d TYPE: %s NAME: %s DESC: %s LINK: %s DIFFICULTY: %d DEVICE: %d",
                id, typeOfWorkout, name, description, link, difficulty, deviceId);
    }

    public long getId() {
        return id;
    }

    public String getTypeOfWorkout() {
        return typeOfWorkout;
    }

    public void setTypeOfWorkout(String typeOfWorkout) {
        this.typeOfWorkout = typeOfWorkout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }
}
