package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalproject.models.Device;
import com.example.finalproject.sqlite.SQLDevicesDataAccess;

public class DeviceDetailsActivity extends AppCompatActivity {

    EditText name;
    EditText desc;
    Button save;
    Button delete;
    SQLDevicesDataAccess sdda;
    Device device;
    public static final String TAG = "DeviceDetailsActivity";
    public static final String EXTRA_DEVICE_ID = "deviceId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);

        name = findViewById(R.id.txtDevName);
        desc = findViewById(R.id.txtDevDesc);
        save = findViewById(R.id.btnSaveDevice);
        delete = findViewById(R.id.btnDelete2);
        sdda = new SQLDevicesDataAccess(this);

        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_DEVICE_ID, 0);
        if(id > 0){
            device = sdda.getDeviceById(id);
            putDataIntoUI();
        }

        save.setOnClickListener(v -> {
            if(validate()){
                getDataFromUI();
                if(device.getId() > 0){
                    sdda.updateDevice(device);
                }else{
                    sdda.insertDevice(device);
                }
                Intent intent = new Intent(DeviceDetailsActivity.this, DeviceListActivity.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(v -> {
            if(device != null){
                alertDialog();
            }
        });

    }


    private void getDataFromUI(){
        String deviceName = name.getText().toString();
        String deviceDesc = desc.getText().toString();
        if(device != null){
            device.setName(deviceName);
            device.setDesc(deviceDesc);

        }else{
           device = new Device(deviceName, deviceDesc);
        }
    }
    private void putDataIntoUI(){
        if(device != null){
            name.setText(device.getName());
            desc.setText(device.getDesc());
            delete.setVisibility(View.VISIBLE);
        }
    }
    private void alertDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(DeviceDetailsActivity.this);
        dialog.setTitle(R.string.confirm_delete);
        dialog.setPositiveButton(R.string.Yes, (dialog12, which) -> {
            sdda.deleteDevice(device);
            Intent i = new Intent(DeviceDetailsActivity.this, DeviceListActivity.class);
            startActivity(i);
        });
        dialog.setNegativeButton(R.string.No, (dialog1, which) -> dialog1.cancel());
        dialog.show();
    }
    private boolean validate(){
        boolean isValid = true;
        if(name.getText().toString().isEmpty()) {
            name.setError("The name of device field cannot be empty"); // TODO str res
            isValid = false;
        }
        if(desc.getText().toString().isEmpty()) {
            desc.setError("The description should not be empty");
            isValid = false;
        }
        return isValid;
    }
}