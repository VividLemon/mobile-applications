package com.example.samplecode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnLog;
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLog = findViewById(R.id.btnLog);
        btnLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "You pressed on btnLog");
            }
        });
    }

    public void buttonPressed(View view){
        //Toast.makeText(this, "You clicked me", Toast.LENGTH_LONG).show();
        int idNumber = view.getId();
        String idName = getResources().getResourceEntryName(idNumber);
        //Toast.makeText(this, idName, Toast.LENGTH_LONG).show();

        switch(idName){
            case "button":
                Toast.makeText(this, "You clicked on button", Toast.LENGTH_LONG).show();
                break;
            case "button2":
                Toast.makeText(this, "You clicked on Button2", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "Not sure what you clicked on", Toast.LENGTH_LONG).show();
                break;
        }



    }



}