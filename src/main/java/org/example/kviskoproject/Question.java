package org.example.kviskoproject;

import java.util.ArrayList;

public class Question {
    private String questionText;
    private String correctAnswer;
    private ArrayList<String> answers;
    private ArrayList<String> allAnswers;
    private int questionCounter = 0;

    public Question() {}
    public Question(String questionText, String correctAnswer, ArrayList<String> answers) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }
    public Question(Question otherQuestion){
        this.questionText = otherQuestion.getQuestionText();
        this.correctAnswer = otherQuestion.getCorrectAnswer();
        this.answers = otherQuestion.getAnswers();
        this.questionCounter = otherQuestion.getQuestionCounter();
        this.allAnswers = otherQuestion.getAllAnswers();
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

    public int getQuestionCounter() {
        return questionCounter;
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

    public void setQuestionCounter(int questionCounter) {
        this.questionCounter = questionCounter;
    }

    public void setAllAnswers(ArrayList<String> allAnswers) {
        this.allAnswers = allAnswers;
    }

    public void questionCounterIncrement(){
        questionCounter++;
    }
}