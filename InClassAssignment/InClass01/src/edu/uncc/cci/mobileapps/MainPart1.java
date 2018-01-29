/*
 * Assignment #: 1
 * File Name: MainPart1.java
 * Our Names: Bijan Razavi, Kushal Tiwari
 */

package edu.uncc.cci.mobileapps;

import java.io.*;
import java.util.*;



class SortByAge implements Comparator<User> {
    public int compare(User a, User b) {
        return a.age - b.age;
    }
}

public class MainPart1 {
    /*
    * Question 1:
    * - In this question you will use the Data.users array that includes
    * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
    * - Create a User class that should parse all the parameters for each user.
    * - Insert each of the users in a list.
    * - Print out the TOP 10 oldest users.
    *
    * Print firstName, lastName, age!
    * */

    public static void main(String[] args) {

        ArrayList<User> user = new ArrayList<User>();

        String[] temp;

        int index = 0;

        //example on how to access the Data.users array.
        for (String str : Data.users) {
            temp = str.split (",");

            user.add (index, new User (temp[0], temp[1], Integer.parseInt (temp[2]), temp[3], temp[4], temp[5], temp[6]));

            index++;
        }

        Collections.sort (user, new SortByAge ());

        for (int i = user.size () - 1; i > user.size () - 11; i--) {
            user.get (i).printOut ();
        }
    }
}