package com.example.dimanor3.inclass03;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dimanor3 on 1/29/2018.
 */

public class Student implements Parcelable {
    String name, email, department;
    int mood;

    protected Student (Parcel in) {
        name = in.readString ();
        email = in.readString ();
        department = in.readString ();
        mood = in.readInt ();
    }

    public static final Creator<Student> CREATOR = new Creator<Student> () {
        @Override
        public Student createFromParcel (Parcel in) {
            return new Student (in);
        }

        @Override
        public Student[] newArray (int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel parcel, int i) {
        parcel.writeString (name);
        parcel.writeString (email);
        parcel.writeString (department);
        parcel.writeInt (mood);
    }
}
