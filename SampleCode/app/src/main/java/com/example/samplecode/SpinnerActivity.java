package com.example.samplecode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class SpinnerActivity extends AppCompatActivity {

    Spinner spinner;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        //assigning UI components
        imageView = findViewById(R.id.imageView);
        spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = spinner.getSelectedItem().toString();
                Toast.makeText(SpinnerActivity.this, selectedItem, Toast.LENGTH_LONG).show();
                changeImage(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void changeImage(String selected){
        int resourceId = 0;
        switch(selected) {
            case "Happy Dog":
                resourceId = R.drawable.happy_dog;
                break;
            case "Angry Dog":
                resourceId= R.drawable.angry_dog;
                break;
            case "Puppies":
                resourceId = R.drawable.dog_with_puppies;
                break;
            default:
                Toast.makeText(this, "Invalid image!", Toast.LENGTH_SHORT).show();

        }
        imageView.setImageResource(resourceId);
    }
}