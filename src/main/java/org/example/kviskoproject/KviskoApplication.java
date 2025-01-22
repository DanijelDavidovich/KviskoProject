package org.example.kviskoproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KviskoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(KviskoApplication.class.getResource("quiz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 370);
        stage.setTitle("KVISKO");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}