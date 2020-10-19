package com.example.samplecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class intentSenderActivity extends AppCompatActivity {

    public static final String EXTRA_SOME_KEY = "somekey";

    Button btnOpenUrl;
    EditText txtMessage;
    Button btnSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_sender);

        Intent i = getIntent();
        String extraMessage = i.getStringExtra(EXTRA_SOME_KEY);

        if(extraMessage != null){
            Toast.makeText(this, extraMessage, Toast.LENGTH_SHORT).show();
        }

        btnOpenUrl = findViewById(R.id.btnOpenUrl);
        txtMessage = findViewById(R.id.txtMessage);
        btnSendMessage = findViewById(R.id.btnSendMessage);

        btnOpenUrl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = "http://www.google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String messageText = txtMessage.getText().toString();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, messageText);
                startActivity(i);
            }
        });

    }
}