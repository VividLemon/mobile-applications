package com.example.samplecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class IntentReceiverActivity extends AppCompatActivity {

    TextView lblMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_receiver);

        lblMessage = findViewById(R.id.lblMessage);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Intent.EXTRA_TEXT);
        if(message != null){
            lblMessage.setText(message);
        }else{
            lblMessage.setText("Unable to find the proper KEY in the extras'.");
        }

    }
}