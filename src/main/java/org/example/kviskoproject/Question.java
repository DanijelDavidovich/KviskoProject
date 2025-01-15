package org.example.kviskoproject;

import java.util.ArrayList;

public class Question {
    private String questionText;
    private String correctAnswer;
    private ArrayList<String> answers;

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

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

}