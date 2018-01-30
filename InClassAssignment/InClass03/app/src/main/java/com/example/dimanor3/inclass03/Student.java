package com.example.dimanor3.inclass03;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Dimanor3 on 1/29/2018.
 */

public class Student implements Serializable {
    String name, email, department;
    int mood;

    public Student (String name, String email, String department, int mood) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getDepartment () {
        return department;
    }

    public void setDepartment (String department) {
        this.department = department;
    }

    public int getMood () {
        return mood;
    }

    public void setMood (int mood) {
        this.mood = mood;
    }
}
