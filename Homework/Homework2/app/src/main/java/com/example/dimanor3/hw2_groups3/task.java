package com.example.dimanor3.hw2_groups3;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Dimanor3 on 2/5/2018.
 */

public class Task implements Serializable {
    private String title, priority, date, time;

    public Task (String title, String priority, String date, String time) {
        this.title = title;
        this.priority = priority;
        this.date = date;
        this.time = time;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getPriority () {
        return priority;
    }

    public void setPriority (String priority) {
        this.priority = priority;
    }

    public String getDate () {
        return date;
    }

    public void setDate (String date) {
        this.date = date;
    }

    public String getTime () {
        return time;
    }

    public void setTime (String time) {
        this.time = time;
    }
}
