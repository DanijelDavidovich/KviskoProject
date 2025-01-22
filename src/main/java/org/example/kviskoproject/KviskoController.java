package org.example.kviskoproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.util.*;

public class KviskoController implements Initializable {

    @FXML
    private VBox mainVbox;
    @FXML
    private VBox questionBox;
    @FXML
    private Label titleText;
    @FXML
    private Label questionText;
    @FXML
    private Label questionTimer;
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
    @FXML
    private HBox rankBox;

    private int jokerUsage = 2;
    private ReadXMLFile questionsAndAnswers = new ReadXMLFile();
    private List<Question> quizQuestions = new ArrayList<>();
    private static Question currentQuestion;
    int timeLeft = 60;
    int averageTime = 0;
    int timeSum = 0;
    private final String[] titleClasses = {"titleClassOn", "titleClassOff"};
    private int titleClassesIndex = 0;

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        if (timeLeft > 0) {
            timeLeft--;
            questionTimer.setText(String.valueOf(timeLeft));
            System.out.println("U timeru");
        } else {

            // Kada dođe do 0, trigerujemo funkciju
            wrongAnswer();
            quizResult("Game Over", "Time out! Try again...", "Player: Donald Trump", "Correct Answers: " + (getIndexOfNextQuestion() - 1), "Average time: " + (timeSum*1.0)/getIndexOfNextQuestion());
//            ((Timeline) event.getSource()).stop(); // Zaustavljamo tajmer
        }
    }));

    Timeline titleSwitch = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
        titleText.getStyleClass().remove(titleClasses[titleClassesIndex]);
        titleClassesIndex = (titleClassesIndex + 1) % titleClasses.length; // Prebaci na sledeći stil
        titleText.getStyleClass().add(titleClasses[titleClassesIndex]);
    }));




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleText.getStyleClass().add(titleClasses[0]);
        titleSwitch.setCycleCount(Timeline.INDEFINITE);
        titleSwitch.play();
        answerButtons.setVisible(false);
        answerButtons.setManaged(false);
        questionBox.setVisible(false);
        questionBox.setManaged(false);
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
        rankBox.setVisible(false);
        rankBox.setManaged(false);
        questionBox.setVisible(true);
        questionBox.setManaged(true);
        answerButtons.setVisible(true);
        answerButtons.setManaged(true);
        jokerBox.setVisible(true);
        jokerBox.setManaged(true);
        timeSum = 0;

        quizQuestions.clear();

        shuffleQuizQuestions();
        getQuizQuestion();
        updateingQuestionsText();
        currentQuestion = quizQuestions.get(0);

        quizSet();
    }

    public void quizSet(){

        timeLeft = 60;
        System.out.println(currentQuestion.getAllAnswers());
        questionText.setText(currentQuestion.getQuestionNumber() + currentQuestion.getQuestionText());
        ansA.setText(currentQuestion.getAllAnswers().get(0));
        ansB.setText(currentQuestion.getAllAnswers().get(1));
        ansC.setText(currentQuestion.getAllAnswers().get(2));
        ansD.setText(currentQuestion.getAllAnswers().get(3));

        questionTimer.setText(timeLeft + "");


        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        jokerSwitch();
    }

    private void getQuizQuestion() {
        for(int i=0; i<15; i++){
            quizQuestions.add(questionsAndAnswers.getQuestionsAsList().get(i));
        }
    }

    private void updateingQuestionsText(){
        for(int i=0; i<15; i++){
            quizQuestions.get(i).setQuestionNumber(i+1 + ". ");
        }
    }

    private void shuffleQuizQuestions() {
        Collections.shuffle(questionsAndAnswers.getQuestionsAsList());
    }


    public void wrongAnswer() {
        answerButtons.setVisible(false);
        answerButtons.setManaged(false);
        questionBox.setVisible(false);
        questionBox.setManaged(false);
        titleText.setVisible(true);
        titleText.setManaged(true);
        titleText.setText("KVISKO");
        rankBox.setVisible(true);
        rankBox.setManaged(true);
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
        timeSum += timeLeft;
        if(source.getText().equals(currentQuestion.getCorrectAnswer())){
            int indexOfNextQuestion = getIndexOfNextQuestion();
            if(indexOfNextQuestion >=0 && indexOfNextQuestion < quizQuestions.size()) {
                currentQuestion = quizQuestions.get(indexOfNextQuestion);
                quizSet();
            }else{
                quizResult("VICTORY", "Congratulations", "Player: Donald Trump", "Correct Answers: 15", "Average time: " + timeSum/15.0);
                wrongAnswer();
            }
        }else{
            quizResult("Game Over", "Wrong answer! Try again...", "Player: Donald Trump", "Correct Answers: " + (getIndexOfNextQuestion() - 1), "Average time: " + (timeSum*1.0)/getIndexOfNextQuestion());
            wrongAnswer();
        }
    }

    private void quizResult(String resultTitle, String resultMessage, String nickname, String answers, String averageTime) {
        try {
            FXMLLoader quizResultLoader = new FXMLLoader(getClass().getResource("result-message.fxml"));
            Parent root = quizResultLoader.load();
            ResultController resultController = quizResultLoader.getController();
            resultController.setResultTitle(resultTitle);
            resultController.setMessage(resultMessage);
            resultController.setNickname(nickname);
            resultController.setCorrectAnswers(answers);
            resultController.setAverageTime(averageTime);


            Stage resultWindow = new Stage();
            Scene quizResultScene = new Scene(root, 400, 300);
            resultWindow.setTitle("Result");
            resultWindow.setScene(quizResultScene);
            timeline.stop();
            resultWindow.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int getIndexOfNextQuestion(){
        return Integer.parseInt( currentQuestion.getQuestionNumber().replaceAll("[^0-9]", ""));
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

