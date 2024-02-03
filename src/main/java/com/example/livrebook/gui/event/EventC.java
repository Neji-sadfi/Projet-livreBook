package com.example.livrebook.gui.event;

import com.example.livrebook.model.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EventC implements Initializable {

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


        String imagePath = event.getPicture();
        System.out.println(imagePath);
        if (imagePath != null && !imagePath.isEmpty()) {
            // Construire le chemin d'accès complet avec le préfixe file:///
            String fullImagePath = "/com/example/livrebook/image/images/"+imagePath;
            System.out.println(fullImagePath);

            Image image = new Image(getClass().getResourceAsStream(fullImagePath));
// Chargement de l'image à partir du chemin d'accès local



            eventImage.setImage(image);}
        eventTitle.setText(event.getTitle());

        if (event.getNb_ticket()==0){
            eventAdress.setText("Solde Out ");
            eventAdress.setStyle("-fx-text-fill: red;");
        }else {
            eventAdress.setText(event.getAdresse());}
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
