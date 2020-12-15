package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.models.Device;
import com.example.finalproject.models.Deviceable;
import com.example.finalproject.models.Workout;
import com.example.finalproject.models.Workoutable;
import com.example.finalproject.sqlite.SQLDevicesDataAccess;
import com.example.finalproject.sqlite.SQLWorkoutDataAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class WorkoutListActivity extends AppCompatActivity {

    public static final String TAG = "WorkoutListActivity";
    ListView listView;
    Workoutable sda;
    ArrayList<Workout> allWorkouts;
    boolean isVisible;
    FloatingActionButton fab, childFab;
    TextView childText;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);


        listView = findViewById(R.id.deviceList);
        sda = new SQLWorkoutDataAccess(this);
        allWorkouts = sda.getAllWorkouts();
        if(allWorkouts.size() == 0) {
            Toast.makeText(this, "There is nothing to display, going to add new workout...", Toast.LENGTH_LONG).show(); // TODO: str res
            Intent i = new Intent(this, WorkoutDetailsActivity.class);
            startActivity(i);
        }



        //parent
        fab = findViewById(R.id.expand_fab);
        //child
        childFab = findViewById(R.id.add_workout_fab);
        childText = findViewById(R.id.lbl_add);
        childFab.hide();
        childText.setVisibility(View.GONE);
        isVisible = false;
        fab.setOnClickListener(view -> {
            if(!isVisible){
                childFab.show();
                childText.setVisibility(View.VISIBLE);
                isVisible = true;
            }else{
                childFab.hide();
                childText.setVisibility(View.GONE);
                isVisible = false;
            }
        });

        childFab.setOnClickListener(v -> {
            Intent i = new Intent(WorkoutListActivity.this, WorkoutDetailsActivity.class);
            startActivity(i);
        });



        listView.setOnItemClickListener((parent, view, position, id) -> {
            if(!isVisible){
                Workout selectedWorkout = allWorkouts.get(position);
                Intent i = new Intent(WorkoutListActivity.this, WorkoutDetailsActivity.class);
                i.putExtra(WorkoutDetailsActivity.EXTRA_WORKOUT_ID, selectedWorkout.getId());
                startActivity(i);
            }else{
                childFab.hide();
                childText.setVisibility(View.GONE);
                isVisible = false;
            }
        });


        ArrayAdapter<Workout> adapter = new ArrayAdapter(this, R.layout.workout_list_adapter, R.id.lblName, allWorkouts){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View customView = super.getView(position,convertView,parent);
                TextView lblName = customView.findViewById(R.id.lblName);
                Spinner spnType = customView.findViewById(R.id.spnType2);
                Button btnEdit = customView.findViewById(R.id.btnEdit2);
                TextView lblDesc = customView.findViewById(R.id.txtDesc2);
                TextView lblLink = customView.findViewById(R.id.txtLink2);
                TextView lblDifficulty = customView.findViewById(R.id.txtDiff);
                Spinner spnDevices = customView.findViewById(R.id.spnDevices);
                Deviceable sdda = new SQLDevicesDataAccess(WorkoutListActivity.this);
                ArrayList<String> spinnerArray = sdda.getAllDevicesNames(WorkoutListActivity.this);

                ArrayAdapter arrayAdapter = new ArrayAdapter(WorkoutListActivity.this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                spnDevices.setAdapter(arrayAdapter);

                final Workout currentWorkout = allWorkouts.get(position);
                lblName.setText(currentWorkout.getName());
                if(currentWorkout.getTypeOfWorkout().equals("Cardio")){
                    spnType.setSelection(0);
                }else if(currentWorkout.getTypeOfWorkout().equals("Strength")){
                    spnType.setSelection(1);
                }else{
                    spnType.setSelection(0);
                    Log.e(TAG, "ERROR IN WORKOUT TYPE: \nworkout.getType: " + currentWorkout.getTypeOfWorkout() + "\nCurrent spnType: " + spnType.getSelectedItem());
                }
                lblDesc.setText(currentWorkout.getDescription());
                if(currentWorkout.getLink().isEmpty()){
                    lblLink.setText("(None)");
                }else{
                    lblLink.setText(currentWorkout.getLink());
                }
                lblDifficulty.setText(String.valueOf(currentWorkout.getDifficulty()));
                btnEdit.setOnClickListener(v -> {
                    Intent i = new Intent(WorkoutListActivity.this, WorkoutDetailsActivity.class);
                    i.putExtra(WorkoutDetailsActivity.EXTRA_WORKOUT_ID, currentWorkout.getId());
                    startActivity(i);
                });
                customView.setOnLongClickListener(v -> {
                    Intent i = new Intent(WorkoutListActivity.this, WorkoutDetailsActivity.class);
                    i.putExtra(WorkoutDetailsActivity.EXTRA_WORKOUT_ID, currentWorkout.getId());
                    startActivity(i);
                    return true;
                });

                spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(spnType.getSelectedItem().toString().equals("Cardio")){
                            currentWorkout.setTypeOfWorkout(spnType.getSelectedItem().toString());
                        }else if(spnType.getSelectedItem().toString().equals("Strength")){
                            currentWorkout.setTypeOfWorkout(spnType.getSelectedItem().toString());
                        }else{
                            Log.e(TAG, "ERROR, at spnType on item click listener!\nCurrent workout: " + currentWorkout + "\nSpn type: " + spnType);
                            currentWorkout.setTypeOfWorkout("Cardio");
                        }
                        sda.updateWorkout(currentWorkout);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spnDevices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String curr = spnDevices.getSelectedItem().toString();
                        ArrayList<Device> devices = sdda.getAllDevices();
                        for(int i = 0; i < devices.size(); i++){
                            Device currDevice = devices.get(i);
                            if(curr.equals(currDevice.getName())){
                                currentWorkout.setDeviceId(currDevice.getId());
                            }
                        }
                        sda.updateWorkout(currentWorkout);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                return customView;
            }
        };

        listView.setAdapter(adapter);

    }
}