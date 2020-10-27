package com.example.samplecode;

import androidx.appcompat.app.AppCompatActivity;
import java.text.DateFormat;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.example.samplecode.fileio.FileHelper;
import com.example.samplecode.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;

public class FileIOActivity extends AppCompatActivity {

    public static final String TAG = "FileIOActivity";
    private static final String TASKS_FILE = "tasks.csv";

    ArrayList<Task> tasksList = new ArrayList<>();


    private String convertTaskToCSV(Task task){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return task.getId() + "," +  task.getDescription() + "," + dateFormat.format(task.getDue()) + "," + task.isDone();
    }

    private Task convertCSVToTask(String string){
        Task task = new Task();
        try {
            String[] split = string.split(",");
            task.setId(Long.parseLong(split[0]));
            task.setDescription(split[1]);
            task.setDue(new SimpleDateFormat("MM/dd/yyyy").parse(split[2]));
            task.setDone(Boolean.parseBoolean(split[3]));
        }
        catch (ParseException parseErr){
            Log.e(TAG, "Parse error " + parseErr.toString());
        }
        catch (Exception err){
            Log.e(TAG, err.toString());
        }
        return task;
    }


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

        Task t = new Task(1, "Mow the lawn", new Date(), false);
        String csv = convertTaskToCSV(t);
        Log.d(TAG, "Task CSV: " + csv);
        Task newTask = convertCSVToTask(csv);
        Log.d(TAG, "CSV to Task: " + newTask.toString());

        tasksList.add(new Task(1, "Homework", new Date(), false));
        tasksList.add(new Task(2, "Launcdry", new Date(), false));
        tasksList.add(new Task(3, "Cleaning", new Date(), false));

        String csvData = "";
        for (Task task:
             tasksList) {
            csvData += convertTaskToCSV(task) + "\n";
        }
        FileHelper.writeToFile(TASKS_FILE,csvData, this);
        //Log.d(TAG, csvData);

        csvData = FileHelper.readFromFile(TASKS_FILE, this);
        ArrayList<Task> newTasksList = new ArrayList<>();
        String[] split = csvData.split("\n");
        for (String nextLine :
                split) {
            Task task = convertCSVToTask(nextLine);
            if(task != null){
                newTasksList.add(task);
            }
        }
        Log.d(TAG, newTasksList.toString());



    }
}