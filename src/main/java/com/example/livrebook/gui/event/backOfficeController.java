package com.example.livrebook.gui.event;

import com.example.livrebook.gui.delivery.DeliveryController;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class backOfficeController implements Initializable {
    @FXML
    private ImageView exit, menu;

    @FXML
    private AnchorPane pageId;
    @FXML
    private AnchorPane pane1, pane2, centerContent;
    @FXML
    private BorderPane mainPane;

    @FXML
    private JFXButton idHomePage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        exit.setOnMouseClicked(event -> System.exit(0));
        pane1.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menu.setOnMouseClicked(event -> {
            pane1.setVisible(true);
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });

        pane1.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> pane1.setVisible(false));

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });


    }



    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void handleButton1Action(ActionEvent event) {
        try {
            System.out.println("You clicked me");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/livrebook/Event/event-view.fxml"));
            Parent cardView = fxmlLoader.load();

            EventController eventController = fxmlLoader.getController();
            // You can access methods or properties of your CardController here if needed

            mainPane.setCenter(cardView);
        } catch (IOException e) {
            showErrorDialog("Error Loading FXML", "An error occurred while loading the FXML file.");
            e.printStackTrace(); // Add proper error handling here
        }
    }
    @FXML
    void handleButton2Action(ActionEvent event) {

        try {
            System.out.println("You clicked me");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/livrebook/Event/event-view.fxml"));
            Parent cardView = fxmlLoader.load();

            EventController eventController = fxmlLoader.getController();
            // You can access methods or properties of your CardController here if needed

            mainPane.setCenter(cardView);
        } catch (IOException e) {
            showErrorDialog("Error Loading FXML", "An error occurred while loading the FXML file.");
            e.printStackTrace(); // Add proper error handling here
        }
    }
@FXML
     void handleButton3Action(ActionEvent event) {
        try {
            System.out.println("You clicked me");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/livrebook/Event/Ticket.fxml"));
            Parent cardView = fxmlLoader.load();

            TicketController ticketController = fxmlLoader.getController();
            // You can access methods or properties of your CardController here if needed

            mainPane.setCenter(cardView);
        } catch (IOException e) {
            showErrorDialog("Error Loading FXML", "An error occurred while loading the FXML file.");
            e.printStackTrace(); // Add proper error handling here
        }
    }

    @FXML
    void handleButton7Action(ActionEvent event) {

        try {
            System.out.println("You clicked me");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/livrebook/Delivery/Delivery.fxml"));
            Parent cardView = fxmlLoader.load();

            DeliveryController deliveryController = fxmlLoader.getController();


            mainPane.setCenter(cardView);
        } catch (IOException e) {
            showErrorDialog("Error Loading FXML", "An error occurred while loading the FXML file.");
            e.printStackTrace();
        }
    }
}
