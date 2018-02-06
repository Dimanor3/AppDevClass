/*
* Homework Assignment:  2
* File Name:            MainActivity.java
* Full Name:            Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.hw2_groups3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    LinkedList<Task> Tasks = new LinkedList<> ();
    TextView taskTitle, taskTime, taskDate, taskPriority;

    // Access to taskOf textview to dynamically update text
    TextView taskOf;
    String task;
    int taskIndex = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        setTitle ("View Tasks");

        taskTitle = (TextView) findViewById (R.id.taskTitle);
        taskDate = (TextView) findViewById (R.id.taskDate);
        taskTime = (TextView) findViewById (R.id.taskTime);
        taskPriority = (TextView) findViewById (R.id.taskPriority);

        if (Tasks.size () == 0) {
            taskIndex = -1;
        }

        taskOf = (TextView) findViewById (R.id.taskOf);
        task = "Task " + Integer.toString (taskIndex + 1) + " of " + Integer.toString (Tasks.size ());
        taskOf.setText (task);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            Task test = (Task) data.getExtras ().getSerializable ("result");

            //Tasks.add ();
        }
    }

    public void makeNewTask (View v) {
        Intent intent = new Intent (this, createTask.class);
        startActivityForResult (intent, 0);

        Log.d ("Test", "ADD NEW!!!");
    }

    public void gotoFirst (View v) {
        taskIndex = 0;

        Log.d ("Test", "FIRST!!!");
    }

    public void gotoPrevious (View v) {
        if (taskIndex > 0) {
            taskIndex--;
        } else {
            Toast.makeText (this, "Can't go back any further!", Toast.LENGTH_SHORT).show ();
        }

        Log.d ("Test", "PREVIOUS!!!");
    }

    public void edit (View v) {
        Log.d ("Test", "EDIT!!!");
    }

    public void delete (View v) {
        Tasks.remove (taskIndex);

        Log.d ("Test", "DELETED!!!");
    }

    public void gotoNext (View v) {
        if (taskIndex < Tasks.size () - 1) {
            taskIndex++;
        } else {
            Toast.makeText (this, "Can't go any further!", Toast.LENGTH_SHORT).show ();
        }

        Log.d ("Test", "NEXT!!!");
    }

    public void gotoLast (View v) {
        taskIndex = Tasks.size () - 1;

        Log.d ("Test", "LAST!!!");
    }
}
