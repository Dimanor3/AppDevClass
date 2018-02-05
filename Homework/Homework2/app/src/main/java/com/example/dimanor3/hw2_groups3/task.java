/*
* Homework Assignment:  2
* File Name:            task.java
* Full Name:            Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.hw2_groups3;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Dimanor3 on 2/5/2018.
 */

public class task implements Serializable {
    String title;
    int priority;
    Date date;
    Time time;

    public task (String title, int priority, Date date, Time time) {
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

    public int getPriority () {
        return priority;
    }

    public void setPriority (int priority) {
        this.priority = priority;
    }

    public Date getDate () {
        return date;
    }

    public void setDate (Date date) {
        this.date = date;
    }

    public Time getTime () {
        return time;
    }

    public void setTime (Time time) {
        this.time = time;
    }
}
