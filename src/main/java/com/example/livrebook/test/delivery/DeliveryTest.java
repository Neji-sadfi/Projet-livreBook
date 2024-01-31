package com.example.livrebook.test.delivery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

<<<<<<<< HEAD:src/main/java/com/example/livrebook/test/TicketApplication.java
public class TicketApplication  extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/livrebook/Event/Ticket.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
========
public class DeliveryTest extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DeliveryTest.class.getResource("/com/example/livrebook/Delivery/Delivery.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
>>>>>>>> 6174e16e975e09bbc1059011add929c71cbeac49:src/main/java/com/example/livrebook/test/delivery/DeliveryTest.java
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}