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
    String question, picURL;
    ArrayList<String> answerChoices = new ArrayList<> ();
    int questionNumber, correctAnswer;

    public Questions (String question, String picURL, ArrayList<String> answerChoices, int questionNumber, int correctAnswer) {

        this.question = question;
        this.picURL = picURL;
        this.answerChoices = answerChoices;
        this.questionNumber = questionNumber;
        this.correctAnswer = correctAnswer;
    }

    public void setQuestion (String question) {
        this.question = question;
    }

    public void setPicURL (String picURL) {
        this.picURL = picURL;
    }

    public void setAnswerChoices (ArrayList<String> answerChoices) {
        this.answerChoices = answerChoices;
    }

    public void setQuestionNumber (int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void setCorrectAnswer (int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}