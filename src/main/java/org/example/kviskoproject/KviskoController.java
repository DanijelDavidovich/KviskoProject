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
    private VBox questionTextBox;
    @FXML
    private HBox initialButtons;
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

    int jokerUsage = 2;
    ReadXMLFile questionsAndAnswers = new ReadXMLFile();
    List<Question> quizQuestions = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        answerButtons.setVisible(false);
        answerButtons.setManaged(false);
        titleText.setText("KVISKO");
        jokerBox.setVisible(false);
        jokerBox.setManaged(false);
        ReadXMLFile.readXMLFile();



    }

    public void startGame(ActionEvent actionEvent) {
        initialButtons.setVisible(false);
        initialButtons.setManaged(false);
        answerButtons.setVisible(true);
        answerButtons.setManaged(true);
        jokerBox.setVisible(true);
        jokerBox.setManaged(true);
        titleText.setText("Kako se zove mitološko čudovište u kojem su sjedinjeni lav i orao?");
        ansA.setText("Jednorog");
        ansB.setText("Grifon*");
        ansC.setText("Minotaur");
        ansD.setText("Eaglion");

        quizQuestions.clear();

        ReadXMLFile.questionsSort();
        getQuizQuestion();
        shuffleQuizQuestions();

        for(int i=0; i<quizQuestions.size(); i++){
            System.out.println("Question number " + (i+1) + ".");
            System.out.println("Text: " + quizQuestions.get(i).getQuestionText());
            System.out.println("Correct Answer: " + quizQuestions.get(i).getCorrectAnswer());
            System.out.println("Other Answerds: " + quizQuestions.get(i).getAnswers());
            System.out.println();
        }

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
        titleText.setText("KVISKO");
        initialButtons.setVisible(true);
        initialButtons.setManaged(true);
        jokerBox.setVisible(false);
        jokerBox.setManaged(false);
    }

    public void clickA(ActionEvent actionEvent) {
        clickOnAnswer();
    }

    public void clickB(ActionEvent actionEvent) {
        clickOnAnswer();
    }

    public void clickC(ActionEvent actionEvent) {
        clickOnAnswer();
    }

    public void clickD(ActionEvent actionEvent) {
        clickOnAnswer();
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
            if(answers.get(i).getText().contains("*")){
                answers.remove(i);
            }
        }
        answers.remove(randomAnswerNum);
        answers.get(0).setDisable(true);
        answers.get(1).setDisable(true);
        jokerBtn.setDisable(true);
    }
}