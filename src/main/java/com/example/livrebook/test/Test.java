package com.example.livrebook.test;

import com.example.livrebook.test.delivery.TicketApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Test  extends Application {

        @Override
        public void start(Stage stage) throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader(Test.class.getResource("/com/example/livrebook/Event/client_event.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 700);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {

            launch(args);

        }
    }

