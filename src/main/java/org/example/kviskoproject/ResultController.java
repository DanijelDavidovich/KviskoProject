package org.example.kviskoproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resultMessage.setText("dsadsadasdas");
    }
    @FXML
    private Label resultMessage;
    @FXML
    private Button startMenuBtn;
    @FXML
    private Label resultTitle;
    @FXML
    private VBox score;
    @FXML
    private Label scoreTitle;
    @FXML
    private Label nickname;
    @FXML
    private Label correctAnswers;
    @FXML
    private Label averageTime;


    public void setMessage(String message) {
        resultMessage.setText(message);
    }

    public void setResultTitle(String title) {
        resultTitle.setText(title);
    }

    public void setScoreTitle(String title) {
        scoreTitle.setText(title);
    }

    public void setNickname(String nickname) {
        this.nickname.setText(nickname);
    }
    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers.setText(correctAnswers);
    }

    public void setAverageTime(String averageTime) {
        this.averageTime.setText(averageTime);
    }
}
