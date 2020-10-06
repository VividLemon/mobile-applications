package com.example.samplecode;

import  androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ListenerActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener);

        btnSample = findViewById(R.id.btnSample);
        /*
        btnSample.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(ListenerActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });
*/
//        View.OnClickListener listener = new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Toast.makeText(ListenerActivity.this, "Hello", Toast.LENGTH_SHORT).show();
//            }
//        };
//        btnSample.setOnClickListener(listener);

        btnSample.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        Toast.makeText(ListenerActivity.this, "Hello", Toast.LENGTH_SHORT).show();
    }
}