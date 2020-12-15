package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.models.Device;
import com.example.finalproject.models.Deviceable;
import com.example.finalproject.sqlite.SQLDevicesDataAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.finalproject.DeviceDetailsActivity.EXTRA_DEVICE_ID;

public class DeviceListActivity extends AppCompatActivity {

    boolean isVisible;
    private FloatingActionButton childFab;
    private TextView childText;
    private ArrayList<Device> allDevices;
    private Context context;
    private ArrayAdapter arrayAdapter;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        ListView listView = findViewById(R.id.deviceList);
        Deviceable sdda = new SQLDevicesDataAccess(this);
        allDevices = sdda.getAllDevices();
        if(allDevices.size() == 0){
            Toast.makeText(this, "There is nothing to display, going to add new device...", Toast.LENGTH_LONG).show(); // TODO: str res
            Intent i = new Intent(this, DeviceDetailsActivity.class);
            startActivity(i);
        }

        //parent
        FloatingActionButton fab = findViewById(R.id.expand_fab2);
        //child
        childFab = findViewById(R.id.add_device_fab);
        childFab.hide();
        childText = findViewById(R.id.lbl_add2);
        childText.setVisibility(View.GONE);
        isVisible = false;
        fab.setOnClickListener(view -> {
            if(!isVisible){
                childFab.show();
                childText.setVisibility(View.VISIBLE);
                isVisible = true;
            }else{
                childFab.hide();
                childText.setVisibility(View.GONE);
                isVisible = false;
            }
        });
        childFab.setOnClickListener(v -> {
            Intent i = new Intent(DeviceListActivity.this, DeviceDetailsActivity.class);
            startActivity(i);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if(!isVisible){
                Device selectedDevice = allDevices.get(position);
                Intent i = new Intent(DeviceListActivity.this, DeviceDetailsActivity.class);
                i.putExtra(EXTRA_DEVICE_ID, selectedDevice.getId());
                startActivity(i);
            }else{
                childFab.hide();
                childText.setVisibility(View.GONE);
                isVisible = false;
            }
        });

        arrayAdapter = new ArrayAdapter(this, R.layout.device_list_adapter, R.id.txtDeviceName, allDevices){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//               return super.getView(position, convertView, parent);
                View customView = super.getView(position,convertView, parent);
                TextView lblFirstName = customView.findViewById(R.id.txtDeviceName);
                TextView lblDeviceDesc = customView.findViewById(R.id.txtDeviceDescription);
                Button btnEdit = customView.findViewById(R.id.btnEdit);
                final Device currentDevice = allDevices.get(position);
                lblFirstName.setText(currentDevice.getName());
                lblDeviceDesc.setText(currentDevice.getDesc());
                btnEdit.setText("EDIT"); //TODO:
                btnEdit.setOnClickListener(v -> {
                    Intent i = new Intent(DeviceListActivity.this, DeviceDetailsActivity.class);
                    i.putExtra(EXTRA_DEVICE_ID, currentDevice.getId());
                    startActivity(i);
                });
                customView.setOnLongClickListener(v -> {
                    Intent i = new Intent(DeviceListActivity.this, DeviceDetailsActivity.class);
                    i.putExtra(EXTRA_DEVICE_ID, currentDevice.getId());
                    startActivity(i);
                    return true;
                });
                return customView;
            }
        };
        listView.setAdapter(arrayAdapter);

    }
}