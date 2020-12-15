package com.example.finalproject.models;

import java.util.ArrayList;

public interface Workoutable {
    ArrayList<Workout> getAllWorkouts();
    Workout getWorkoutById(long id);
    Workout insertWorkout(Workout workout);
    Workout updateWorkout(Workout workout);
    int deleteWorkout(Workout workout);
    String getDeviceName(long id);
    ArrayList<Workout> getAllWorkoutsByType(int type);


}
