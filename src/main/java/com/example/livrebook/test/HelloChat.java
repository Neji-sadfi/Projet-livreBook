package com.example.livrebook.test;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloChat extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Load the chatbot_interface.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/livrebook/chatbot_interface.fxml"));
        Parent root = loader.load();

        Stage chatbotStage = new Stage();
        chatbotStage.setScene(new Scene(root, 600, 400));
        chatbotStage.setTitle("Votre Chatbot");

        Scene scene = null;
        stage.setScene(scene);
        chatbotStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}