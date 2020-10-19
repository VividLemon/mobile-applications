package com.example.samplecode;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.example.samplecode.fileio.FileHelper;

public class FileIOActivity extends AppCompatActivity {

    public static final String TAG = "FileIOActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_i_o);

        boolean result = FileHelper.writeToFile("test.txt", "Hello world!!!", this);
        if(result){
            Log.d(TAG, "PASSED THE FIRST TEST!");
        }
        else{
            Log.d(TAG, "FAILED TO WRITETOFILE() DID NOT SUCCEED");
        }

        String data = FileHelper.readFromFile("test.txt", this);
        if(data != null){
            Log.d(TAG, "PASSED THE SECOND TEST");
        }
        else{
            Log.d(TAG, "FAILED TO READFROMFILE()");
        }
    }
}