package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject.models.Workout;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switchActivity(View view){
        int idNumber = view.getId();
        String idName = getResources().getResourceEntryName(idNumber);
        Intent i = null;

        switch (idName){
            case "btnStart":
                alertDialog();
                break;
            case "btnAdd":
                i = new Intent(this, WorkoutDetailsActivity.class);
                startActivity(i);
                break;
            case "btnList" :
                i = new Intent(this, WorkoutListActivity.class);
                startActivity(i);
                break;
            case "btnDeviceList":
                i = new Intent(this, DeviceListActivity.class);
                startActivity(i);
                break;
            default:
                Toast.makeText(this, "Not sure how you managed to get here", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void alertDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Select type: ");
        dialog.setPositiveButton("Cardio", (dialog12, which) -> {
            Intent i = new Intent(MainActivity.this, StartWorkoutActivity.class);
            i.putExtra(StartWorkoutActivity.EXTRA_WORKOUT_TYPE, Workout.TYPE_CARDIO);
            startActivity(i);
        });
        dialog.setNegativeButton("Strength", (dialog1, which) -> {
            Intent i = new Intent(MainActivity.this, StartWorkoutActivity.class);
            i.putExtra(StartWorkoutActivity.EXTRA_WORKOUT_TYPE, Workout.TYPE_STRENGTH);
            startActivity(i);
        });
        dialog.show();
    }


}