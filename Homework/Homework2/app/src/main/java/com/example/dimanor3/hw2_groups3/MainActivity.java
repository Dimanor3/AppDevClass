/*
* Homework Assignment:  2
* File Name:            MainActivity.java
* Full Name:            Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.hw2_groups3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    LinkedList<task> tasks = new LinkedList<task> ();

    // Access to taskOf textview to dynamically update text
    TextView taskOf;
    String task;
    int taskIndex = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        setTitle ("View Tasks");

        if (tasks.size () == 0) {
            taskIndex = -1;
        }

        taskOf = (TextView) findViewById (R.id.taskOf);
        task = "Task " + Integer.toString (taskIndex + 1) + " of " + Integer.toString (tasks.size ());
        taskOf.setText (task);
    }

    public void makeNewTask (View v) {
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
        tasks.remove (taskIndex);

        Log.d ("Test", "DELETED!!!");
    }

    public void gotoNext (View v) {
        if (taskIndex < tasks.size () - 1) {
            taskIndex++;
        } else {
            Toast.makeText (this, "Can't go any further!", Toast.LENGTH_SHORT).show ();
        }

        Log.d ("Test", "NEXT!!!");
    }

    public void gotoLast (View v) {
        taskIndex = tasks.size () - 1;

        Log.d ("Test", "LAST!!!");
    }
}
