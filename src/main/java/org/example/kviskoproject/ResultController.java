package org.example.kviskoproject;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
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
    private Label nicknameField;
    @FXML
    private Label corrAnsField;
    @FXML
    private Label averTimeField;

    @Setter
    private Stage resultStage;

    private boolean congratulationSwitch = false;


//    Timeline winCongratulation = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
//        congratulationSwitch = congratulationSwitch ? false : true;
//        if (congratulationSwitch) {
//            resultMessage.getStyleClass().remove("resultMessageWin");
//            resultMessage.getStyleClass().add("resultMessageWinTact");
//        }else{
//            resultMessage.getStyleClass().remove("resultMessageWinTact");
//            resultMessage.getStyleClass().add("resultMessageWin");
//        }
//    }));

    ScaleTransition winTitleTransition;


    public void setMessage(String message) {
        resultMessage.setText(message);
        if(message.equals("CONGRATULATIONS")) {
            winTitleTransition = new ScaleTransition(Duration.seconds(0.3), resultMessage);
            winTitleTransition.setFromY(1);
            winTitleTransition.setFromX(1);
            winTitleTransition.setToY(1.5);
            winTitleTransition.setToX(1.2);
            winTitleTransition.setCycleCount(ScaleTransition.INDEFINITE); // Beskonačno
            winTitleTransition.setAutoReverse(true);
            winTitleTransition.play();
//            winCongratulation.setCycleCount(Timeline.INDEFINITE);
//            winCongratulation.play();
        }

    }

    public void setResultTitle(String title) {
        resultTitle.setText(title);
    }

    public void setScoreTitle(String title) {
        scoreTitle.setText(title);
    }

    public void setNicknameField(String nicknameField) {
        this.nicknameField.setText(nicknameField);
    }
    public void setCorrAnsField(String corrAnsField) {
        this.corrAnsField.setText(corrAnsField);
    }

    public void setAverTimeField(String averageTime) {
        this.averTimeField.setText(averageTime);
    }

    public void setGameOverStyle(){
        resultTitle.getStyleClass().remove("resultTitleWin");
        resultTitle.getStyleClass().add("resultTitleGameOver");

        resultMessage.getStyleClass().remove("resultMessageWin");
        resultMessage.getStyleClass().add("resultMessageGameOver");
    }

    public void setWinStyle(){
        resultTitle.getStyleClass().remove("resultTitleGameOver");
        resultTitle.getStyleClass().add("resultTitleWin");

        resultMessage.getStyleClass().remove("resultMessageGameOver");
        resultMessage.getStyleClass().add("resultMessageWin");
    }

    public void startMenuBtnHandler(javafx.event.ActionEvent actionEvent) {
        resultStage.close();
    }
}
