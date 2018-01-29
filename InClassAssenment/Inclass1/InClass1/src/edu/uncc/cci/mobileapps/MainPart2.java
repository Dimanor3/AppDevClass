/*
 * Assignment #: 1
 * File Name: MainPart2.java
 * Our Names: Bijan Razavi, Kushal Tiwari
 */

package edu.uncc.cci.mobileapps;

import java.util.*;

public class MainPart2 {
    /*
    * Question 2:
    * - In this question you will use the Data.users array that includes
    * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
    * - Create a User class that should parse all the parameters for each user.
    * - The goal is to count the number of users living each state.
    * - Print out the list of State, Count order in ascending order by count.
    * */

    public static void main(String[] args) {
        ArrayList<User> user = new ArrayList<User>();

        String[] temp;

        HashMap<String, Integer> map = new HashMap<> ();

        int index = 0;

        //example on how to access the Data.users array.
        for (String str : Data.users) {
            temp = str.split (",");

            user.add (index, new User (temp[0], temp[1], Integer.parseInt (temp[2]), temp[3], temp[4], temp[5], temp[6]));

            index++;
        }

        int incre = 0;

        for (User u : user) {
            if (map.containsKey (u.state)) {
                map.compute (u.state, (k, v) -> v + 1);
            } else {
                map.put (u.state, 1);
            }
        }

        for (String name : map.keySet ()) {
            System.out.println (name + " " + map.get (name));
        }
    }
}