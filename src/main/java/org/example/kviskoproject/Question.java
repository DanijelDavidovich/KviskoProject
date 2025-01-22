package org.example.kviskoproject;

import java.util.ArrayList;
//import lombok.Getter;

//@Getter
public class Question {
    private String questionText;
    private String correctAnswer;
    private ArrayList<String> answers;
    private ArrayList<String> allAnswers;
    private String questionNumber = "";
    private int timer = 60;
    private int answerTime = 0;

    public Question() {}
    public Question(String questionText, String correctAnswer, ArrayList<String> answers) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    public String getQuestionText() {
        return questionText;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<String> getAllAnswers() {
        return allAnswers;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void setAllAnswers(ArrayList<String> allAnswers) {
        this.allAnswers = allAnswers;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }
    public String getQuestionNumber() {
        return questionNumber;
    }

    public int getTimer() {
        return timer;
    }
    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(int answerTime) {
        this.answerTime = answerTime;
    }

}