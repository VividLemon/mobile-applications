package com.example.samplecode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samplecode.models.User;

import java.util.ArrayList;

public class AdapterActivity extends AppCompatActivity {

    ListView listView;
    String[] names = {"Bob", "Sally", "Betty"};
    ArrayList<User> users = User.allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);

        listView = findViewById(R.id.listView);
        //example3();
        example4();

    }
    public void example1(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = names[position];
                Toast.makeText(AdapterActivity.this, selectedName, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void example2(){
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = users.get(position);
                Toast.makeText(AdapterActivity.this, selectedUser.getFirstName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void example3(){
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter(this, R.layout.custom_list_item, R.id.lblFirstName,users){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                //return super.getView(position, convertView, parent);
                View customView = super.getView(position, convertView, parent);
                TextView lblFirstName = customView.findViewById(R.id.lblFirstName);
                CheckBox chkActive = customView.findViewById(R.id.chkActive);
                final User currentUser = users.get(position);
                chkActive.setTag(currentUser);
                chkActive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox pressed = (CheckBox)v;
                        User user = (User)pressed.getTag();
                        user.setActive(pressed.isChecked());
                        Toast.makeText(AdapterActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                customView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AdapterActivity.this, "Selected user: " + currentUser.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                return customView;
            }
        };
        listView.setAdapter(arrayAdapter);
    }
    // context, the layout, WHERE it needs to display the toString data (optional I think), and then the DATA to use
    public void example4(){
        final ArrayAdapter<User> adapter = new ArrayAdapter(this, R.layout.custom_list_item_2, R.id.lblFirstName, users){
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) { // get the view, change the view, then return the view
                View customView = super.getView(position, convertView, parent); // get view
                TextView lblFirstName = customView.findViewById(R.id.lblFirstName);
                Button btnDetails = customView.findViewById(R.id.btnDetails);
                final CheckBox chkActive = customView.findViewById(R.id.chkActive); // get handles on all UI components in custom list item file
                final User currentUser = users.get(position); // make it get the current user in the arraylist and get it's index by passing in position
                lblFirstName.setText(currentUser.getFirstName()); // set text to correct
                chkActive.setChecked(currentUser.isActive()); // set the chkactive to be the correct version
                btnDetails.setText("Edit");
                chkActive.setOnClickListener(new View.OnClickListener() { // in the actual final, you want to actually update content, so instead of using this click listener, you would want to call the update method for SQLite
                    @Override
                    public void onClick(View v) {
                      currentUser.setActive(chkActive.isChecked());
                        Toast.makeText(AdapterActivity.this, currentUser.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                btnDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(AdapterActivity.this, TaskDetailsActivity.class);
//                        intent.putExtra("Index", currentUser.getId()); // Key (should be a static constant of EXTRA_SOMETHING_SOMETHING, then the Id of the current data.
//                        startActivity(intent);
                        Toast.makeText(AdapterActivity.this, "Toast", Toast.LENGTH_SHORT).show();
                    }
                });

                return customView; // return view
            }
        };
        listView.setAdapter(adapter); // you need to set the listView wanted to the made customadapter. This needs to be declared above.
    }

}