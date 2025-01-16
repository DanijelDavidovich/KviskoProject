package org.example.kviskoproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.util.*;

public class KviskoController implements Initializable {

    @FXML
    private VBox mainVbox;
    @FXML
    private Label titleText;
    @FXML
    private Label questionText;
    @FXML
    private VBox questionTextBox;
    @FXML
    private VBox initialButtons;
    @FXML
    private Button startBtn;
    @FXML
    private Button rankBtn;
    @FXML
    private HBox answerButtons;
    @FXML
    private Button ansA;
    @FXML
    private Button ansB;
    @FXML
    private Button ansC;
    @FXML
    private Button ansD;
    @FXML
    private HBox jokerBox;
    @FXML
    private Button jokerBtn;

    private int jokerUsage = 2;
    private ReadXMLFile questionsAndAnswers = new ReadXMLFile();
    private List<Question> quizQuestions = new ArrayList<>();
    private static Question currentQuestion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        answerButtons.setVisible(false);
        answerButtons.setManaged(false);
        questionText.setVisible(false);
        questionText.setManaged(false);
        titleText.setText("KVISKO");
        jokerBox.setVisible(false);
        jokerBox.setManaged(false);
        ReadXMLFile.readXMLFile();
    }

    public void startGame(ActionEvent actionEvent) {
        initialButtons.setVisible(false);
        initialButtons.setManaged(false);
        titleText.setVisible(false);
        titleText.setManaged(false);
        questionText.setVisible(true);
        questionText.setManaged(true);
        answerButtons.setVisible(true);
        answerButtons.setManaged(true);
        jokerBox.setVisible(true);
        jokerBox.setManaged(true);
//        titleText.setText("Kako se zove mitološko čudovište u kojem su sjedinjeni lav i orao?");
//        ansA.setText("Jednorog");
//        ansB.setText("Grifon*");
//        ansC.setText("Minotaur");
//        ansD.setText("Eaglion");

        quizQuestions.clear();

        ReadXMLFile.questionsSort();
        getQuizQuestion();
        shuffleQuizQuestions();
        currentQuestion = new Question(quizQuestions.get(0));

//        for(int i=0; i<quizQuestions.size(); i++){
//            System.out.println("Question number " + (i+1) + ".");
//            System.out.println("Text: " + quizQuestions.get(i).getQuestionText());
//            System.out.println("Correct Answer: " + quizQuestions.get(i).getCorrectAnswer());
//            System.out.println("Other Answerds: " + quizQuestions.get(i).getAnswers());
//            System.out.println();
//        }

        quizSet();



    }

    public void quizSet(){
        System.out.println(currentQuestion.getAllAnswers());
        questionText.setText(currentQuestion.getQuestionText());
        ansA.setText(currentQuestion.getAllAnswers().get(0));
        ansB.setText(currentQuestion.getAllAnswers().get(1));
        ansC.setText(currentQuestion.getAllAnswers().get(2));
        ansD.setText(currentQuestion.getAllAnswers().get(3));
        jokerSwitch();
    }

    private void getQuizQuestion() {
        for(int i=0; i<15; i++){
            quizQuestions.add(new Question(questionsAndAnswers.getQuestionsAsList().get(i)));
            questionsAndAnswers.getQuestionsAsList().get(i).questionCounterIncrement();
        }
    }

    private void shuffleQuizQuestions() {
        Collections.shuffle(quizQuestions);
    }


    public void clickOnAnswer() {
        answerButtons.setVisible(false);
        answerButtons.setManaged(false);
        questionText.setVisible(false);
        questionText.setManaged(false);
        titleText.setVisible(true);
        titleText.setManaged(true);
        titleText.setText("KVISKO");
        initialButtons.setVisible(true);
        initialButtons.setManaged(true);
        jokerBox.setVisible(false);
        jokerBox.setManaged(false);
        jokerUsage = 2;
    }



    public void clickA(ActionEvent actionEvent) {
        answerClickHandler(actionEvent);
    }

    public void clickB(ActionEvent actionEvent) {
        answerClickHandler(actionEvent);
    }

    public void clickC(ActionEvent actionEvent) {
        answerClickHandler(actionEvent);
    }

    public void clickD(ActionEvent actionEvent) {
        answerClickHandler(actionEvent);
    }

    private void answerClickHandler(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        if(source.getText().equals(currentQuestion.getCorrectAnswer())){
            int indexOfNextQuestion = getIndexOfNextQuestion();
            if(indexOfNextQuestion >=0) {
                currentQuestion = new Question(quizQuestions.get(indexOfNextQuestion));
                quizSet();
            }
        }else{
            clickOnAnswer();
        }
    }
    private int getIndexOfNextQuestion(){
        for(int i=0; i<quizQuestions.size(); i++){
            if(currentQuestion.getQuestionText().equals(quizQuestions.get(i).getQuestionText())) {
                return i+1;
            }
        }
        return -1;
    }

    public void jokerClick(ActionEvent actionEvent) {
        ArrayList<Button> answers = new ArrayList<>();
        Random rand = new Random();
        int randomAnswerNum = rand.nextInt(3);
        answers.add(ansA);
        answers.add(ansB);
        answers.add(ansC);
        answers.add(ansD);

        for(int i=0; i<3; i++){
            System.out.println("Unutar Jokera");
            if(answers.get(i).getText().equals(currentQuestion.getCorrectAnswer())){
                System.out.println(answers.get(i).getText());
                answers.remove(i);
            }
        }
        answers.remove(randomAnswerNum);
        answers.get(0).setDisable(true);
        answers.get(1).setDisable(true);
        jokerUsage--;
        jokerBtn.setDisable(true);
    }

    private void jokerSwitch(){
        ArrayList<Button> answers = new ArrayList<>();
        answers.add(ansA);
        answers.add(ansB);
        answers.add(ansC);
        answers.add(ansD);
        answers.get(0).setDisable(false);
        answers.get(1).setDisable(false);
        answers.get(2).setDisable(false);
        answers.get(3).setDisable(false);
        System.out.println(jokerUsage);
        if(jokerUsage >= 0){
            jokerBtn.setDisable(false);
        }
    }
}

