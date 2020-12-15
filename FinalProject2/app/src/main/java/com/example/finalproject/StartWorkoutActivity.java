package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.models.Workout;
import com.example.finalproject.sqlite.SQLDevicesDataAccess;
import com.example.finalproject.sqlite.SQLWorkoutDataAccess;

import java.util.ArrayList;

public class StartWorkoutActivity extends AppCompatActivity {

    public static final String TAG = "StartActivity";
    public static final String EXTRA_WORKOUT_TYPE = "workoutType";

    ArrayList<Workout> workouts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        Intent i = getIntent();
        int type = i.getIntExtra(EXTRA_WORKOUT_TYPE, Workout.TYPE_CARDIO);
        if(type == Workout.TYPE_CARDIO){
            workouts = new SQLWorkoutDataAccess(this).getAllWorkoutsByType(Workout.TYPE_CARDIO);
            if(workouts.size() == 0){
                AlertDialog.Builder dialog = new AlertDialog.Builder(StartWorkoutActivity.this);
                dialog.setTitle("No workouts for Cardio made...");
                dialog.setPositiveButton("Create new workout?", (dialog12, which) -> {
                    Intent intent = new Intent(StartWorkoutActivity.this, WorkoutDetailsActivity.class);
                    intent.putExtra(WorkoutDetailsActivity.EXTRA_WORKOUT_TYPE, Workout.TYPE_CARDIO);
                    startActivity(intent);
                });
                dialog.setNegativeButton("Cancel", (dialog1, which) -> dialog1.cancel());
                dialog.show();
            }
        }else if(type == Workout.TYPE_STRENGTH){
            workouts = new SQLWorkoutDataAccess(this).getAllWorkoutsByType(Workout.TYPE_STRENGTH);
            if(workouts.size() == 0){
                AlertDialog.Builder dialog = new AlertDialog.Builder(StartWorkoutActivity.this);
                dialog.setTitle("No workouts for Strength made...");
                dialog.setPositiveButton("Create new workout?", (dialog12, which) -> {
                    Intent intent = new Intent(StartWorkoutActivity.this, WorkoutDetailsActivity.class);
                    intent.putExtra(WorkoutDetailsActivity.EXTRA_WORKOUT_TYPE, Workout.TYPE_STRENGTH);
                    startActivity(intent);
                });
                dialog.setNegativeButton("Cancel", (dialog1, which) -> {
                    Intent intent = new Intent(StartWorkoutActivity.this, MainActivity.class);
                    Toast.makeText(this, "Exiting...", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                });
                dialog.show();
            }
        }else{
            workouts = new SQLWorkoutDataAccess(this).getAllWorkoutsByType(Workout.TYPE_CARDIO);
            Log.e(TAG, "ERROR in StartActivity: \nType: " + type + "\nworkouts: " + workouts.toString());
            if(workouts.size() == 0){
                Intent intent = new Intent(this, MainActivity.class);
                Toast.makeText(this, "No cardio workouts made\nReturning to main menu...", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        }

    }

}