package com.example.livrebook.test.actuality;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ActualityFront extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ActualityFront.class.getResource("/com/example/livrebook/actuality/ActualityFront-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
