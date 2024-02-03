package com.example.livrebook.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class testUser extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventApplication.class.getResource("/com/example/livrebook/user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 700);
        stage.setTitle("Sign In ");
        stage.setScene(scene);
        stage.show();
    }

}

