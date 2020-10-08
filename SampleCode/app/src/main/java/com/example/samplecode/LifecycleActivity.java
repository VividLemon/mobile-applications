package com.example.samplecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LifecycleActivity extends AppCompatActivity {

    // Create a 'tag' for logging
    public static final String TAG = "LifeCycleActivity";

    // We'll use this as the 'key' for when we insert key/value pairs into a hashmap-like object
    public static final String CLICK_COUNT_KEY = "clickCount";

    // We need to 'get a handle' on one of the buttons in the UI
    Button btnClickCount;

    // We are going to count the number of times the button gets clicked
    private int clickCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        // Get handles on the UI components
        btnClickCount = (Button)findViewById(R.id.btnClickCount);

        // Log that the onCreate method running
        // (it will be called/invoked by the Android OS when the OS creates this activity)
        Log.d(TAG, "onCreate().....");

        /*
        // We can restore any saved 'state' of our activity by looking
        // at the Bundle that the Android OS passes into onCreate()
        if(savedInstanceState != null){
            Log.d(TAG, "restoring data.....");
            clickCount = savedInstanceState.getInt(CLICK_COUNT_KEY, 0);
            btnClickCount.setText("Click Count (" + clickCount + ")");
        }
        */
    }

    // event handler for when btnGoToMain is pressed
    public void goToMain(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    // event handler for when btnClickCount is pressed
    public void updateClickCount(View v){
        clickCount++;
        btnClickCount.setText("Click Count (" + clickCount + ")");
    }


    // Let's override the 'life cycle methods' and add log when they are invoked
    // so that we can observe the life cycle of an activity
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume().....");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart().....");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause().....");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop().....");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy().....");
    }

    /*
    // If we need to save any state (such as clickCount) we can override
    // onSaveInstanceState() and stuff the data into the Bundle that get's passed into it
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState().....saving data!!!!");

        //outState.putInt("clickCount", clickCount);
        outState.putInt(CLICK_COUNT_KEY, clickCount);
    }
    */
}