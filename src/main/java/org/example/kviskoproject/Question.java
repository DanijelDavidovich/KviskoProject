package org.example.kviskoproject;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class Question {
    private String questionText;
    private String correctAnswer;
    private ArrayList<String> answers;
    private ArrayList<String> allAnswers;
    private int questionNumber = 0;
}