/*
 * Assignment #: 1
 * File Name: User.java
 * Our Names: Bijan Razavi, Kushal Tiwari
 */

package edu.uncc.cci.mobileapps;

import java.io.*;
import java.util.*;

public class User {
    String firstName, lastName, email, gender, city, state;
    int age;

    public User (String fN, String lN, int a, String e, String g, String c, String s) {
        firstName = fN;
        lastName = lN;
        email = e;
        gender = g;
        city = c;
        state = s;
        age = a;
    }

    void printOut () {
        System.out.println ("First Name: " + firstName + " Last Name: " + lastName + " Age: " + age);
    }
}
