package com.example.livrebook.gui.event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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



    public void setData(com.example.livrebook.model.event.Event event ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/livrebook/Event/Client_event.fxml"));
        if (event.getPicture() != null) {
            Image image = new Image((getClass().getResourceAsStream(event.getPicture())));
            eventImage.setImage(image);}
        eventTitle.setText(event.getTitle());

        eventAdress.setText(event.getAdresse());

    }
}
