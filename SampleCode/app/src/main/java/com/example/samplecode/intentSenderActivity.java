package com.example.samplecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class intentSenderActivity extends AppCompatActivity {

    public static final String EXTRA_SOME_KEY = "somekey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_sender);

        Intent i = getIntent();
        String extraMessage = i.getStringExtra(EXTRA_SOME_KEY);

        if(extraMessage != null){
            Toast.makeText(this, extraMessage, Toast.LENGTH_SHORT).show();
        }
    }
}