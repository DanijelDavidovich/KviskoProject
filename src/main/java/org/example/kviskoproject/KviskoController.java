package org.example.kviskoproject;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

    private List<Button> answerButtonsList = new ArrayList<>();


    int timeLeft = 10;
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
            quizResult("Game Over", "Time out! Try again...", "Donald Trump", "" + (getIndexOfNextQuestion() - 1), (timeSum*1.0)/getIndexOfNextQuestion());
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
        ReadXMLFile.readXMLFile();
        answerButtonsList = new ArrayList<>(Arrays.asList(ansA, ansB, ansC, ansD));

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

        getQuizQuestion();
        updateingQuestionsText();
        currentQuestion = quizQuestions.getFirst();

        quizSet();
    }

    public void quizSet(){
        int ansBtnOrd = 0;
        timeLeft = 10;
        questionText.setText(currentQuestion.getQuestionNumber() + ". " + currentQuestion.getQuestionText());

        for(Button ansBtn : answerButtonsList ){
            ansBtn.setText(currentQuestion.getAllAnswers().get(ansBtnOrd));
            ansBtnOrd++;
        };

        answersHandlers();


        questionTimer.setText(timeLeft + "");


        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        jokerSwitch();
    }

    private void getQuizQuestion() {
        Collections.shuffle(questionsAndAnswers.getQuestionsAsList());
        for(int i=0; i<15; i++){
            quizQuestions.add(questionsAndAnswers.getQuestionsAsList().get(i));
        }
    }

    private void updateingQuestionsText(){
        for(int i=0; i<15; i++){
            quizQuestions.get(i).setQuestionNumber(i+1);
        }
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

    public void answersHandlers(){
        for(Button btn : answerButtonsList){
            btn.setOnAction(event -> answerClickHandler(event));
        }
    }

    private void answerClickHandler(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        timeSum += timeLeft;
        if(source.getText().equals(currentQuestion.getCorrectAnswer())){
            int indexOfNextQuestion = getIndexOfNextQuestion();
            if(indexOfNextQuestion < quizQuestions.size()) {
                currentQuestion = quizQuestions.get(indexOfNextQuestion);
                quizSet();
            }else{
                quizResult("You Win", "CONGRATULATIONS", "Donald Trump", "15",  (timeSum*1.0)/15);
                wrongAnswer();
            }
        }else{
            quizResult("Game Over", "Wrong answer! Try again...", "Donald Trump", "" + (getIndexOfNextQuestion() - 1), (timeSum*1.0)/getIndexOfNextQuestion());
            wrongAnswer();
        }
    }

    private void quizResult(String resultTitle, String resultMessage, String nickname, String answers, double averageTime) {
        try {
            FXMLLoader quizResultLoader = new FXMLLoader(getClass().getResource("result-message.fxml"));
            Parent root = quizResultLoader.load();
            ResultController resultController = quizResultLoader.getController();
            resultController.setResultTitle(resultTitle);
            resultController.setMessage(resultMessage);
            resultController.setNicknameField(nickname);
            resultController.setCorrAnsField(answers);
            resultController.setAverTimeField(String.format("%.1f", averageTime));
            if(resultTitle.equals("Game Over")){
                resultController.setGameOverStyle();
            }else{
                resultController.setWinStyle();
            }


            Stage resultWindow = new Stage();
            Scene quizResultScene = new Scene(root, 400, 300);
            resultWindow.setTitle("Result");
            resultWindow.initStyle(StageStyle.UNDECORATED);
            resultWindow.setScene(quizResultScene);
            resultWindow.initModality(Modality.APPLICATION_MODAL);
            resultController.setResultStage(resultWindow);
            timeline.stop();
            resultWindow.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int getIndexOfNextQuestion(){
        return currentQuestion.getQuestionNumber();
    }

    public void jokerClick(ActionEvent actionEvent) {
        ArrayList<Button> answers = new ArrayList<>();
        Random rand = new Random();
        int randomAnswerNum = rand.nextInt(3);
        for(Button ansBtn : answerButtonsList){
            answers.add(ansBtn);
        }

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

        for(Button ansBtn : answerButtonsList){
            ansBtn.setDisable(false);
        }

        System.out.println(jokerUsage);
        if(jokerUsage >= 0){
            jokerBtn.setDisable(false);
        }
    }
}

