/*
 * Assignment# 3
 * File Name: Questions.java
 * Full Name: Bijan Razavi, Kushal Tiwari
 */

package com.example.dimanor3.group3_hw03;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dimanor3 on 2/19/2018.
 */

public class Questions implements Serializable {
    private String question, picURL;
	private ArrayList<String> answerChoices = new ArrayList<> ();
	private int questionNumber, correctAnswer;

    public Questions (String question, String picURL, ArrayList<String> answerChoices, int questionNumber, int correctAnswer) {

        this.question = question;
        this.picURL = picURL;
        this.answerChoices = answerChoices;
        this.questionNumber = questionNumber;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion () {
        return question;
    }

    public String getPicURL () {
        return picURL;
    }

    public ArrayList<String> getAnswerChoices () {
        return answerChoices;
    }

    public int getQuestionNumber () {
        return questionNumber;
    }

    public int getCorrectAnswer () {
        return correctAnswer;
    }
}