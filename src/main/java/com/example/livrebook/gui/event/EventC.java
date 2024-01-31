package com.example.livrebook.gui.event;

import com.example.livrebook.model.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EventC {

    @FXML
    private Button btn_buy;

    @FXML
    private Label eventAdress;

    @FXML
    private ImageView eventImage;

    @FXML
    private Label eventTitle;
    @FXML
    private GridPane eventContainer;
    @FXML
    private Button reserveButton;



    public void setData(com.example.livrebook.model.event.Event event ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/livrebook/Event/Client_event.fxml"));
        if (event.getPicture() != null) {
            Image image = new Image((getClass().getResourceAsStream(event.getPicture())));
            eventImage.setImage(image);}
        eventTitle.setText(event.getTitle());

        eventAdress.setText(event.getAdresse());
        reserveButton.setOnAction(e -> reserveButtonClicked(e, event));

    }
    @FXML
    void reserveButtonClicked(ActionEvent event, Event ev) {
        System.out.println("Reservation successful for event with ID: " + ev.toString());
        try {
            // Load the FXML file for the popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/livrebook/Event/Event_description.fxml"));
            Parent root = loader.load();
            Event send=ev;
            // Create a new stage
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Les détails de l'événement");

            // Set the content of the stage
            popupStage.setScene(new Scene(root));
            // Get the controller of the popup (if needed)
            EventDescriptionController popupController = loader.getController();

            popupController.setEvent(ev);
            // Show the stage
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
