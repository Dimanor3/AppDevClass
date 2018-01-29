/*
 * Assignment #: 1
 * File Name: MainPart3.java
 * Our Names: Bijan Razavi, Kushal Tiwari
 */

package edu.uncc.cci.mobileapps;

import java.util.HashSet;

public class MainPart3 {
    /*
    * Question 3:
    * - This is a simple programming question that focuses on finding the
    * longest increasing subarray. Given the array A = {1,2,3,2,5,2,4,6,7} the
    * longest increasing subarray is {2,4,6,7}, note that the values have to be
    * contiguous.
    * */

    public static void main(String[] args) {
        //example call
        //int[] input = {}; // output {}
        //int[] input = {1}; // output {1}
        //int[] input = {1,2,3,4}; // output {1,2,3,4}
        //int[] input = {1,2,3,4,4,4,4,4,5,6}; // output {1,2,3,4}
        //int[] input = {1,2,3,-1,4,5,8,20,25,1,1,4,6}; // output {-1,4,5,8,20,25}
        //int[] input = {1,2,3,1,1,1,2,3,4,1,1,2,4,6,7}; // output{1,2,4,6,7}
        int[] input = {1,2,3,2,5,2,4,6,7}; // output {2,4,6,7}
        int[] result = printLongestSequence(input);
    }

    public static int[] printLongestSequence(int[] input){
        int[4] result = {};

        int a = 0, b = 0, l = 0;

        for (int i = 0; i < input.length; i++) {
            b = i;

            if (i + 1 < input.length)
                if (input[i] < input[i + 1]) {
                    l++;
                } else {
                    l = 0;
                    a = i;
                }
        }

        int index = 0;

        for (int i = a; i < b; i++) {
            result[index] = input[i];

            index++;
        }

        return result;
    }
}