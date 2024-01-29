package com.example.livrebook.gui.event;

import com.example.livrebook.model.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class CardController {
    @FXML
    private HBox box;

    @FXML
    private Label dateEvent;

    @FXML
    private Label eventName;

    @FXML
    private ImageView eventImage;
    @FXML
    private Label adressEvent;
    private String [] colors ={};



    public void setData(Event event ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/livrebook/Event/Client_event.fxml"));
        if (event.getPicture() != null) {
        Image image = new Image((getClass().getResourceAsStream(event.getPicture())));
        eventImage.setImage(image);}
        eventName.setText(event.getTitle());
        String date= String.valueOf(event.getStartDate());
        dateEvent.setText(date);
        adressEvent.setText(event.getAdresse());
    }
}
