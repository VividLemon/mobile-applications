package com.example.samplecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.samplecode.models.Dog;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

        ArrayList<Dog> dogs = Dog.allDogs;

        for(Dog d : dogs){
            Log.d(TAG, d.toString());
        }
    }



    public void buttonPressed(View view){
        //Toast.makeText(this, "You clicked me", Toast.LENGTH_LONG).show();
        int idNumber = view.getId();
        String idName = getResources().getResourceEntryName(idNumber);
        //Toast.makeText(this, idName, Toast.LENGTH_LONG).show();

        Intent i = null;

        switch(idName){
            case "button":
                Toast.makeText(this, "You clicked on button", Toast.LENGTH_LONG).show();
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case "button2":
                Toast.makeText(this, "You clicked on Button2", Toast.LENGTH_LONG).show();
                i = new Intent(this, SpinnerActivity.class);
                startActivity(i);
                break;
            case "btnListener":
                i = new Intent(this, ListenerActivity.class);
                startActivity(i);
                break;
            case "btnLifecycle":
                i = new Intent(this, LifecycleActivity.class);
                startActivity(i);
                break;
            case "btnIntentSender":
                i = new Intent(this,intentSenderActivity.class);
                i.putExtra(intentSenderActivity.EXTRA_SOME_KEY, "HELLO");
                startActivity(i);
                break;

            case "btnFileIO":
                i = new Intent(this, FileIOActivity.class);
                startActivity(i);
                break;
            default:
                Toast.makeText(this, "Not sure what you clicked on", Toast.LENGTH_LONG).show();
                break;
        }


    }



}