package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.finalproject.models.Device;
import com.example.finalproject.models.Workout;
import com.example.finalproject.models.Workoutable;
import com.example.finalproject.sqlite.SQLDevicesDataAccess;
import com.example.finalproject.sqlite.SQLWorkoutDataAccess;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkoutDetailsActivity extends AppCompatActivity {

    public static final String TAG = "WorkoutDetailsActivity";
    public static final String EXTRA_WORKOUT_ID = "workoutId";
    public static final String EXTRA_WORKOUT_TYPE = "workoutType";
    EditText txtName;
    Spinner spnTypeOfWorkout;
    EditText txtDesc;
    EditText txtLink;
    EditText difficulty;
    Spinner spnTypeOfDevice;
    Button btnSave;
    Button btnDelete;
    Workoutable sda;
    SQLDevicesDataAccess sdda;
    Workout workout;
    TextView txtTypeOfWorkout;
    TextView txtTypeOfDevice;
    ArrayList<String> spinnerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);
        txtName = findViewById(R.id.txtName);
        txtTypeOfWorkout = findViewById(R.id.txtType);
        spnTypeOfWorkout = findViewById(R.id.spnType);
        txtDesc = findViewById(R.id.txtDesc);
        txtLink = findViewById(R.id.txtLink);
        difficulty = findViewById(R.id.txtDifficulty);
        txtTypeOfDevice = findViewById(R.id.txtTypeOfDevice);
        spnTypeOfDevice = findViewById(R.id.spnTypeOfDevice);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        sda = new SQLWorkoutDataAccess(this);
        sdda = new SQLDevicesDataAccess(this);
        spinnerArray = sdda.getAllDevicesNames(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spnTypeOfDevice.setAdapter(arrayAdapter);


        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_WORKOUT_ID, 0);
        int typeFromIntent = i.getIntExtra(EXTRA_WORKOUT_TYPE, -1);
        if(id > 0){
            workout = sda.getWorkoutById(id);
            putDataIntoUI();
        }
        if(typeFromIntent > -1){
            if(typeFromIntent == Workout.TYPE_CARDIO){
                spnTypeOfWorkout.setSelection(Workout.TYPE_CARDIO);
            }else if(typeFromIntent == Workout.TYPE_STRENGTH){
                spnTypeOfWorkout.setSelection(Workout.TYPE_STRENGTH);
            }else{
                spnTypeOfWorkout.setSelection(Workout.TYPE_CARDIO);
                Log.e(TAG,"ERROR in setting type of workout\ntypeFromIntent: " + typeFromIntent + "spnTypeOfWorkout: " + spnTypeOfWorkout);
            }
        }

        btnSave.setOnClickListener(v -> {
            if (validate()) {
                getDataFromUI();
                if (workout.getId() > 0) {
                    sda.updateWorkout(workout);
                } else {
                    sda.insertWorkout(workout);
                }
                Intent i1 = new Intent(WorkoutDetailsActivity.this, WorkoutListActivity.class);
                startActivity(i1);
            }
        });
        btnDelete.setOnClickListener(v -> {
            if(workout != null){
                alertDialog();
            }
        });
    }

    private void getDataFromUI(){
        String name = txtName.getText().toString();
        String type = spnTypeOfWorkout.getSelectedItem().toString();
        String desc = txtDesc.getText().toString();
        String link;
        if(txtLink.getText().toString().isEmpty()){
            link = "";
        }else{
            link = txtLink.getText().toString();
        }
        int diff;
        if(difficulty.getText().toString().equals("")){
            diff = 0;
        }else{
            diff = Integer.parseInt(difficulty.getText().toString());
        }
        long deviceId = placeCorrectDeviceIdForGettingFromUi();
        if(workout != null){
            workout.setName(name);
            workout.setTypeOfWorkout(type);
            workout.setDescription(desc);
            workout.setLink(link);
            workout.setDifficulty(diff);
            workout.setDeviceId(deviceId);
        }else{
            workout = new Workout(name, type, desc, link, diff, deviceId);
        }
    }

    private void putDataIntoUI(){
        if(workout != null){
            txtName.setText(workout.getName());
            if(workout.getTypeOfWorkout().equals("Cardio")){
                spnTypeOfWorkout.setSelection(Workout.TYPE_CARDIO);
            }else if(workout.getTypeOfWorkout().equals("Strength")){
                spnTypeOfWorkout.setSelection(Workout.TYPE_STRENGTH);
            }else{
                spnTypeOfWorkout.setSelection(Workout.TYPE_CARDIO);
                Log.e(TAG, "ERROR IN WORKOUT TYPE: \nworkout.getType: " + workout.getTypeOfWorkout() + "\nCurrent spnType: " + spnTypeOfWorkout.getSelectedItem());
            }
            txtDesc.setText(workout.getDescription());
            txtLink.setText(workout.getLink());
            difficulty.setText(String.valueOf(workout.getDifficulty()));
            placeCorrectDeviceIdForPlacingIntoUi();
            btnDelete.setVisibility(View.VISIBLE);
        }
    }

    private void alertDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(WorkoutDetailsActivity.this);
        dialog.setTitle(R.string.confirm_delete);
        dialog.setPositiveButton(R.string.Yes, (dialog12, which) -> {
            sda.deleteWorkout(workout);
            Intent i = new Intent(WorkoutDetailsActivity.this, WorkoutListActivity.class);
            startActivity(i);
        });
        dialog.setNegativeButton(R.string.No, (dialog1, which) -> dialog1.cancel());
        dialog.show();
    }

    private boolean validate(){
        boolean isValid = true;
        if(txtName.getText().toString().isEmpty()){
            txtName.setError("You must enter a name for the workout");
            isValid = false;
        }
        if(txtDesc.getText().toString().isEmpty()){
            txtDesc.setError("You must enter a description of the workout");
            isValid = false;
        }
        if(spnTypeOfDevice == null || spnTypeOfDevice.getSelectedItem().toString().equals(getString(R.string.no_devices))) {
            spnDeviceAlertDialog();
            txtTypeOfDevice.setError("You need to set a type of device used.");
            isValid = false;// TODO: fix this
        }
        if(!txtLink.getText().toString().isEmpty()){
            //Pattern pattern = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);
            Matcher matcher = Patterns.WEB_URL.matcher(txtLink.getText());
            if(!matcher.find()){
                txtLink.setError("The link must be a valid URL!");
                isValid = false;
            }
        }
        if(!difficulty.getText().toString().isEmpty()){
            int value = Integer.parseInt(difficulty.getText().toString());
            if(value > 10 || value < 0){
                difficulty.setError("Value should be greater than 0, but less than 10");
                isValid = false;
            }
        }
        return isValid;
    }

    private void spnDeviceAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutDetailsActivity.this);
        builder.setTitle("No devices have been made.\nCreate new device?");
        builder.setPositiveButton("Create device", (dialog, which) -> {
            Intent i = new Intent(WorkoutDetailsActivity.this, DeviceDetailsActivity.class);
            startActivity(i);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private long placeCorrectDeviceIdForGettingFromUi(){
        String curr = spnTypeOfDevice.getSelectedItem().toString();
        ArrayList<Device> devices = sdda.getAllDevices();
        long id = -1;
        for(int i = 0; i < devices.size(); i++){
            Device currDevice = devices.get(i);
            if(curr.equals(currDevice.getName())){
                id = currDevice.getId();
            }
        }
        return id;
    }

    private void placeCorrectDeviceIdForPlacingIntoUi(){
        Device device = sdda.getDeviceById(workout.getDeviceId());
        for(int i = 0; i < spinnerArray.size(); i++){
            if(device.getName().equals(spinnerArray.get(i))){
                spnTypeOfDevice.setSelection(i);
            }
        }
    }

}